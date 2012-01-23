/**
 * 
 */
package DataTransport;

import DataModel.ModelContact;

/**
 * @author Yannick Lanz
 *
 */
abstract public class AdminStream extends Stream {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6863466225026237159L;
	
	public AdminStream(ModelContact owner){
		super(owner);
	}
}
