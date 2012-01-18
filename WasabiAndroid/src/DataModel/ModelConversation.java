/**
 * 
 */
package DataModel;

import java.io.Serializable;

/**
 * @author Yannick Lanz
 *
 */
public abstract class ModelConversation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 984483949946037158L;
	private int id;
	private String msg;
	
	public ModelConversation(){ }
	
	public ModelConversation(int id){ 
		this.id = id;
	}
	
	public boolean isSimple(){
		return false;
	}
	
	public boolean isGroup(){
		return false;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getMsg(){
		return msg;
	}
	
	public void setMsg(String msg){
		this.msg = msg;
	}
}
