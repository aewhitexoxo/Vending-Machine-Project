package com.techelevator;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ItemTest extends TestCase {

    private Item item;
    private int itemCount;

    @Before
    public void setUp() {item = new Item("lays", 250, "hi"); this.itemCount = 5; }

    @Test
    public void testGetProductName() {

        Assert.assertEquals("lays", item.getProductName());

    }
    @Test
    public void testGetPrice() {
        Assert.assertEquals(250, item.getPrice());

    }
    @Test
    public void testGetDispenseMessage() {
        Assert.assertEquals("hi", item.getDispenseMessage());

    }
    @Test
    public void testGetItemCount() {
        Assert.assertEquals(5, item.getItemCount());


    }
    @Test
    public void testReduceItemCount() {
        Assert.assertEquals(5, item.getItemCount());
        item.reduceItemCount();
        Assert.assertEquals(4, item.getItemCount());

    }
}