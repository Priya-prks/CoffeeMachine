/*
Interface for listening to low stock data
 */
public interface NotificationListener {

    public void onLowStock(String name, Integer quantity);

}
