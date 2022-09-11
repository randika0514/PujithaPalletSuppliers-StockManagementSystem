package model;

public class SupplierOrder {
    private String supOrderId;
    private String supId;
    private String item;
    private String date;

    public SupplierOrder() {
    }

    public SupplierOrder(String supOrderId, String supId, String item, String date) {
        this.supOrderId = supOrderId;
        this.supId = supId;
        this.item = item;
        this.date = date;
    }

    public String getSupOrderId() {
        return supOrderId;
    }

    public void setSupOrderId(String supOrderId) {
        this.supOrderId = supOrderId;
    }

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SupplierOrder{" +
                "supOrderId='" + supOrderId + '\'' +
                ", supId='" + supId + '\'' +
                ", item='" + item + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
