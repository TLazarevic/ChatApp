package beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;

import model.User;

@Stateless
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
	public User login(User user) {
		System.out.println(registered);
		System.out.println(user.getUsername());
		if(registered.contains(user)) 
		{
			System.out.println("Sucessfully logged in "+user.getUsername());
			loggedIn.add(user);
			return user;
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
		return this.loggedIn;
	}
	
	@GET
	@Path("/registered")
	public List<User> registered() {
		return this.registered;
	}
}
