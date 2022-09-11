package view.tm;

import javafx.scene.control.Button;

public class SupplierOrderCartTM {
    private String materialId;
    private String type;
    private double rate;
    private int qty;
    private double totalCost;
    private Button btn;

    public SupplierOrderCartTM() {
    }

    public SupplierOrderCartTM(String materialId, String type, double rate, int qty, double totalCost, Button btn) {
        this.materialId = materialId;
        this.type = type;
        this.rate = rate;
        this.qty = qty;
        this.totalCost = totalCost;
        this.btn = btn;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "SupplierOrderCartTM{" +
                "materialId='" + materialId + '\'' +
                ", type='" + type + '\'' +
                ", rate=" + rate +
                ", qty=" + qty +
                ", totalCost=" + totalCost +
                ", btn=" + btn +
                '}';
    }
}
