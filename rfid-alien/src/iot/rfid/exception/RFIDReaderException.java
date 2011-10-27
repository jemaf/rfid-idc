/**
 * 
 */
package iot.rfid.exception;

/**
 * @author lucas.meirelles
 *
 */
public class RFIDReaderException extends RuntimeException {
	public RFIDReaderException() {
		super();
	}
	public RFIDReaderException(String message) {
		super(message);
	}
	public RFIDReaderException(String message, Exception exception) {
		super(message, exception);
	}

}
