/**
 * CRException - Help to Handle exception
 * 
 *  @author Danielle Monthe, Marie ...
 */
package exception;

public class CRException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3636880590924169063L;
	private String message = null;
	
	public CRException(String message) {
		super(message);
		this.message = message;
	}
	
	public CRException() {
		super();
	}
	
	// Getter and setter
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
