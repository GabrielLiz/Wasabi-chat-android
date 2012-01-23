/**
 * 
 */
package DataTransport;

import java.util.LinkedList;

import DataModel.ModelContact;
import DataModel.ModelConversation;

/**
 * @author Yannick Lanz
 *
 */
public class ConversationListResponse extends AdminStream {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7621845500341849628L;
	
	private LinkedList<ModelConversation> conversations;

	/**
	 * @param ownContact
	 */
	public ConversationListResponse(ModelContact owner) {
		super(owner);
		conversations = new LinkedList<ModelConversation>();
	}
	
	public ConversationListResponse(ModelContact owner, LinkedList<ModelConversation> conversations) {
		super(owner);
		this.conversations = conversations;
	}
	
	public void addConversation(ModelConversation conversation){
		this.conversations.add(conversation);
	}
	
	public LinkedList<ModelConversation> getConversations(){
		return conversations;
	}

}
