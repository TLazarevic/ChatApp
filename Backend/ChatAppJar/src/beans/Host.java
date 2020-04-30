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

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import data.NetworkData;

@Stateful
@LocalBean
@Path("")
@Startup
public class Host {

	@EJB NetworkData data;
	
	private String alias;
	private String adress;

	
	@PostConstruct
	public void postConstruct() {
		
		System.out.println("Created Host Agent!");
		
		InetAddress inetAddress;
		
		try {
			inetAddress = InetAddress.getLocalHost();
			
			this.adress= inetAddress.getHostAddress();
			this.alias=inetAddress.getHostName()+data.getCounter();
			
			System.out.println("IP Address:- " + this.adress+" alias: "+this.alias);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		//doTest();
		
		if(data.getMaster()==null) {
			data.setMaster(this);
		}
		else {
			data.getNodes().add(this);
			System.out.println("slave created");
			
			//handshake();
		}

	}
	
	public void doTest() {
		
		System.out.println("TESTING");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/ChatAppWar/rest/users/test");
		Response response = target.request().get();
    	String ret = response.readEntity(String.class);
    	System.out.println(ret);
		client.close();
		System.out.println("DONE");
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
	
	
	
	 public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

}
