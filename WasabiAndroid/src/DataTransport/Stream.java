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
	
	private ModelContact owner;
	
	public Stream(ModelContact owner){
		this.owner = owner;
	}

	public ModelContact getOwner() {
		return owner;
	}

	public void setOwnContact(ModelContact owner) {
		this.owner = owner;
	}
}
