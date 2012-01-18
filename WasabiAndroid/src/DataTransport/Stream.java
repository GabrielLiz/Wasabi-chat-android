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
abstract public class Stream implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1982056651442244303L;
	
	private ModelContact ownContact;
	
	public Stream(){
		this.ownContact = null;
	}
	
	public Stream(ModelContact ownContact){
		this.ownContact = ownContact;
	}

	public ModelContact getOwnContact() {
		return ownContact;
	}

	public void setOwnContact(ModelContact ownContact) {
		this.ownContact = ownContact;
	}
}
