package beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import data.NetworkData;

@Singleton
@LocalBean
@Path("")
@Startup
public class SessionBean {

	@EJB NetworkData data;

	
	@PostConstruct
	public void postConstruct() {
		
		System.out.println("Created Host Agent!");
		
		InetAddress inetAddress;
		
		try {
			
			Host node=new Host();
			inetAddress = InetAddress.getLocalHost();
			
			node.setAdress(inetAddress.getHostAddress());
			node.setAlias(inetAddress.getHostName()+data.getCounter());
			
			System.out.println("IP Address:- " + node.getAdress()+" alias: "+node.getAlias());
			
			doTest(node.getAdress());
			
			
			if(data.getMaster()==null) {
				data.setMaster(node);
			}
			else {
				data.getNodes().add(node);
				System.out.println("slave created");
				
				//handshake();
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void doTest(String adress) {
		
		
//		  System.out.println("TESTING"); ResteasyClient client = new
//		  ResteasyClientBuilder().build(); ResteasyWebTarget target =
//		  client.target("http://"+adress+":8080/ChatAppWar/rest/users/test"); Response
//		  response = target.request().get(); String ret =
//		  response.readEntity(String.class); System.out.println(ret); client.close();
//		  System.out.println("DONE");
		 
	
	}
	
	public void handshake() {
		
	}
	
	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "ok";
	}
	
	@POST
	@Path("/register ")
	public String register() {
		return "OK";
	}
	
	@POST
	@Path("/node")
	public String node() {
		return "OK";
	}
	
	@POST
	@Path("/nodes")
	public String nodes() {
		return "OK";
	}
	
	
	@DELETE
	@Path("/node/{alias}")
	public String delete() {
		return "OK";
	}
	
	
	
}
