package barcode;

import barcode.exception.InvalidBarcodeException;

import java.net.MalformedURLException;

/**
 * Interface processes barcodes
 */
public interface BarcodeInterface {
    /**
     * Process barcode and then return the bike's id
     * @param barcode barcode of bike
     * @return bike id
     * @throws InvalidBarcodeException if barcode not valid (barcode only contains 0, 1)
     * @throws MalformedURLException if can't recognize url
     */
    public abstract int processBarcode(String barcode) throws InvalidBarcodeException, MalformedURLException;
}
