package com.techelevator;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

public class VendingMachineTest extends TestCase {


    @Test
    public void testAddFeedMoney() {
        VendingMachine vendingMachine = new VendingMachine();
        Assert.assertEquals(500, vendingMachine.addFeedMoney(5));
        Assert.assertEquals(1500, vendingMachine.addFeedMoney(10));

    }

    @Test
    public void testGetChange() {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.addFeedMoney(10);
        Assert.assertEquals(1000, vendingMachine.getChange());
        Assert.assertEquals(0, vendingMachine.getBalance());
    }

    @Test
    public void testGetProduct() throws Exception {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.loadInventory("vendingmachine.csv");
        vendingMachine.addFeedMoney(5);
        Assert.assertEquals(500,vendingMachine.getBalance());
        Item item = vendingMachine.getProduct("C4");
        Assert.assertEquals(500 - item.getPrice(),vendingMachine.getBalance());
        Assert.assertEquals("Heavy", item.getProductName());
        int count = item.getItemCount();
        item = vendingMachine.getProduct("C4");
        Assert.assertEquals(count - 1, item.getItemCount());

    }

    @Test
    public void testGetProductSoldOut () throws Exception {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.loadInventory("vendingmachine.csv");
        vendingMachine.addFeedMoney(10);
        vendingMachine.getProduct("B2");
        vendingMachine.getProduct("B2");
        vendingMachine.getProduct("B2");
        vendingMachine.getProduct("B2");
        vendingMachine.getProduct("B2");

        try {
            vendingMachine.getProduct("B2");
            fail("We expected invalid transaction exception");
        } catch (InvalidTransactionException e) {
            //expected
        }

    }
    @Test
    public void testGetProductOutOFMoney () throws Exception {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.loadInventory("vendingmachine.csv");
        vendingMachine.addFeedMoney(5);
        vendingMachine.getProduct("B2");
        vendingMachine.getProduct("B2");
        vendingMachine.getProduct("B2");


        try {
            vendingMachine.getProduct("B2");
            fail("We expected invalid transaction for insufficient funds");
        } catch (InvalidTransactionException e) {
            //expected
        }

    }

}