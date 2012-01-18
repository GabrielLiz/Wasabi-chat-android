/**
 * 
 */
package DataTransport;

import DataModel.ModelContact;

/**
 * @author Yannick Lanz
 *
 */
public class Authentification extends AdminStream {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8555975845720419348L;
	private String pass;

	/**
	 * 
	 */
	public Authentification(ModelContact owner, String pass) {
		super(owner);
		this.pass = pass;
	}
	
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
}
