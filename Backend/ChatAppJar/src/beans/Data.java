package beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Singleton;

import model.CustomMessage;
import model.User;

/**
 * Session Bean implementation class CachedAgents
 */
@Singleton
@LocalBean
public class Data{

	List<User> registered = new ArrayList<User> ();
	HashMap<String,List<CustomMessage>> userMessages;

	/**
	 * Default constructor.
	 */
	public Data() {

	}

	public List<User> getLoggedIn() {
		return registered;
	}

	public void addUser(String key, User user) {
		registered.add(user);
	}

}
