package edu.iastate.ballinonabudget.Objects;

import androidx.annotation.NonNull;

import java.io.Serializable;

/*What are items in this app
They are purchases
They have the following qualities
Name
Price
Type
Hyperlink (Optional)
* */
public class Items implements Serializable {
    private String purchaseName;
    private double purchaseAmount;
    private String purchaseCategory;
    private boolean recurring;
    private String hyperlink;
    private int purchaseMonth;
    private int purchaseYear;

    public Items(String name, double amount, String category, String link, boolean recurring, int purchaseMonth, int purchaseYear) {
        purchaseName = name;
        purchaseAmount = amount;
        purchaseCategory = category;
        hyperlink = link;
        this.recurring = recurring;
        this.purchaseMonth = purchaseMonth;
        this.purchaseYear = purchaseYear;
    }

    public String getPurchaseTitle(){
        return purchaseName;
    }

    public double getPurchaseAmount(){
        return purchaseAmount;
    }

    public String getCategory(){
        return purchaseCategory;
    }

    public String getHyperlink(){
        return hyperlink;
    }

    public void editPurchaseName(String newPurchaseName){
        purchaseName = newPurchaseName;
    }

    public void editPurchaseAmount(double newPurchaseAmount){
        purchaseAmount = newPurchaseAmount;
    }

    public void editCategory(String newCategory){
        purchaseCategory = newCategory;
    }

    public void editHyperLink(String newHyperLink){
        hyperlink = newHyperLink;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public int getPurchaseMonth() {
        return purchaseMonth;
    }

    public int getPurchaseYear() {
        return purchaseYear;
    }

    @Override
    @NonNull
    public String toString() {
        return purchaseName + " ... " + purchaseAmount;
    }
}
