package beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
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
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.User;

@Singleton
@Path("/users")
@LocalBean //sad restendpointi ne moraju biti u remote interfejsu
//prima rest i prepakuje poruku u jms poruku 1 korak
public class UserBean {
	
	List<User> loggedIn = new ArrayList<User>();
	List<User> registered = new ArrayList<User>();
	
	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;
	@Resource(mappedName = "java:jboss/exported/jms/queue/mojQueue")
	private Queue queue;
	
	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String login(User user) {
		System.out.println(registered);
		System.out.println(user.getUsername());
		if(registered.contains(user)) 
		{
			System.out.println("Sucessfully logged in "+user.getUsername());
			loggedIn.add(user);
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = null;
			try {
				jsonStr = mapper.writeValueAsString(user);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} 
			return jsonStr;
		}
		else {
			return null;
		}
		
	}
	
	@POST
	@Path("/register")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public User register(User user) {
		
		for(User u : registered) {
			if (u.getUsername().equals(user.getUsername()))
				return null;
		}
		System.out.println("Sucessfully registered"+user.getUsername());
		registered.add(user);
		return user;
	}
	
	@GET
	@Path("/loggedIn")
	public List<User> loggedIn() {
		System.out.println("loggedIn"+loggedIn);
		return this.loggedIn;
	}
	
	@GET
	@Path("/registered")
	public List<User> registered() {
		System.out.println("registered"+registered);
		return this.registered;
	}
	
	@DELETE
	@Path("/loggedIn/{user}")
	@Produces(MediaType.TEXT_PLAIN)
	public String delete(@PathParam("user") String user) {
		System.out.println("deleted hit");
		for(User u : loggedIn) {
			if(u.getUsername().equals(user)) {
				this.loggedIn.remove(u);
				return "sucess";
			}
		}
		return "error";
		
	}
}
