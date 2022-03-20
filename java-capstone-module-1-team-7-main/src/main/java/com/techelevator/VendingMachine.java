package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class VendingMachine {
    private Map<String,Item> inventory = new TreeMap<>();
    private int balance;
    private int machineBalance;
    private int quarter = 25;
    private int dime = 10;
    private int nickel = 5;


    public int getBalance() {
    return balance;
}

    public Map<String, Item> getInventory() {
        return inventory;
    }


    public void loadInventory(String fileName) throws FileNotFoundException {
        File inputFile = new File(fileName);
        try (Scanner scanner = new Scanner(inputFile)) {
            while (scanner.hasNextLine()) {
                String lineFromFile = scanner.nextLine();
                String [] splits = lineFromFile.split("\\|");
                String slotIdentifier = splits[0];
                String itemName = splits[1];
                int price = (int)(Double.parseDouble(splits[2]) * 100);
                String category = splits[3];
                Item item = null;
                if (category.equalsIgnoreCase("chip")) {
                    item = new Chip(itemName, price);
                } else if(category.equalsIgnoreCase("candy")) {
                    item = new Candy(itemName, price);
                }  else if(category.equalsIgnoreCase("drink")) {
                    item = new Drink(itemName, price);
                }else {
                    item = new Gum(itemName, price);
                }
                inventory.put(slotIdentifier, item);
            }
        }

    }
    public int addFeedMoney(int feedMoney){
        balance += feedMoney * 100;
        return balance;
    }
    public int getMachineBalance(Item item) {
        machineBalance = balance;
        return machineBalance;
    }

    public int getChange(){
        int change = balance;
        balance = 0;
        return change;
    }
    public Item getProduct(String slotIdentifier) throws InvalidTransactionException {
        Item item = inventory.get(slotIdentifier);
        if (item == null) {

            throw new InvalidTransactionException("Invalid Slot Entered");
        }
        if (balance < item.getPrice()) {

            throw new InvalidTransactionException("Not enough funds available");
        }
        if (item.getItemCount() == 0){
            throw new InvalidTransactionException("SOLD OUT");
        }
        int cost = item.getPrice();
        balance -= cost;

        item.reduceItemCount();
        return item;
    }
    public String coins(int change){
        int quarterCount = 0;
        int dimeCount = 0;
        int nickelCount = 0;
        while(machineBalance >=25){
            machineBalance = machineBalance - quarter;
            quarterCount++;
        } while (machineBalance >=10) {
            machineBalance = machineBalance - dime;
            dimeCount++;
        } while (machineBalance >=5) {
            machineBalance = machineBalance - nickel;
            nickelCount++;
        }
        return String.format("Your change is : %d quarters, %d dimes, %d nickels %n", quarterCount, dimeCount, nickelCount);
    }
}
