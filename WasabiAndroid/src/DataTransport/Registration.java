/**
 * 
 */
package DataTransport;

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
		this.login = login;
		this.pass = pass;
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
