/**
 * 
 */
package DataTransport;

import DataModel.ModelContact;

/**
 * @author Yannick Lanz
 *
 */
public class Registration extends AdminStream {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3562864960380657226L;
	private String login;
	private String pass;
	
	/**
	 * 
	 */
	public Registration(String login, String pass) {
		super(new ModelContact(-1, login, pass));
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

}
