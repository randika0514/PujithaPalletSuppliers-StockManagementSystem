package model;

public class Buyer {
    private String buyerId;
    private String buyerName;
    private String buyerNic;
    private String buyerAddress;
    private String buyerContact;
    private String buyerEmail;

    public Buyer() {
    }

    public Buyer(String buyerId, String buyerName, String buyerNic, String buyerAddress, String buyerContact, String buyerEmail) {
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.buyerNic = buyerNic;
        this.buyerAddress = buyerAddress;
        this.buyerContact = buyerContact;
        this.buyerEmail = buyerEmail;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerNic() {
        return buyerNic;
    }

    public void setBuyerNic(String buyerNic) {
        this.buyerNic = buyerNic;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public String getBuyerContact() {
        return buyerContact;
    }

    public void setBuyerContact(String buyerContact) {
        this.buyerContact = buyerContact;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "buyerId='" + buyerId + '\'' +
                ", buyerName='" + buyerName + '\'' +
                ", buyerNic='" + buyerNic + '\'' +
                ", buyerAddress='" + buyerAddress + '\'' +
                ", buyerContact='" + buyerContact + '\'' +
                ", buyerEmail='" + buyerEmail + '\'' +
                '}';
    }
}
