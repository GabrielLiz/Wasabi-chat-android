package DataModel;

import java.io.Serializable;
import java.sql.Timestamp;

public class ModelMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -460698461988164465L;
	private int id;
	private int ownerId;
	private int conversationId;
	private Timestamp date;
	private String message;
	
	public ModelMessage(String message, int conversationId) {
		this.message = message;
		this.conversationId = conversationId;
		this.id = -1;
		this.ownerId = -1;
	}
	
	public ModelMessage(String message, int conversationId, int ownerId, int messageId, Timestamp date) {
		this.message = message;
		this.conversationId = conversationId;
		this.id = messageId;
		this.ownerId = ownerId;
		this.date = date;
	}
	
	public int getConversationId(){
		return conversationId;
	}
	
	public void setConversationId(int id){
		conversationId = id;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getMessage(){
		return message;
	}
	
	public void setMessage(String message){
		this.message = message;
	}

	public Timestamp getDateTime() {
		return date;
	}

	public void setDateTime(Timestamp dateTime) {
		this.date = dateTime;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	
	
}
