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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.vfs.VirtualFile;
import data.NetworkData;
import java.net.URL;
import java.net.URLConnection;
import java.net.URISyntaxException;


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
			
			
			//ovo je iz sieboga
			try {
				File f = getFile(SessionBean.class, "", "connections.properties");
				FileInputStream fileInput;
				fileInput = new FileInputStream(f);
				Properties properties = new Properties();
				try {
					properties.load(fileInput);
					fileInput.close();
					String masterAddress=properties.getProperty("master");
					
					if(masterAddress==null || masterAddress=="") {
						//TODO WRITE MASTER TO FILE
						System.out.println("master created");
						data.setMaster(node);
					}
					else {
						data.getNodes().add(node);
						System.out.println("slave created");
						doTest(masterAddress);
						//handshake();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void doTest(String masterAddress) {
		
		  System.out.println("TESTING"); ResteasyClient client = new
		  ResteasyClientBuilder().build(); ResteasyWebTarget target =
		  client.target("http://"+masterAddress+":8080/ChatAppWar/rest/users/test"); 
		  Response response = target.request().get(); 
		  String ret = response.readEntity(String.class); System.out.println(ret); client.close();
		  System.out.println("DONE");
		 
	
	}
	
	public void handshake() {
		
	}
	
	public static File getFile(Class<?> c, String prefix, String fileName) {
		File f = null;
		
		URL url = c.getResource(prefix + fileName);
		
		if (url != null) {
			if (url.toString().startsWith("vfs:/")) {
				try {
					URLConnection conn = new URL(url.toString()).openConnection();
					VirtualFile vf = (VirtualFile)conn.getContent();
					f = vf.getPhysicalFile();
				} catch (Exception ex) {
					ex.printStackTrace();
					f = new File(".");
				}
			} else {
				try {
					f = new File(url.toURI());
				} catch (URISyntaxException e) {
					e.printStackTrace();
					f = new File(".");
				}
			}
		} else {
			f = new File(fileName);
		}
				
		return f;
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
