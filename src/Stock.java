public class Stock extends Ingredients {

    private static Stock stockInstance = null;

    public static Integer LOW_STOCK_THRESHOLD = 10;
    public static NotificationListener listener;

    private Stock(){

    }

    public void refillStock(String name, Integer quantToAdd){
        if(ingredients.containsKey(name)){
            Integer availQuant = ingredients.get(name);
            ingredients.put(name, availQuant+ quantToAdd);
            System.out.println("Stock of " + name + " refilled, current quantity: " + quantToAdd);
        } else {
            setData(name, quantToAdd);
            System.out.println("New item added to the stock");
        }
    }

    public static void registerListener(NotificationListener notificationListener){
        listener = notificationListener;
    }

    //Double lock threadsafe
    public static Stock getInstance(){
        if(stockInstance == null){
            stockInstance = new Stock();
        }
        return stockInstance;
    }

}
