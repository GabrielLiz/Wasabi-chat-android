package DataModel;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

public class ModelMessage implements Serializable {

	private int id;
	private String message;
	private Timestamp dateTime;
	
	private int conversationId;
	
	public ModelMessage(String message, Timestamp dateTime) {
		this.message = message;
		this.dateTime = dateTime;
	}
	
	
}
