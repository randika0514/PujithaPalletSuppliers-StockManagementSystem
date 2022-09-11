package model;

public class MaterialDetails {
    private String materialId;
    private String supOrderId;
    private int qty;
    private double rate;

    public MaterialDetails() {
    }

    public MaterialDetails(String materialId, String supOrderId, int qty, double rate) {
        this.materialId = materialId;
        this.supOrderId = supOrderId;
        this.qty = qty;
        this.rate = rate;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getSupOrderId() {
        return supOrderId;
    }

    public void setSupOrderId(String supOrderId) {
        this.supOrderId = supOrderId;
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
        return "MaterialDetails{" +
                "materialId='" + materialId + '\'' +
                ", supOrderId='" + supOrderId + '\'' +
                ", qty=" + qty +
                ", rate=" + rate +
                '}';
    }
}
