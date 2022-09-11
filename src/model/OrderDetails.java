package model;

public class OrderDetails {
    private String orderId;
    private String palletId;
    private int qty;
    private double rate;

    public OrderDetails() {
    }

    public OrderDetails(String orderId, String palletId, int qty, double rate) {
        this.orderId = orderId;
        this.palletId = palletId;
        this.qty = qty;
        this.rate = rate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPalletId() {
        return palletId;
    }

    public void setPalletId(String palletId) {
        this.palletId = palletId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderId='" + orderId + '\'' +
                ", palletId='" + palletId + '\'' +
                ", qty=" + qty +
                ", rate=" + rate +
                '}';
    }
}
