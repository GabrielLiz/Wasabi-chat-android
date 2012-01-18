/**
 * 
 */
package DataTransport;

import DataModel.ModelContact;

/**
 * @author Yannick Lanz
 *
 */
public class ComStream extends Stream {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5606577684155435373L;
	
	private ModelContact destContact;

	/**
	 * 
	 */
	public ComStream() {
		super();
		this.destContact = null;
	}
	
	public ComStream(ModelContact ownContact){
		super(ownContact);
		this.destContact = null;
	}
	
	public ComStream(ModelContact ownContact, ModelContact destContact){
		super(ownContact);
		this.destContact = destContact;
	}

	public ModelContact getDestContact() {
		return destContact;
	}

	public void setDestContact(ModelContact destContact) {
		this.destContact = destContact;
	}

}
