import java.util.ArrayList;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CoffeeMachine implements NotificationListener{

    Stock stock;
    DrinkList taskList;
    ArrayList<String> taskNameList;

    public static void main(String[] args){
        CoffeeMachine mCoffeeMachine = new CoffeeMachine();

        mCoffeeMachine.init();
        mCoffeeMachine.processDrink();
    }

    public void init(){
         stock = Stock.getInstance();
         taskList = DrinkList.getInstance();
         taskNameList = new ArrayList<>();

        try {
            InputParser.parse(stock, taskList, taskNameList);
        } catch (Exception ex){
            System.out.println(ex);
        }

        Stock.registerListener(this);
    }

    public void processDrink(){
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(InputParser.outletCount);

        for (String bevName : taskNameList) {
            Ingredients ingredients = taskList.getIngredients(bevName);
            if(ingredients != null)
                executor.execute(new DrinkProcessor(stock, bevName, ingredients));
        }

        executor.shutdown();
    }

    @Override
    public void onLowStock(String name, Integer quantity) {
        System.out.println(name + " quantity is running low : " + quantity);
    }
}
