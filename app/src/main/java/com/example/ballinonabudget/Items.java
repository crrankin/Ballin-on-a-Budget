package com.example.ballinonabudget;

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
    private String hyperlink;

    public Items(String name, double amount, String category, String link){
        purchaseName = name;
        purchaseAmount = amount;
        purchaseCategory = category;
        hyperlink = link;
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
    
}
