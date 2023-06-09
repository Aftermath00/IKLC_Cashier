
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DBConnector.initDBConnection();
        Items.loadItemFromDB();

        ArrayList<Items> itemList;
        itemList = Items.itemList;

        Transaction transaction = new Transaction();
        TransactionDetail transactionDetail = new TransactionDetail();
        Credits credit = new Credits();
        Token token = new Token();

        float currentTotal = 0;

        int transactionID = transaction.generateRandomTransactionID();

        String tokenCode;
        String operator = " ";
        String phoneNumber = " ";
        String nominal = " ";

        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");

        Scanner sc = new Scanner(System.in);
        boolean exit = true;
        while (exit) {
            System.out.println("+----------------------------+");
            System.out.println("| IKLC Cashier (POSApp)      |");
            System.out.println("+----------------------------+");
            System.out.println("| 1. Food                    |");
            System.out.println("| 2. Top Up phone Credit     |");
            System.out.println("| 3. Electricity Token       |");
            System.out.println("| 4. Checkout                |");
            System.out.println("+----------------------------+");
            System.out.print("| Choose : ");
            int n = sc.nextInt();
            System.out.println();
            System.out.println();

            switch (n) {
                case 1 -> {
                    boolean backToFoodShelves = true;

                    while (backToFoodShelves) {
                        System.out.println("+---------------------+");
                        System.out.println("|    Food Shelves     |");
                        System.out.println("+---------------------+");
                        System.out.print("| Input Code (1-50) : ");
                        int foodCode = sc.nextInt();
                        System.out.println("+---------------------+");
                        Items tempItem = null;
                        boolean itemFound = false; // Add a boolean flag to track if the item is found

                        for (Items item : itemList) {
                            if (item.getCode().equals(Integer.toString(foodCode))) { // Convert foodCode to String for comparison
                                itemFound = true; // Set the flag to true

                                System.out.println("Item code     : " + item.getCode());
                                System.out.println("Product name  : " + item.getProductName());
                                System.out.println("Product price : " + decimalFormat.format(item.getPrice()));
                                tempItem = item;
                                System.out.println();
                                System.out.print("Amount : ");
                                int product_amount = sc.nextInt();

                                currentTotal = currentTotal + (product_amount * tempItem.getPrice());

                                transactionDetail.ItemIdList.add(Integer.valueOf(item.getCode()));
                                transactionDetail.ItemPriceList.add(currentTotal);
                                transactionDetail.ItemTotalList.add(product_amount);
                                transactionDetail.setDetailTransactionID(transactionID);
                                break;
                            }
                        }

                        if (!itemFound) {
                            System.out.println("Item with code " + foodCode + " not found in the database.");
                        }

                        System.out.print("Buy another food (y/n) : ");
                        String answer = sc.next();
                        backToFoodShelves = answer.equalsIgnoreCase("y");
                        exit = answer.equalsIgnoreCase("n");
                    }
                    System.out.println();
                }
                case 2 -> {
                    boolean backToCredit = true;

                    while (backToCredit) {
                        System.out.println("+-----------------------+");
                        System.out.println("| Top Up Phone Credits  |");
                        System.out.println("+-----------------------+");
                        System.out.println("| 1. Telkomsel          |");
                        System.out.println("| 2. Indosat            |");
                        System.out.println("| 3. Tri                |");
                        System.out.println("+-----------------------+");
                        System.out.print("| Choose Operator : ");
                        int operatorCode = sc.nextInt();
                        switch (operatorCode) {
                            case 1 -> operator = "Telkomsel";
                            case 2 -> operator = "Indosat";
                            case 3 -> operator = "Tri";
                        }
                        System.out.println("+--------------+");
                        System.out.println("| 1. 5.000     |");
                        System.out.println("| 2. 10.000    |");
                        System.out.println("| 3. 20.000    |");
                        System.out.println("| 4. 50.000    |");
                        System.out.println("| 5. 100.000   |");
                        System.out.println("+--------------+");
                        System.out.print("| Choose Nominal : ");
                        int nominalCode = sc.nextInt();
                        switch (nominalCode) {
                            case 1 -> nominal = "5000";
                            case 2 -> nominal = "10000";
                            case 3 -> nominal = "20000";
                            case 4 -> nominal = "50000";
                            case 5 -> nominal = "100000";
                        }
                        System.out.println("+----------------------------------+");
                        System.out.print("| Input your number : ");
                        phoneNumber = sc.next();
                        System.out.println("+----------------------------------+");
                        String creditCode = "Pulsa " + operator + " " + nominal;
                        Items tempItem = null;

                        boolean itemFound = false; // Add a boolean flag to track if the item is found

                        for (Items item : itemList) {
                            if (item.getProductName().equals(creditCode)) { // Convert foodCode to String for comparison
                                itemFound = true; // Set the flag to true

                                System.out.println("Item code     : " + item.getCode());
                                System.out.println("Product name  : " + item.getProductName());
                                System.out.println("Product price : " + decimalFormat.format(item.getPrice()));
                                tempItem = item;
                                int product_amount = 1;

                                currentTotal = currentTotal + (product_amount * tempItem.getPrice());
                                transactionDetail.ItemIdList.add(Integer.valueOf(item.getCode()));
                                transactionDetail.ItemPriceList.add(item.getPrice());
                                transactionDetail.ItemTotalList.add(product_amount);
                                transactionDetail.setDetailTransactionID(transactionID);

                                credit.setTransaction_id(transactionID);
                                credit.setOperator(operator);
                                credit.setNominal(Float.parseFloat(nominal));
                                credit.setItem_id(Integer.parseInt(item.getCode()));
                                credit.setPhoneNumber(phoneNumber);
                                credit.setPrice(item.getPrice());
                                credit.insertCredits(credit);
                                break;
                            }
                        }

                        if (!itemFound) {
                            System.out.println("Item with " + creditCode + " not found in the database.");
                        }

                        System.out.print("Buy another credits? (y/n) : ");
                        String answer = sc.next();
                        backToCredit = answer.equalsIgnoreCase("y");
                        exit = answer.equalsIgnoreCase("n");
                        System.out.println();
                    }
                }
                case 3 -> {
                    boolean backToToken = true;

                    while (backToToken) {
                        System.out.println("+----------------------------+");
                        System.out.println("|      Electricity Token     |");
                        System.out.println("+----------------------------+");
                        System.out.println("| 1. Token Listrik 20.000    |");
                        System.out.println("| 2. Token Listrik 50.000    |");
                        System.out.println("| 3. Token Listrik 100.000   |");
                        System.out.println("| 4. Token Listrik 200.000   |");
                        System.out.println("| 5. Token Listrik 500.000   |");
                        System.out.println("| 6. Token Listrik 1.000.000 |");
                        System.out.println("| 7. Token Listrik 2.000.000 |");
                        System.out.println("+----------------------------+");
                        System.out.println();
                        System.out.print("Input Electricity ID: ");
                        String electricityID = "";

                        boolean validInput = false;
                        while (!validInput) {
                            try {
                                electricityID = sc.next();
                                Long.parseLong(electricityID);  // Parse the input as a long to validate if it's a number
                                if (electricityID.length() != 11) {
                                    throw new IllegalArgumentException("Electricity ID must be 11 digits long.");
                                }
                                validInput = true;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a numeric value.");
                                System.out.print("Input Electricity ID: ");
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                                System.out.print("Input Electricity ID: ");
                            }
                        }

                        System.out.print("Choose Nominal : ");
                        int NominalCode = sc.nextInt();
                        switch (NominalCode) {
                            case 1 -> nominal = "20000";
                            case 2 -> nominal = "50000";
                            case 3 -> nominal = "100000";
                            case 4 -> nominal = "200000";
                            case 5 -> nominal = "500000";
                            case 6 -> nominal = "1000000";
                            case 7 -> nominal = "2000000";
                        }
                        System.out.println("+----------------------------------+");
                        String tokenName = "Token Listrik " + nominal;
                        Items tempItem = null;

                        boolean itemFound = false; // Add a boolean flag to track if the item is found

                        for (Items item : itemList) {
                            if (item.getProductName().equals(tokenName)) { // Convert foodCode to String for comparison
                                itemFound = true; // Set the flag to true

                                System.out.println("Item code     : " + item.getCode());
                                System.out.println("Product name  : " + item.getProductName());
                                System.out.println("Product price : " + decimalFormat.format(item.getPrice()));
                                tempItem = item;
                                int product_amount = 1;

                                Random random = new Random();
                                StringBuilder tokenCodeBuilder = new StringBuilder();

                                for (int j = 0; j < 20; j++) {
                                    int randomNumber = random.nextInt(10); // Generate numbers between 0 and 9
                                    tokenCodeBuilder.append(randomNumber);
                                }
                                tokenCode = tokenCodeBuilder.toString();
                                float nominalToken = item.getPrice() - (item.getPrice() * 11/100);

                                token.setTransaction_id(transactionID);
                                token.setElectricity_id(electricityID);
                                token.setNominal(nominalToken);
                                token.setItem_id(Integer.parseInt(item.getCode()));
                                token.setTokenCode(tokenCode);
                                token.setPrice(item.getPrice());
                                token.insertToken(token);

                                currentTotal = currentTotal + (product_amount * tempItem.getPrice());

                                transactionDetail.ItemIdList.add(Integer.valueOf(item.getCode()));
                                transactionDetail.ItemPriceList.add(item.getPrice());
                                transactionDetail.ItemTotalList.add(product_amount);
                                transactionDetail.setDetailTransactionID(transactionID);

                                break;
                            }
                        }

                        if (!itemFound) {
                            System.out.println("Item with name  " + tokenName + " not found in the database.");
                        }

                        System.out.print("Buy another credits? (y/n) : ");
                        String answer = sc.next();
                        backToToken = answer.equalsIgnoreCase("y");
                        exit = answer.equalsIgnoreCase("n");
                        System.out.println();
                    }
                }
                case 4 -> {
                    System.out.println("+-----------------------+");
                    System.out.println("|  IKLC Cashier Receipt |");
                    System.out.println("+-----------------------+");
                        for (int i = 0; i < transactionDetail.ItemIdList.size(); i++) {
                            int itemId = transactionDetail.ItemIdList.get(i);
                            float itemPrice = transactionDetail.ItemPriceList.get(i);
                            int itemTotal = transactionDetail.ItemTotalList.get(i);

                            for (Items item : itemList) {
                                if (item.getCode().equals(Integer.toString(itemId))) {
                                    System.out.println("| Item: " + item.getProductName());
                                    System.out.println("| Price: " + itemPrice);
                                    System.out.println("| Total: " + itemTotal);
                                    System.out.println("+-----------------------+");
                                }
                            }
                        }
                    float totalPaid = 0;
                    float change = 0;
                    boolean validAmount = false;

                        while (!validAmount) {
                            System.out.println("Total amount to pay: " + decimalFormat.format(currentTotal));
                            System.out.print("Total paid: ");
                            totalPaid = sc.nextFloat();
                            if (totalPaid >= currentTotal) {
                                change = totalPaid - currentTotal;
                                validAmount = true;
                            } else {
                                System.out.println("Insufficient payment. Please enter the correct amount.");
                            }
                        }

                    transaction.setChanges(change);
                    transaction.setTotal_paid(totalPaid);
                    transaction.setTransaction_id(transactionID);
                    transaction.setTotal_price(currentTotal);
                    transaction.insertTransactionData();
                    transactionDetail.insertTransactionDetail();

                    System.out.println("+-----------------------------+");
                    System.out.println("|       Receipt               |");
                    System.out.println("+-----------------------------+");
                    System.out.println("| Transaction ID : " + transactionID);
                    System.out.println("| Total Price    : " + decimalFormat.format(currentTotal));
                    System.out.println("| Total Paid     : " + decimalFormat.format(totalPaid));
                    System.out.println("| Change         : " + decimalFormat.format(change));
                    if (token.getTokenCode()!=null){
                        System.out.println("| Token Code     : " + token.getTokenCode());
                        System.out.println("| Nominal        : " + decimalFormat.format(token.getNominal()));
                    }
                    else if(credit.getPhoneNumber()!=null){
                        System.out.println("| Credits        : " + credit.getProductName());
                        System.out.println("| Number         : " + credit.getPhoneNumber());
                    }
                    else if(token.getTokenCode()!=null && credit.getPhoneNumber()!=null){
                        System.out.println("| Token Code     : " + token.getTokenCode());
                        System.out.println("| Nominal        : " + token.getNominal());
                        System.out.println("| Credits        : " + credit.getProductName());
                        System.out.println("| Number         : " + credit.getPhoneNumber());
                    }
                    System.out.println("+------------------------------+");

                    exit = false;
                }
                default -> System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }
}
