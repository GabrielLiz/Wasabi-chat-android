/**
 * 
 */
package DataTransport;

import java.io.Serializable;

import DataModel.ModelContact;

/**
 * @author Yannick Lanz
 *
 */
public class Status extends AdminStream {

	public enum Value implements Serializable{
		OK,
		ERROR,
		CONTACT_FOUND,
		CONTACT_NOT_FOUND,
		CONTACT_ALREADY_EXISTS,
		CONTACT_CREATION_FAILED,
		CONTACT_AUTHENTIFICATION_SUCCESS,
		CONTACT_AUTHENTIFICATION_FAILED
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -7327069164383536077L;
	private Value value;
	/**
	 * 
	 */
	public Status(ModelContact owner) {
		super(owner);
		value = Value.OK;
	}
	
	public Status(ModelContact owner, Value value) {
		super(owner);
		this.value = value;
	}

	public Value getValue(){
		return value;
	}
	
	public void setValue(Value value){
		this.value = value;
	}
}
