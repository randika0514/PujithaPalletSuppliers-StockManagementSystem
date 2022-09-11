package model;

public class Material {
    private String matId;
    private String matItem;
    private double matRate;
    private int matQtyOnHand;

    public Material() {
    }

    public Material(String matId, String matItem, double matRate, int matQtyOnHand) {
        this.matId = matId;
        this.matItem = matItem;
        this.matRate = matRate;
        this.matQtyOnHand = matQtyOnHand;
    }

    public String getMatId() {
        return matId;
    }

    public void setMatId(String matId) {
        this.matId = matId;
    }

    public String getMatItem() {
        return matItem;
    }

    public void setMatItem(String matItem) {
        this.matItem = matItem;
    }

    public double getMatRate() {
        return matRate;
    }

    public void setMatRate(double matRate) {
        this.matRate = matRate;
    }

    public int getMatQtyOnHand() {
        return matQtyOnHand;
    }

    public void setMatQtyOnHand(int matQtyOnHand) {
        this.matQtyOnHand = matQtyOnHand;
    }

    @Override
    public String toString() {
        return "Material{" +
                "matId='" + matId + '\'' +
                ", matItem='" + matItem + '\'' +
                ", matRate=" + matRate +
                ", matQtyOnHand=" + matQtyOnHand +
                '}';
    }
}
