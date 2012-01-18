package DataModel;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * @author Yannick Lanz
 * 
 */
public abstract class ModelConversation implements Serializable {

	private static final long serialVersionUID = 984483949946037158L;

	private int id;
	private String title;

	private LinkedList<Integer> contactList;

	public ModelConversation() {

	}

	public ModelConversation(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
