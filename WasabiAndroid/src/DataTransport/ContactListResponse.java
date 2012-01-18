/**
 * 
 */
package DataTransport;

import java.util.LinkedList;

import DataModel.ModelContact;

/**
 * @author Yannick Lanz
 *
 */
public class ContactListResponse extends AdminStream {

	/**
	 * 
	 */
	private static final long serialVersionUID = -437562119289724001L;
	private LinkedList<ModelContact> contacts;
	/**
	 * 
	 */
	public ContactListResponse(ModelContact owner) {
		super(owner);
		contacts = new LinkedList<ModelContact>();
	}
	
	public ContactListResponse(ModelContact owner, LinkedList<ModelContact> contacts) {
		super(owner);
		this.contacts = contacts;
	}
	
	public Object[] getContactList(){
		return contacts.toArray();
	}
	
	public void addContact(ModelContact contact){
		contacts.add(contact);
	}
	
	public void setContactList(LinkedList<ModelContact> contacts){
		this.contacts = contacts;
	}
}
