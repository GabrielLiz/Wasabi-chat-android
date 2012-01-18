/**
 * 
 */
package DataModel;

/**
 * @author Yannick Lanz
 *
 */
public class ModelConversationGroup extends ModelConversation {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2098877067148346780L;

	/**
	 * 
	 */
	public ModelConversationGroup() {
		// TODO Auto-generated constructor stub
	}

	public boolean isSimple(){
		return false;
	}
	
	public boolean isGroup(){
		return true;
	}
}
