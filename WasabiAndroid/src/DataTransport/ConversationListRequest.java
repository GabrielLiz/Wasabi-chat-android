/**
 * 
 */
package DataTransport;

import DataModel.ModelContact;

/**
 * @author Yannick Lanz
 *
 */
public class ConversationListRequest extends AdminStream {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5952977044432633468L;
	
	/**
	 * @param ownContact
	 */
	public ConversationListRequest(ModelContact owner) {
		super(owner);
	}

}
