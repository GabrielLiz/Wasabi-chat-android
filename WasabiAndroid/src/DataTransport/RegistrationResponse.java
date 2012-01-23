/**
 * 
 */
package DataTransport;

import DataModel.ModelContact;

/**
 * @author Yannick Lanz
 *
 */
public class RegistrationResponse extends AdminStream {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8541432905821282835L;
	private ResponseType responseType;

	/**
	 * @param owner
	 */
	public RegistrationResponse(ModelContact owner) {
		super(owner);
	}
	
	public ResponseType getResponseType() {
		return responseType;
	}
	
	public void setResponseType(ResponseType responseType) {
		this.responseType = responseType;
	}

	static public enum ResponseType{
		REG_ALREADY_EXISTS,
		REG_LOGIN_BAD_FORMAT,
		REG_PASS_BAD_FORMAT,
		REG_UNKNOW_ERROR,
		REG_SUCCESS
	}
}
