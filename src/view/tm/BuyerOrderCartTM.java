package view.tm;

import javafx.scene.control.Button;

public class BuyerOrderCartTM {
    private String palletId;
    private String size;
    private double rate;
    private int qty;
    private double totalCost;
    private Button btn;

    public BuyerOrderCartTM() {
    }

    public BuyerOrderCartTM(String palletId, String size, double rate, int qty, double totalCost, Button btn) {
        this.palletId = palletId;
        this.size = size;
        this.rate = rate;
        this.qty = qty;
        this.totalCost = totalCost;
        this.btn = btn;
    }

    public String getPalletId() {
        return palletId;
    }

    public void setPalletId(String palletId) {
        this.palletId = palletId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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
        return "BuyerOrderCartTM{" +
                "palletId='" + palletId + '\'' +
                ", size='" + size + '\'' +
                ", rate=" + rate +
                ", qty=" + qty +
                ", totalCost=" + totalCost +
                ", btn=" + btn +
                '}';
    }
}
