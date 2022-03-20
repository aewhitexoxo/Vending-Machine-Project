package com.techelevator;

public class Item {

    private String productName;
    private int price;
    private String dispenseMessage;
    private int itemCount;


    public Item(String productName, int price, String dispenseMessage) {
        this.productName = productName;
        this.price = price;
        this.dispenseMessage = dispenseMessage;
        this.itemCount = 5;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public String getDispenseMessage() {
        return dispenseMessage;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void reduceItemCount(){
        itemCount--;
    }
}
