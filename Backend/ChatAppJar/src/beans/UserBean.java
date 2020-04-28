package beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.PostActivate;
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

import model.CustomMessage;
import model.User;

@Stateful
@Path("/users")
@LocalBean // sad restendpointi ne moraju biti u remote interfejsu
//prima rest i prepakuje poruku u jms poruku 1 korak
public class UserBean {

	@EJB
	Data data; // database for registered users and messages

	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)

	public String login(User user) {

		if (data.getRegistered().contains(user)) {
			if (!data.getLoggedIn().contains(user))
				data.getLoggedIn().add(user);
				System.out.println(user+" logged in");
				
				ObjectMapper mapper = new ObjectMapper();
				String obj;
				try {
					obj = mapper.writeValueAsString(user);
					return obj;
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}	
		}

		return null;

	}

	@POST
	@Path("/register")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response register(User user) {

		for (User u : data.getRegistered()) {
			if (u.getUsername().equals(user.getUsername()))
				return Response.status(Response.Status.BAD_REQUEST).build();
		}

		System.out.println("Sucessfully registered " + user.getUsername());
		data.getRegistered().add(user);

		return Response.status(Response.Status.OK).build();
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/loggedIn")
	public List<User> loggedIn() {
		System.out.println("loggedIn " + data.getLoggedIn());
		return data.getLoggedIn();
	}

	@GET
	@Path("/registered")
	public List<User> registered() {
		System.out.println("registered " + data.getRegistered());
		return data.getRegistered();
	}

	@DELETE
	@Path("/loggedIn/{user}")
	@Produces(MediaType.TEXT_PLAIN)
	public String delete(@PathParam("user") String user) {
		System.out.println("deleting");

		for (User u : data.getLoggedIn()) {
			if (u.getUsername().equals(user)) {
				data.getLoggedIn().remove(u);
				return "sucess";
			}
		}
		return "error";

	}
}
