/**
 * 
 */
package DataTransport;

import DataModel.ModelConversation;

/**
 * @author Yannick Lanz
 *
 */
public class Conversation extends AdminStream {

	private ModelConversation conv;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4479215220670353994L;

	/**
	 * 
	 */
	public Conversation() { }
	
	public Conversation(ModelConversation conv){
		this.conv = conv;
	}

	public ModelConversation getConv() {
		return conv;
	}

	public void setConv(ModelConversation conv) {
		this.conv = conv;
	}
}
