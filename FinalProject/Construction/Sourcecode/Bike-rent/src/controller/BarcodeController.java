package controller;

/**
 * This class manages operations related barcode.
 */
public class BarcodeController {

    /**
     * This class take the responsibility to make sure that barcode is right format given.
     * @param barcode String entered by user
     * @return true if barcode only contains numbers.
     */
    public boolean validateBarcode(String barcode) {
        try {
            if (barcode.length() != 10)
                return false;
            Integer.parseInt(barcode);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
