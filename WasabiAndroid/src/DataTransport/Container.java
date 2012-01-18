/**
 * 
 */
package DataTransport;

import java.io.Serializable;

/**
 * @author Yannick Lanz
 *
 */
public class Container implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5253548840157737870L;
	private Stream stream;
	private String login;
	
	/**
	 * 
	 */
	
	public Container(Stream stream, String login) {
		this.stream = stream;
		this.login = login;
	}
	
	public Stream getStream(){
		return stream;
	}
	
	public void setStream(Stream stream){
		this.stream = stream;
	}
	
	public String getLogin(){
		return login;
	}
	
	public void setLogin(String login){
		this.login = login;
	}
}
