/**
 * 
 */
package DataTransport;

import java.util.ArrayList;
import java.util.Iterator;

import DataModel.ModelContact;

/**
 * @author Yannick Lanz
 *
 */
public class ConversationCreationRequest extends AdminStream {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2124004551444087048L;
	private ArrayList<Integer> ids = new ArrayList<Integer>();

	/**
	 * @param contact
	 */
	public ConversationCreationRequest(ModelContact owner) {
		super(owner);
	}
	
	public ConversationCreationRequest(ModelContact owner, ModelContact contact) {
		super(owner);
		ids.add(contact.getId());
	}
	
	public ConversationCreationRequest(ModelContact owner, ArrayList<ModelContact> contacts){
		super(owner);
		addContacts(contacts);
	}
	
	public void addContact(ModelContact contact){
		ids.add(contact.getId());
	}
	
	public void addContacts(ArrayList<ModelContact> contacts){
		Iterator<ModelContact> i = contacts.iterator();
		while(i.hasNext()){
			ids.add(i.next().getId());
		}
	}

}
