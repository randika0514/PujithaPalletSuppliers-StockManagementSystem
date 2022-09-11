package model;

public class Order {
    private String orderId;
    private String buyerId;
    private String Date;

    public Order() {
    }

    public Order(String orderId, String buyerId, String date) {
        this.orderId = orderId;
        this.buyerId = buyerId;
        Date = date;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", Date='" + Date + '\'' +
                '}';
    }
}
