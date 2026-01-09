package dto;

import java.io.Serializable;

public class ToppingDTO implements Serializable {
    private int toppingId;
    private String toppingName;
    private int priceYen;

    public ToppingDTO() {}

    public ToppingDTO(int toppingId, String toppingName, int priceYen) {
        this.toppingId = toppingId;
        this.toppingName = toppingName;
        this.priceYen = priceYen;
    }

    public int getToppingId() { return toppingId; }
    public void setToppingId(int toppingId) { this.toppingId = toppingId; }

    public String getToppingName() { return toppingName; }
    public void setToppingName(String toppingName) { this.toppingName = toppingName; }

    public int getPriceYen() { return priceYen; }
    public void setPriceYen(int priceYen) { this.priceYen = priceYen; }
}
