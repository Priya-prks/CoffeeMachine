import java.util.Hashtable;
import java.util.Set;

public class Ingredients {
    
    protected Hashtable<String, Integer> ingredients;

    public Ingredients(){
        ingredients = new Hashtable<>();
    }

    public void setData(String name, Integer value){
        ingredients.put(name, value);
    }
    public Set<String> getIngredientsNames(){
        return ingredients.keySet();
    }

    public Integer getQuantity(String name){
        if(ingredients.containsKey(name)){
            return ingredients.get(name);
        }
        return -1;
    }
}
