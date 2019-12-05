package edu.iastate.ballinonabudget.Objects;

/*What are items in this app
They are purchases
They have the following qualities
Name
Price
Type
Hyperlink (Optional)
* */
public class Items {
    private String purchaseName;
    private double purchaseAmount;
    private String purchaseCategory;
    private boolean recurring;
    private String hyperlink;
    private String purchaseMonth;
    private String purchaseYear;

    public Items(String name, double amount, String category, String link, boolean recurring, String purchaseMonth, String purchaseYear) {
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

    public boolean isRecurring() {
        return recurring;
    }

    public String getPurchaseMonth() {
        return purchaseMonth;
    }

    public String getPurchaseYear() {
        return purchaseYear;
    }
}
