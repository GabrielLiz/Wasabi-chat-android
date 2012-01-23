/**
 * 
 */
package DataTransport;

import DataModel.ModelContact;

/**
 * @author Yannick Lanz
 *
 */
public class MessageSendResponse extends AdminStream {

	private ResponseType responseType;

	/**
	 * 
	 */
	private static final long serialVersionUID = -6038143299572613142L;

	/**
	 * @param contact
	 */
	public MessageSendResponse(ModelContact owner) {
		super(owner);
	}
	
	public ResponseType getResponseType() {
		return responseType;
	}

	public void setResponseType(ResponseType responseType) {
		this.responseType = responseType;
	}
	
	private enum ResponseType{
		MSG_SEND_SUCCESS,
		MSG_SEND_ERR_CONV_NOT_EXISTS
	}

}
