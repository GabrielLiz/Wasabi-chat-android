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
	
	/**
	 * 
	 */
	public Container(Stream stream) {
		this.stream = stream;
	}
	
	public Stream getStream(){
		return stream;
	}
	
	public void setStream(Stream stream){
		this.stream = stream;
	}
}
