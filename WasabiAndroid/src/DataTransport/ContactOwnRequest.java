/**
 * 
 */
package DataTransport;

import DataModel.ModelContact;

/**
 * @author Yannick Lanz
 *
 */
public class ContactOwnRequest extends AdminStream {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4061347974327905142L;

	/**
	 * 
	 */
	public ContactOwnRequest(ModelContact owner) {
		super(owner);
	}

}
