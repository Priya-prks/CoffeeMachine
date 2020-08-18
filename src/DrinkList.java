import java.util.Hashtable;
import java.util.Set;

public class DrinkList {

    private static Hashtable<String, Ingredients> drinksList = null;
    private static DrinkList list = null;

    DrinkList(){
        drinksList = new Hashtable<>();
    }

    //Singleton - double lock
    public static DrinkList getInstance(){
        if(list == null){
            list = new DrinkList();
        }
        return list;
    }

    public void setData(String name, Ingredients ing){
        drinksList.put(name, ing);
    }

    public Set<String> getBeverageList(){
        return drinksList.keySet();
    }

    //Error check if this item does not exist in the list
    public Ingredients getIngredients(String name){
        if(drinksList.containsKey(name)){
            return drinksList.get(name);
        }
        return null;
    }

    /*public void addTask(String name, Ingredients items){
        drinksList.put(name, items);
    }*/
}
