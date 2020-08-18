import java.util.Set;
import java.util.concurrent.TimeUnit;

public class DrinkProcessor implements  Runnable{

    private final Stock mStock;
    private final String drinkName;
    private final Ingredients ingredients;

    DrinkProcessor(Stock stock, String name, Ingredients items){
        mStock = stock;
        ingredients = items;
        drinkName = name;
    }

    @Override
    public void run() {
        synchronized (mStock){
            //System.out.println("Thread: " + Thread.currentThread().getId());
            Set<String> ingredientNames = ingredients.getIngredientsNames();

            boolean canMakeDrink = true;

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

            if(canMakeDrink){
                System.out.println(drinkName + " is prepared");
                for(String name : ingredientNames){
                    Integer newQuant = mStock.getQuantity(name) - ingredients.getQuantity(name);
                    mStock.setData(name, newQuant);

                    if(newQuant < Stock.LOW_STOCK_THRESHOLD){
                        Stock.listener.onLowStock(name,newQuant);
                        mStock.refillStock(name, 0);
                    }
                }
            }
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch(InterruptedException ex){
            System.out.println(ex);
        }

    }

}
