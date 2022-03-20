package com.techelevator;

import com.techelevator.view.Menu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT };

	private static final String SECOND_MENU_FEED_MONEY = "Feed Money";
	private static final String SECOND_MENU_SELECT_PRODUCT = "Select Product";
	private static final String SECOND_MENU_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] SECOND_MENU_OPTIONS = { SECOND_MENU_FEED_MONEY, SECOND_MENU_SELECT_PRODUCT, SECOND_MENU_FINISH_TRANSACTION };
	private Scanner keyboard = new Scanner(System.in);

	private Menu menu;
	private static final Log logger = new Log("Log.txt");

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() throws FileNotFoundException {
		VendingMachine vendoMatic800 = new VendingMachine();
		vendoMatic800.loadInventory("vendingmachine.csv");


		boolean exitProgram = false;
		boolean start2ndMenu = false;
		while (!exitProgram) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				for(Map.Entry<String,Item> entry : vendoMatic800.getInventory().entrySet()) {
					System.out.printf("%s %s $%.2f - %d in stock%n", entry.getKey(), entry.getValue().getProductName(),penniesToDollars(entry.getValue().getPrice()), entry.getValue().getItemCount());
				}
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				start2ndMenu = true;
				while (start2ndMenu) {
					String choice2 = (String) menu.getChoiceFromOptions(SECOND_MENU_OPTIONS);

					if (choice2.equals(SECOND_MENU_FEED_MONEY)) {
						int feed = getFeedMoney();
						int oldBalance = vendoMatic800.getBalance();
						int newBalance = vendoMatic800.addFeedMoney(feed);
						System.out.printf("Current Money Provided: $%.2f%n",penniesToDollars(newBalance));
						String logMessage = String.format("%s FEED MONEY: $%.2f $%.2f", Log.insertDate(), penniesToDollars(oldBalance), penniesToDollars(newBalance));
						log(logMessage);
					} else if (choice2.equals(SECOND_MENU_SELECT_PRODUCT)) {
						for(Map.Entry<String,Item> entry : vendoMatic800.getInventory().entrySet()) {
							System.out.printf("%s %s $%.2f%n", entry.getKey(), entry.getValue().getProductName(),penniesToDollars(entry.getValue().getPrice()));
						}
						System.out.println("Make your selection ");
							String slotIdentifier = keyboard.nextLine().toUpperCase();
							int oldBalance = vendoMatic800.getBalance();
						try { Item item = vendoMatic800.getProduct(slotIdentifier);
							System.out.printf("You bought %s %s for $%.2f ~%s~ %d left in stock%n", slotIdentifier, item.getProductName(),penniesToDollars(item.getPrice()), item.getDispenseMessage(), item.getItemCount());
							System.out.printf("Your remaining balance is $%.2f%n",penniesToDollars(vendoMatic800.getMachineBalance(item)));
							String logMessage = String.format("%s %s %s $%.2f $%.2f", Log.insertDate(),
									item.getProductName(), slotIdentifier, penniesToDollars(oldBalance), penniesToDollars(vendoMatic800.getMachineBalance(item)));
							log(logMessage);
						} catch (InvalidTransactionException e) {
							System.out.println("Something went wrong " + e.getMessage());
						}
					} else if (choice2.equals(SECOND_MENU_FINISH_TRANSACTION)) {
						start2ndMenu = false;
						int change = vendoMatic800.getChange();
						System.out.printf("Your change is $%.2f%n",penniesToDollars(change));
						System.out.println(vendoMatic800.coins(change));
						String logMessage = String.format("%s GIVE CHANGE: $%.2f $%.2f", Log.insertDate(), penniesToDollars(change), penniesToDollars(vendoMatic800.getBalance()));
						log(logMessage);
					}
				}
			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				exitProgram = true;
			}
		}


	}

	private void log(String logMessage) {
		try {
			logger.write(logMessage);
		} catch (IOException e) {
			System.out.println("Couldn't log message");
		}
	}

	private int getFeedMoney() {
		System.out.println("How much money would you like to deposit? 1, 5 or 10 ");
		String choice = keyboard.nextLine();
		int feedBalance = 0;
		if (choice.equals("1")) {
			feedBalance += 1;
		} else if (choice.equals("5")) {
			feedBalance += 5;
		} else if (choice.equals("10")) {
			feedBalance += 10;
		} else {
			System.out.println("Money input invalid");
		}
		return feedBalance;
	}

	public static double penniesToDollars(int pennies) {
		double dollars = pennies/100.0;
		return dollars;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
