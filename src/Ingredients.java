import java.util.Hashtable;
import java.util.Set;

/*
    Class representing mapping of any drink ingredient name and its quantity
 */

public class Ingredients {

    //Protected for access in child classes, but not accessible to public
    protected Hashtable<String, Integer> ingredients;

    public Ingredients(){
        ingredients = new Hashtable<>();
    }

    //Set the quantity for an ingredient.
    public void setData(String name, Integer value){
        ingredients.put(name, value);
    }

    //To get list of ingredient in the list
    public Set<String> getIngredientsNames(){
        return ingredients.keySet();
    }

    //Return quantity of an item if its present
    public Integer getQuantity(String name){
        if(ingredients.containsKey(name)){
            return ingredients.get(name);
        }
        return -1;
    }

}
