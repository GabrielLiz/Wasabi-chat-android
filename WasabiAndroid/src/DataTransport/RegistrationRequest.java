/**
 * 
 */
package DataTransport;

import DataModel.ModelContact;

/**
 * @author Yannick Lanz
 *
 */
public class RegistrationRequest extends AdminStream {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5640380714641290056L;

	/**
	 * @param owner
	 */
	public RegistrationRequest(String login, String password) {
		super(new ModelContact(-1, login, password));
	}
	
	
	public String getLogin() {
		return getOwner().getLogin();
	}

	public void setLogin(String login) {
		getOwner().setLogin(login);
	}

	public String getPassword() {
		return getOwner().getPassword();
	}

	public void setPassword(String password) {
		getOwner().setPassword(password);
	}

}
