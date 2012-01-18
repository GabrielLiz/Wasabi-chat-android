/**
 * 
 */
package DataTransport;

/**
 * @author Yannick Lanz
 *
 */
public class Authentification extends AdminStream {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8555975845720419348L;
	private String login;
	private String pass;

	/**
	 * 
	 */
	public Authentification(String login, String pass) {
		this.login = login;
		this.pass = pass;
	}
	
	public Authentification() {}
	
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
