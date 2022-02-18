package barcode.exception;

/**
 * this class shows the exception if server returns errorCode 01
 */
public class InvalidBarcodeException extends BarcodeException {
    public InvalidBarcodeException() {
        super("ERROR: Invalid barcode!");
    }
}
