/*
    This is a singleton class to store the total item quantity data.
    Basically mapping of every available item and its quantity
    quantity -1 represents that data is not present in the stock list itself
 */

public class Stock extends Ingredients {

    private static Stock stockInstance = null;

    public static Integer LOW_STOCK_THRESHOLD = 10;
    public static NotificationListener listener;

    private Stock(){

    }

    /*  To refill the data, when its stock is low
        Assumption : If this item was not listed in the stock list, then it can not be loaded as the machine does not support
        Possible : If its possible to add this data to stock, that can be done by SetData if required.
    */
    public void refillStock(String name, Integer quantToAdd){
        if(ingredients.containsKey(name)){
            Integer availQuant = ingredients.get(name);
            ingredients.put(name, availQuant+ quantToAdd);
            System.out.println("Stock of " + name + " refilled, current quantity: " + quantToAdd);
        } else {
            //setData(name, quantToAdd);
            System.out.println("This item can not be added, as its not supported");
        }
    }

    public boolean isEmpty(){
        if(ingredients.isEmpty())
            return true;
        return false;
    }

    public static void registerListener(NotificationListener notificationListener){
        listener = notificationListener;
    }

    /*
        Singleton class, because there would exist only one stock data - on which all the drinks will be made
        Made threadsafe - as parallel processsing is being done, and if anyone calls from multiple threads it
        can created issue.
        Although in current scenario it will not happen, because the Stock is being initialized before processing, before
        threads started.
     */
    public static Stock getInstance(){
        if(stockInstance == null){
            synchronized (Stock.class){
                if(stockInstance == null){
                    stockInstance = new Stock();
                }
            }
        }
        return stockInstance;
    }

}
