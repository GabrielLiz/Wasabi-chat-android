/**
 * 
 */
package DataTransport;

import DataModel.ModelContact;
import DataModel.ModelMessage;

/**
 * @author Yannick Lanz
 *
 */
public class MessageSendRequest extends AdminStream {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6593558527300430569L;
	private ModelMessage message;

	/**
	 * @param contact
	 */
	public MessageSendRequest(ModelContact owner) {
		super(owner);
		setModelMessage(null);
	}
	
	public MessageSendRequest(ModelContact owner, ModelMessage message){
		super(owner);
		setModelMessage(message); 
	}

	public ModelMessage getModelMessage() {
		return message;
	}

	public void setModelMessage(ModelMessage message) {
		this.message = message;
	}

}
