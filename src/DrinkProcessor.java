import java.util.Set;
import java.util.concurrent.TimeUnit;

/*
    Runnable class, which processes the making of drink parallel
 */

public class DrinkProcessor implements  Runnable{

    private final String drinkName;     //Drink to be prepared

    private final Stock mStock;
    private final Ingredients ingredients;

    DrinkProcessor(Stock stock, String name, Ingredients items){
        mStock = stock;
        ingredients = items;
        drinkName = name;
    }

    @Override
    public void run() {
        boolean canMakeDrink = true;

        //Stock will be common for the drinks, so locking of the stock is required.
        synchronized (mStock){
            //System.out.println("Thread: " + Thread.currentThread().getId());

            Set<String> ingredientNames = ingredients.getIngredientsNames();    //get the ingredient list for this drink

            //Check the available quantity in stock, and what is required. If available is less- show err msg
            for(String name : ingredientNames){
                Integer stockQuant = mStock.getQuantity(name);
                Integer requiredQuant = ingredients.getQuantity(name);

                if(stockQuant < requiredQuant){
                    if(stockQuant == -1){
                        System.out.println(drinkName + " cannot be prepared because " + name + " is not available");
                    } else {
                        System.out.println(drinkName + " cannot be prepared because " + name + " is not sufficient");
                    }
                    canMakeDrink = false;
                    break;
                }
            }

            //Drink can be made, so update the stock quantity
            if(canMakeDrink){
                System.out.println(drinkName + " is prepared");
                for(String name : ingredientNames){
                    Integer newQuant = mStock.getQuantity(name) - ingredients.getQuantity(name);
                    mStock.setData(name, newQuant);

                    //If the stock quantity of the item is less than threshold - notify
                    if(newQuant < Stock.LOW_STOCK_THRESHOLD){
                        Stock.listener.onLowStock(name,newQuant);
                        mStock.refillStock(name, 0);
                    }
                }
            }
        }

        /*  All ingredients are reserved - now make drink.
            Assumed that all drinks take 3 seconds.
            Can be customized for every drink if required.
        */
        if(canMakeDrink) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch(InterruptedException ex){
                System.out.println(ex);
            }
        }
    }

}
