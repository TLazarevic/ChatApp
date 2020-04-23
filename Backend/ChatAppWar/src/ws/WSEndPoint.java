package ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.CustomMessage;
import model.User;

@Singleton
@ServerEndpoint("/ws/{clientId}")
@LocalBean
//salje svim klijentima 3 korak
public class WSEndPoint {

	static Map<String, Session> sessions = new ConcurrentHashMap<>();
	
	@OnOpen
	public void onOpen(@PathParam("clientId") String clientId, Session session) {
//		if(!sessions.contains(session)) {
//			sessions.add(session);
//			}
		sessions.put(clientId, session);
		System.out.println("opened "+ clientId);
	}

	@OnMessage
	public void echoTextMessage(String msg) {

		ObjectMapper mapper = new ObjectMapper();
		CustomMessage obj;
		try {
			obj = mapper.readValue(msg, CustomMessage.class);
			System.out.println(msg);
			String reciever = obj.getReciever().getUsername();
			String author = obj.getAuthor().getUsername();

			if (!reciever.equals("all")) { 										//salji odredjenom korisniku i senderu da vidi
				try {
					//if(!sessions.get(reciever).equals(obj.getAuthor()))
						sessions.get(reciever).getBasicRemote().sendText(msg);
						sessions.get(author).getBasicRemote().sendText(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {  																//broadcast, salji svima
				for (Entry<String, Session> entry : sessions.entrySet()) {
					try {
						//if(!sessions.get(reciever).equals(obj.getAuthor()))
							((Session) entry.getValue()).getBasicRemote().sendText(msg);
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}

	}

//		try {
//			for (Session s : sessions) {
//				s.getBasicRemote().sendText(msg);
//				System.out.print("onmessage");
//			}
//		}catch(IOException e) {
//			e.printStackTrace();
//		}
	// }
}
