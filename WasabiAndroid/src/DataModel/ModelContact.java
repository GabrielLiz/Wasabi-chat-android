/**
 * 
 */
package DataModel;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * @author Yannick Lanz
 *
 */
public class ModelContact implements Serializable {

	private static final long serialVersionUID = -4607187837609436641L;
	
	private int id;
	private String login;
	private String password;

	public ModelContact(int id, String login, String password){
		this.id = id;
		this.login = login;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}

	public synchronized LinkedList<Integer> getConversationsIds() {
		return null;
	}
	
	
}
