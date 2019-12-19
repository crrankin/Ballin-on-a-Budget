package edu.iastate.ballinonabudget.Objects;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * What are items in this app
 * They are purchases
 * They have the following qualities:
 * Name, Price, Type, Hyperlink (Optional)
 */
public class Items implements Serializable {
    private String purchaseName;
    private double purchaseAmount;
    private String purchaseCategory;
    private boolean recurring;
    private String hyperlink;
    private int purchaseMonth;
    private int purchaseYear;

    /**
     * This creates a new item (or purchase)
     * @param name what you purchased
     * @param amount how much it cost
     * @param category category (or what budget it is in)
     * @param link string telling the user where they both their item
     * @param recurring if the purchase is a recurring purchase
     * @param purchaseMonth what month the purchase was made
     * @param purchaseYear the year the purchase was made
     */
    public Items(String name, double amount, String category, String link, boolean recurring, int purchaseMonth, int purchaseYear) {
        purchaseName = name;
        purchaseAmount = amount;
        purchaseCategory = category;
        hyperlink = link;
        this.recurring = recurring;
        this.purchaseMonth = purchaseMonth;
        this.purchaseYear = purchaseYear;
    }

    /**
     * Returns the name of the purchase
     * @return purchase name
     */
    public String getPurchaseTitle(){
        return purchaseName;
    }

    /**
     * Changes the name of the purchase
     * @return purchase name
     */
    public double getPurchaseAmount(){
        return purchaseAmount;
    }

    /**
     * Returns the category of the item
     * @return category of item
     */
    public String getCategory(){
        return purchaseCategory;
    }

    /**
     * Returns the hyperlink of the item
     * @return hyperlink of item
     */
    public String getHyperlink(){
        return hyperlink;
    }

    /**
     * Changes the name of the purchase
     */
    public void editPurchaseName(String newPurchaseName){
        purchaseName = newPurchaseName;
    }

    /**
     * Changes the purchase amount
     */
    public void editPurchaseAmount(double newPurchaseAmount){
        purchaseAmount = newPurchaseAmount;
    }

    /**
     * Changes the item/purchase's category
     */
    public void editCategory(String newCategory){
        purchaseCategory = newCategory;
    }

    /**
     * Change an item/purchase's hyperlink
     * @param newHyperLink new hyperlink
     */
    public void editHyperLink(String newHyperLink){
        hyperlink = newHyperLink;
    }

    /**
     * Returns if an item is a recurring purchase or not
     * @return recurring status
     */
    public boolean isRecurring() {
        return recurring;
    }

    /**
     * Returns item's purchase month
     * @return purchase month
     */
    public int getPurchaseMonth() {
        return purchaseMonth;
    }

    /**
     * Returns the year an item was purchased
     * @return purchase year
     */
    public int getPurchaseYear() {
        return purchaseYear;
    }

    /**
     * Return's the item's name with it's purchase amount
     * @return purchase info as a string
     */
    @Override
    @NonNull
    public String toString() {
        return purchaseName + " ... " + purchaseAmount;
    }
}
