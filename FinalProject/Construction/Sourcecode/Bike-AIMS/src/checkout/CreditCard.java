package checkout;

public class CreditCard {
    /**
     * card number of our group's credit card
     */
    private String cardCode = "kstn_group8_2021";
    /**
     * card holder number of our group's credit card
     */
    private String owner = "Group 8";
    /**
     * cvv code of our group's credit card
     */
    private String cvvCode = "412";
    /**
     * expired date of our group's credit card
     */
    private String dateExpired = "1125";

    public CreditCard(String cardCode, String owner, String cvvCode, String dateExpired) {
        this.cardCode = cardCode;
        this.owner = owner;
        this.cvvCode = cvvCode;
        this.dateExpired = dateExpired;
    }

    public String getCardCode() {
        return cardCode;
    }

    public String getOwner() {
        return owner;
    }

    public String getCvvCode() {
        return cvvCode;
    }

    public String getDateExpired() {
        return dateExpired;
    }
}
