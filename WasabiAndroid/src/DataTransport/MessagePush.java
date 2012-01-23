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
public class MessagePush extends MessageSendRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8266942366496534884L;

	/**
	 * @param owner
	 */
	public MessagePush(ModelContact owner) {
		super(owner);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param owner
	 * @param message
	 */
	public MessagePush(ModelContact owner, ModelMessage message) {
		super(owner, message);
		// TODO Auto-generated constructor stub
	}

}
