import java.util.ArrayList;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CoffeeMachine implements NotificationListener{

    private Stock stock;
    private DrinkList drinksMenu;
    private ArrayList<String> taskNameList;

    public static void main(String[] args){
        CoffeeMachine mCoffeeMachine = new CoffeeMachine();

        mCoffeeMachine.init();
        mCoffeeMachine.processDrink();
    }

    /* 1.Initializes Stock, DrinkList class
       2. Parse input and fill Stock data, Drink Ingredient data
       3. Registers listener to receive notification about low stock data
       4. Exit the program in case of error
     */
    public void init(){
         stock = Stock.getInstance();
         drinksMenu = DrinkList.getInstance();
         taskNameList = new ArrayList<>();

        try {
            InputParser.parse(stock, drinksMenu, taskNameList);
        } catch (Exception ex){
            ex.printStackTrace();
            System.exit(-1);
        }

        if(InputParser.outletCount == null || InputParser.outletCount == 0){
            System.out.println("No outlet present, can't make drink");
            System.exit(-1);
        } else if (stock.isEmpty()){
            System.out.println("Empty Stock, Can't make drink");
            System.exit(-1);
        }

        Stock.registerListener(this);
    }

    /* 1. taskNameList : list of drinks to be made. This can be taken as user input for further customisation.
          For now, this is the list of beverages in the input file in order.
       2. Create as many threads as outlet count, to process parallelly.
     */
    public void processDrink(){
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(InputParser.outletCount);

        for (String bevName : taskNameList) {
            Ingredients ingredients = drinksMenu.getIngredients(bevName);
            if(ingredients != null)
                executor.execute(new DrinkProcessor(stock, bevName, ingredients));
        }

        executor.shutdown();
    }

    /*
        To show low stock notification
     */
    @Override
    public void onLowStock(String name, Integer quantity) {
        System.out.println(name + " quantity is running low : " + quantity);
    }
}
