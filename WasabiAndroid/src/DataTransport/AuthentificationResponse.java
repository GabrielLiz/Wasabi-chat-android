/**
 * 
 */
package DataTransport;

import DataModel.ModelContact;

/**
 * @author Yannick Lanz
 *
 */
public class AuthentificationResponse extends AdminStream {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4423345925421933944L;
	private ResponseType responseType;

	/**
	 * @param owner
	 */
	public AuthentificationResponse(ModelContact owner) {
		super(owner);
	}
	
	public ResponseType getResponseType() {
		return responseType;
	}
	
	public void setResponseType(ResponseType responseType) {
		this.responseType = responseType;
	}

	static public enum ResponseType{
		AUTH_FAIL,
		AUTH_SUCCESS
	}

}
