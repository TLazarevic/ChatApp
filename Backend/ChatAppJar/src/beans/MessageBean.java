package beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.CustomMessage;
import model.User;

@Stateless
@Path("/messages")
@LocalBean // sad restendpointi ne moraju biti u remote interfejsu
//prima rest i prepakuje poruku u jms poruku 1 korak
public class MessageBean {

	@EJB
	Data data; // database for registered users and messages


	@POST
	@Path("/all")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response sendToAll(CustomMessage message) throws JsonMappingException, JsonProcessingException {
//		ObjectMapper mapper = new ObjectMapper();
//		CustomMessage message = mapper.readValue(recievedMessage, CustomMessage.class);
		
		List<CustomMessage> temp = new ArrayList<>();
		for (User u : data.getLoggedIn()) {
			try {
				if ( data.getUserMessages().get(u.getUsername())!=null) {
					temp = data.getUserMessages().get(u.getUsername());
					temp.add(message);
					data.getUserMessages().put(u.getUsername(), temp);
				}
				else {
					temp= new ArrayList<>();
					temp.add(message);
					data.getUserMessages().put(u.getUsername(), temp);
				}

			}
			catch (Exception e){
				e.printStackTrace();
				return Response.status(Response.Status.BAD_REQUEST).build();

			}
			
		}

		return Response.status(Response.Status.OK).build();

	}

	@POST
	@Path("/user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response sendToUser(CustomMessage message) {
		
//		ObjectMapper mapper = new ObjectMapper();
//		CustomMessage message;
//		try {
//			message = mapper.readValue(recievedMessage, CustomMessage.class);
//			System.out.println(recievedMessage);
			
			try {
				if (data.getUserMessages().get(message.getReciever().getUsername())!=null) {
					List<CustomMessage> temp = data.getUserMessages().get(message.getReciever().getUsername());
					temp.add(message);
					data.getUserMessages().put(message.getReciever().getUsername(), temp);
	
					return Response.status(Response.Status.OK).build();
				}
				else {
					List<CustomMessage> temp = new ArrayList<>();
					temp.add(message);
					data.getUserMessages().put(message.getReciever().getUsername(), temp);
					return Response.status(Response.Status.OK).build();
				}
				
			}
			catch (Exception e){
				e.printStackTrace();
			}
//			
//
//		} catch (JsonMappingException e1) {
//			e1.printStackTrace();
//		} catch (JsonProcessingException e1) {
//			e1.printStackTrace();
//		}
		return Response.status(Response.Status.BAD_REQUEST).build();
		
	}

	@GET
	@Path("/{user}")
	public List<CustomMessage> getAllMessages(@PathParam("user") String user) {
		try {
			
			if (data.getUserMessages().get(user)!=null)
				return data.getUserMessages().get(user);
			else return new ArrayList<CustomMessage>();
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<CustomMessage>();
		}
	}

}
