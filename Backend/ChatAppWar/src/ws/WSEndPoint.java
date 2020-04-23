package ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@Singleton
@ServerEndpoint("/ws")
@LocalBean
//salje svim klijentima 3 korak
public class WSEndPoint {
	
	static List<Session> sessions = new ArrayList<Session>();
	
	@OnOpen
	public void onOpen(Session session) {
		if(!sessions.contains(session))
			sessions.add(session);
	}
	
	@OnMessage
	public void echoTextMessage(String msg) {
		try {
			for (Session s : sessions) {
				s.getBasicRemote().sendText(msg);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
