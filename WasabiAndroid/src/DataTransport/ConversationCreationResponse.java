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
public class ConversationCreationResponse extends AdminStream {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9002339977442250156L;
	private ModelConversation conversation;
	private ResponseType responseType;

	/**
	 * @param contact
	 */
	public ConversationCreationResponse(ModelContact owner) {
		super(owner);
		setConversation(null);
	}
	
	public ConversationCreationResponse(ModelContact owner, ModelConversation conversation){
		super(owner);
		this.setConversation(conversation);
	}

	public ModelConversation getConversation() {
		return conversation;
	}

	public void setConversation(ModelConversation conversation) {
		this.conversation = conversation;
	}
	
	

	public ResponseType getResponseType() {
		return responseType;
	}

	public void setResponseType(ResponseType responseType) {
		this.responseType = responseType;
	}

	private enum ResponseType{
		CONV_CREATION_SUCCESS,
		CONV_CREATION_FAIL
	}
}
