/**
 * 
 */
package DataTransport;

import DataModel.ModelContact;

/**
 * @author Yannick Lanz
 *
 */
public class ComStream extends Stream {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5606577684155435373L;
	
	
	public ComStream(ModelContact contact){
		super(contact);
	}
}
