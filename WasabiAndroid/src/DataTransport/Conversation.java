/**
 * 
 */
package DataTransport;

import DataModel.ModelContact;
import DataModel.ModelConversation;

/**
 * @author Yannick Lanz
 *
 */
public class Conversation extends AdminStream {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4479215220670353994L;
	private ModelConversation conversation;

	/**
	 * 
	 */
	
	public Conversation(ModelContact owner, ModelConversation conversation){
		super(owner);
		this.conversation = conversation;
	}

	public ModelConversation getConversation() {
		return conversation;
	}

	public void setConversation(ModelConversation conversation) {
		this.conversation = conversation;
	}
}
