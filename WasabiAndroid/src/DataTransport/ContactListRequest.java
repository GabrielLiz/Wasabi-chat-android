/**
 * 
 */
package DataTransport;

import DataModel.ModelContact;

/**
 * @author Yannick Lanz
 *
 */
public class ContactListRequest extends AdminStream {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2644993349013376272L;

	/**
	 * 
	 */
	public ContactListRequest(ModelContact owner) {
		super(owner);
	}
	

}
