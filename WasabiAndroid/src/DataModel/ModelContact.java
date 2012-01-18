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
	private String pass;

	public ModelContact(int id, String login) {
		this.id = id;
		this.login = login;
		this.pass = null;
	}
	
	public ModelContact(int id, String login, String pass){
		this(id, login);
		this.pass = pass;
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
	
	public String getPass(){
		return pass;
	}
	
	public void setPass(String pass){
		this.pass = pass;
	}

	public synchronized LinkedList<Integer> getConversationsIds() {
		return null;
	}
	
	
}
