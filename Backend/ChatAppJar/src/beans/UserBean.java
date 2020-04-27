package beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.User;

@Stateful
@Path("/users")
@LocalBean //sad restendpointi ne moraju biti u remote interfejsu
//prima rest i prepakuje poruku u jms poruku 1 korak
public class UserBean {
	
	@EJB
	Data data; //database for registered users and messages
	
	@Context
	ServletContext context; //servlet context for logged in users
	
	@PostConstruct
	public void init () {
		List<User> list = new ArrayList<>();
	  this.context.setAttribute("loggedIn", list);
	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(User user) {
	
		System.out.println(user.getUsername());
		if(data.registered.contains(user)) 
		{
			System.out.println("Sucessfully logged in "+user.getUsername());
			
			@SuppressWarnings("unchecked")
			List<User> loggedIn= (List<User>) context.getAttribute("loggedIn");
			loggedIn.add(user);
			context.setAttribute("loggedIn", loggedIn);
			
			return Response
				      .status(Response.Status.OK)
				      .build();

		}
		
		return Response
			      .status(Response.Status.BAD_REQUEST)
			      .build();
		
	}
	
	@POST
	@Path("/register")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response register(User user) {
		
		for(User u : data.registered) {
			if (u.getUsername().equals(user.getUsername()))
			return Response
			      .status(Response.Status.BAD_REQUEST)
			      .build();
		}
		
		System.out.println("Sucessfully registered "+user.getUsername());
		data.registered.add(user);
		
		return Response
			      .status(Response.Status.OK)
			      .build();
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/loggedIn")
	public List<User> loggedIn() {
		System.out.println("loggedIn "+ context.getAttribute("loggedIn"));
		return (List<User>) context.getAttribute("loggedIn");
	}
	
	@GET
	@Path("/registered")
	public List<User> registered() {
		System.out.println("registered "+data.registered);
		return data.registered;
	}
	
	@DELETE
	@Path("/loggedIn/{user}")
	@Produces(MediaType.TEXT_PLAIN)
	public String delete(@PathParam("user") String user) {
		System.out.println("deleting");
		
		@SuppressWarnings("unchecked")
		List<User> loggedIn = (List<User>) context.getAttribute("loggedIn");
		
		for(User u : loggedIn) {
			if(u.getUsername().equals(user)) {
				loggedIn.remove(u);
				context.setAttribute("loggedIn", loggedIn);
				return "sucess";
			}
		}
		return "error";
		
	}
}
