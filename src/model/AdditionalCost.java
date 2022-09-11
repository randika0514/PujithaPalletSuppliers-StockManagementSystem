package model;

public class AdditionalCost {
    private String id;
    private String description;
    private String date;
    private double cost;

    public AdditionalCost() {
    }

    public AdditionalCost(String id, String description, String date, double cost) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "AdditionalCost{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", cost=" + cost +
                '}';
    }
}
