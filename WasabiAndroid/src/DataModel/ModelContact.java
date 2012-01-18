/**
 * 
 */
package DataModel;

import java.io.Serializable;

/**
 * @author Yannick Lanz
 *
 */
public class ModelContact implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4607187837609436641L;
	private int id;
	private String login;

	/**
	 * 
	 */
	public ModelContact() { }
	
	public ModelContact(int id, String login) {
		this.id = id;
		this.login = login;
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

}
