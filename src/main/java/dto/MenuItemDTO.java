package dto;

import java.io.Serializable;

public class MenuItemDTO implements Serializable {
    private int itemId;
    private int categoryId;
    private String categoryName;
    private String itemName;
    private int priceYen;
    private int isVisible; // 1=表示, 0=非表示

    public MenuItemDTO() {}

    public MenuItemDTO(int itemId, int categoryId, String categoryName, String itemName, int priceYen, int isVisible) {
        this.itemId = itemId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.itemName = itemName;
        this.priceYen = priceYen;
        this.isVisible = isVisible;
    }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public int getPriceYen() { return priceYen; }
    public void setPriceYen(int priceYen) { this.priceYen = priceYen; }

    public int getIsVisible() { return isVisible; }
    public void setIsVisible(int isVisible) { this.isVisible = isVisible; }

    public boolean isVisible() { return isVisible == 1; }
}
