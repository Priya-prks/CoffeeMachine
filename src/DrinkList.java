import java.util.Hashtable;

/*
    This is a singleton class, to store the possible drinks that can be made
    and its ingredients along with its quantity
 */

public class DrinkList {

    //Map of Drink name, and its Ingredient list (name, quantity)
    private static Hashtable<String, Ingredients> drinksList = null;
    private static DrinkList drinkListInstance = null;

    DrinkList(){
        drinksList = new Hashtable<>();
    }

    /*
        Singleton class : because this serve as the task list for the coffee machine.
        So there should be only one object. This task list data can be updated if required
     */
    public static DrinkList getInstance(){
        if(drinkListInstance == null){
            synchronized (DrinkList.class){
                if(drinkListInstance == null){
                    drinkListInstance = new DrinkList();
                }
            }

        }
        return drinkListInstance;
    }

    /*
        Add drinkname, and its ingredient name-quantity data to the map
     */
    public void setData(String name, Ingredients ing){
        drinksList.put(name, ing);
    }

    /*public Set<String> getBeverageList(){
        return drinksList.keySet();
    }*/

    /*
        Returns the ingredients name, quantity for a given drink name
     */
    public Ingredients getIngredients(String name){
        if(drinksList.containsKey(name)){
            return drinksList.get(name);
        }
        return null;
    }

    //API which can be used if we need to add more drinks to the menu on runtime. Out of scope for current cases
    /*public void addTask(String name, Ingredients items){
        drinksList.put(name, items);
    }*/
}
