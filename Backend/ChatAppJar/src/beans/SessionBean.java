package beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.vfs.VirtualFile;

import data.Data;
import data.NetworkData;
import java.net.URL;
import java.net.URLConnection;
import java.net.URISyntaxException;

@Singleton
@LocalBean
@Path("/host")
@Startup
public class SessionBean{

	@EJB
	NetworkData data;

	@EJB
	Data userData;

	private String masterAddress;

	@PostConstruct
	public void postConstruct() {
		System.out.println("Created Host Agent!");
		InetAddress inetAddress;
		try {
			Host node = new Host();
			inetAddress = InetAddress.getLocalHost();
			node.setAdress(inetAddress.getHostAddress());
			node.setAlias(inetAddress.getHostName() + data.getCounter());
			System.out.println("IP Address:- " + node.getAdress() + " alias: " + node.getAlias());
			// ovo je iz sieboga
			try {
				File f = getFile(SessionBean.class, "", "connections.properties");
				FileInputStream fileInput;
				fileInput = new FileInputStream(f);
				Properties properties = new Properties();
				try {
					properties.load(fileInput);
					fileInput.close();
					this.masterAddress = properties.getProperty("master");
					System.out.print(this.masterAddress);
					if (this.masterAddress == null || this.masterAddress.equals("")) {
						// TODO WRITE MASTER TO FILE
						System.out.println("master created");
						data.setMaster(node);
					} else {
						data.getNodes().add(node);
						System.out.println("slave created");
						handshake(node);
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

	public void handshake(Host host) {
		try {
			register(host);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Retrying handshake");
			try {
				register(host);
			}
			catch (Exception e1) {
				System.out.println("Handshake unsuccessful. Node not registered");
			}
		}
	}

	public void register(Host host) {
        System.out.println("Registering node:"); 
        ResteasyClient client = new
		  ResteasyClientBuilder().build(); ResteasyWebTarget target = client.target("http://"+this.masterAddress+":8080/ChatAppWar/rest/host/register"); 
		  if ((List<Host>) target.request().post(Entity.entity(host, "application/json")).getEntity()!=null)
			  data.setNodes( (List<Host>) target.request().post(Entity.entity(host, "application/json")).getEntity()); 
		  client.close();
		  System.out.println("Node registered");
		  System.out.println("Nodes set");
	}

	public void sendInfo(Host host) {
		// send info about new node to other nodes
		for (Host n : data.getNodes()) {
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target("http://" + n.getAdress() + ":8080/ChatAppWar/rest/host/node");
			Response response = target.request().post(Entity.entity(host, "application/json"));
			String ret = response.readEntity(String.class);
			System.out.println("sent new node to everyone");
			client.close();
		}
		// send loggedInUsers to new node
		ResteasyClient client1 = new ResteasyClientBuilder().build();
		ResteasyWebTarget target1 = client1
				.target("http://" + host.getAdress() + ":8080/ChatAppWar/rest/users/loggedIn");
		Response response1 = target1.request().post(Entity.entity(userData.getLoggedIn(), "application/json"));
		String ret1 = response1.readEntity(String.class);
		System.out.println("sent users to new node");
		client1.close();
	}

	public void postNodes(Host host) {
		
		try {
			throw new EmptyStackException();
//			ResteasyClient client1 = new ResteasyClientBuilder().build();
//			ResteasyWebTarget target1 = client1
//					.target("http://" + host.getAdress() + ":8080/ChatAppWar/rest/host/nodes");
//			Response response1 = target1.request().post(Entity.entity(data.getNodes(), "application/json"));
//			String ret1 = response1.readEntity(String.class);
//			System.out.println("Sent node info to new node");
//			client1.close();
//			sendInfo(host);
		} catch (Exception e) {
			try {
				throw new EmptyStackException();
//				ResteasyClient client1 = new ResteasyClientBuilder().build();
//				ResteasyWebTarget target1 = client1
//						.target("http://" + host.getAdress() + ":8080/ChatAppWar/rest/host/nodes");
//				
//				Response response1 = target1.request().post(Entity.entity(data.getNodes(), "application/json"));
//				String ret1 = response1.readEntity(String.class);
//				System.out.println("Sent node info to new node");
//				client1.close();
//				sendInfo(host);
			} catch (Exception e1) {
				System.out.println("Handshake unsuccessful: Roll-back");
				delete(host);

			}
		}
	}

	public void delete(Host host) {
		System.out.println("Deleting node from hosts:");
		//data.getNodes().remove(host);
		for (int i = 0; i < data.getNodes().size(); i++) {
			System.out.println(i+1 + "/" + data.getNodes().size());
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target("http://" + host.getAdress() + ":8080/ChatAppWar/rest/host/node/"+host.getAlias());
			Response response = target.request().delete();
			String ret = response.readEntity(String.class);
			System.out.println("deleted node from "+ host.getAlias());
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/register")
	public List<Host> registerNode(Host host) {
		new Thread(new Runnable() {
			public void run() {
				
				data.getNodes().add(host);
				System.out.println("node registered");
				postNodes(host);
			}
		}).start();
		return data.getNodes();
	}

	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/node")
	public String node(Host host) {
		System.out.println("New node: " +host);
		data.getNodes().add(host);
		return "OK";
	}

	@POST
	@Path("/nodes")
	@Consumes(MediaType.APPLICATION_JSON)
	public String nodes(List<Host> nodes) {
		data.setNodes(nodes);
		return "OK";
	}

	@DELETE
	@Path("/node/{alias}")
	public String delete(@PathParam("alias")String host) {
		Iterator i =data.getNodes().iterator();
		Host node;
		while (i.hasNext()) {
	         node = (Host) i.next();
	         if (node.getAlias().equals(host)) {
	            i.remove();
	            System.out.println("\n Node has been removed");
	            break;
	         }
	      }
		return "OK";
	}

	public static File getFile(Class<?> c, String prefix, String fileName) {
		File f = null;
		URL url = c.getResource(prefix + fileName);
		if (url != null) {
			if (url.toString().startsWith("vfs:/")) {
				try {
					URLConnection conn = new URL(url.toString()).openConnection();
					VirtualFile vf = (VirtualFile) conn.getContent();
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

}