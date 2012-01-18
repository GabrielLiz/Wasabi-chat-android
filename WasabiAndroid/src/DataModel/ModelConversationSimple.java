/**
 * 
 */
package DataModel;

/**
 * @author Yannick Lanz
 *
 */
public class ModelConversationSimple extends ModelConversation {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7227731559059275780L;
	private String contact_login;
	private String own_login;
	
	/**
	 * 
	 */
	public ModelConversationSimple() {	}
	
	public ModelConversationSimple(int id){
		super(id);
	}
	
	public ModelConversationSimple(String contact_login){
		this.contact_login = contact_login;
	}
	
	public ModelConversationSimple(String own_login, String contact_login){
		this.contact_login = contact_login;
		this.own_login = own_login;
	}
	
	public boolean isSimple(){
		return true;
	}
	
	public boolean isGroup(){
		return false;
	}
	
	public String getContactLogin(){
		return contact_login;
	}
	
	public void setContactLogin(String contact_login){
		this.contact_login = contact_login;
	}
	
	public String getOwnLogin(){
		return own_login;
	}
	
	public void setOwnLogin(String own_login){
		this.own_login = own_login;
	}

}
