package edu.iastate.ballinonabudget.Objects;
/**
 * @author: Caroline Rankin (crrankin)
 * This class represents a monthly budget
 * that allows a user to create a name, amount, and items.
 * Each budget has a unique identifier.
 */

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import edu.iastate.ballinonabudget.DatabaseConfig.BudgetTypeConverters;

@Entity(tableName = "budget")
public class Budget {

    @PrimaryKey(autoGenerate = true)
    private int uid; //id is autogenerated in case that a user makes budgets with the same name

    @ColumnInfo(name = "name")
    private String name; //name of the budget. Eg: "May Budget"

    @ColumnInfo(name = "total_amount")
    private double totalAmount; //total amount for the entire budget

    @ColumnInfo(name = "items")
    @TypeConverters(BudgetTypeConverters.class)
    private List<Items> items; //list of the purchases/items in the budget

    /**
     * Constructs a budget
     * @param name
     * @param totalAmount
     * @param items
     */
    public Budget(String name, double totalAmount, List<Items> items){
        this.name = name;
        this.totalAmount = totalAmount;
        this.items = items;
    }

    /*
        Getters and Setters for private variables
     */
    public int getUid(){
        return this.uid;
    }

    public String getName(){
        return this.name;
    }

    public double getTotalAmount(){
        return this.totalAmount;
    }

    public List<Items> getItems() {
        return this.items;
    }

    public void  setUid(int uid) {
        this.uid = uid;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setTotalAmount(double totalAmount){
        this.totalAmount = totalAmount;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

}