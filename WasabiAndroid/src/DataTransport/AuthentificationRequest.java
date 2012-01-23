/**
 * 
 */
package DataTransport;

import DataModel.ModelContact;

/**
 * @author Yannick Lanz
 *
 */
public class AuthentificationRequest extends AdminStream {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8132700021987315331L;

	/**
	 * @param owner
	 */
	public AuthentificationRequest(ModelContact owner) {
		super(owner);
	}
	
	public AuthentificationRequest(String login, String password){
		super(new ModelContact(-1, login, password));
	}

}
