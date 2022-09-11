package model;

public class Pallet {
    private String palletId;
    private String palletSize;
    private double palletRate;
    private int palletQtyOnHand;
    private String palletDesc;

    public Pallet() {
    }

    public Pallet(String palletId, String palletSize, double palletRate, int palletQtyOnHand, String palletDesc) {
        this.palletId = palletId;
        this.palletSize = palletSize;
        this.palletRate = palletRate;
        this.palletQtyOnHand = palletQtyOnHand;
        this.palletDesc = palletDesc;
    }

    public String getPalletId() {
        return palletId;
    }

    public void setPalletId(String palletId) {
        this.palletId = palletId;
    }

    public String getPalletSize() {
        return palletSize;
    }

    public void setPalletSize(String palletSize) {
        this.palletSize = palletSize;
    }

    public double getPalletRate() {
        return palletRate;
    }

    public void setPalletRate(double palletRate) {
        this.palletRate = palletRate;
    }

    public int getPalletQtyOnHand() {
        return palletQtyOnHand;
    }

    public void setPalletQtyOnHand(int palletQtyOnHand) {
        this.palletQtyOnHand = palletQtyOnHand;
    }

    public String getPalletDesc() {
        return palletDesc;
    }

    public void setPalletDesc(String palletDesc) {
        this.palletDesc = palletDesc;
    }

    @Override
    public String toString() {
        return "Pallet{" +
                "palletId='" + palletId + '\'' +
                ", palletSize='" + palletSize + '\'' +
                ", palletRate=" + palletRate +
                ", palletQtyOnHand=" + palletQtyOnHand +
                ", palletDesc='" + palletDesc + '\'' +
                '}';
    }
}
