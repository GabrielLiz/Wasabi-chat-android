/**
 * 
 */
package DataTransport;

import DataModel.ModelContact;

/**
 * @author Yannick Lanz
 *
 */
public class ContactOwnResponse extends AdminStream {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6978970486055661381L;

	/**
	 * 
	 */
	
	public ContactOwnResponse(){
		super();
	}
	
	public ContactOwnResponse(ModelContact ownContact){
		super(ownContact);
	}
}
