import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

public class InputParser {
        public static Integer outletCount;
        public static Integer taskCount;

    public static void parse(Ingredients stock, DrinkList drinkList, ArrayList<String> taskOrder) throws  Exception {
        taskCount = 0;

        Object obj = JsonParser.parseReader(new FileReader("input/input.json"));
        JsonObject jsonObj = (JsonObject) obj;

        JsonObject machineObj = jsonObj.getAsJsonObject("machine");
        JsonObject outletObj = machineObj.getAsJsonObject("outlets");

        JsonElement outletElem = outletObj.get("count_n");
        Integer outlet = outletElem.getAsInt();

        //parse Stock related data
        parseStockData(stock, machineObj);

        //parse every beverage ingredient data
        JsonObject beverObj = machineObj.getAsJsonObject("beverages");
        parseDrinkData(drinkList, beverObj, taskOrder);

        outletCount = outlet;
    }

    private static void parseStockData(Ingredients stock, JsonObject machineObj){
        JsonObject quantObj = machineObj.getAsJsonObject("total_items_quantity");

        Iterator it = quantObj.keySet().iterator();
        while(it.hasNext()){
            Object key = it.next();
            Object val = quantObj.get(key.toString());
            Integer value = Integer.parseInt(val.toString());
            stock.setData(key.toString(),value);
        }
    }

    private static void parseDrinkData(DrinkList drinkList, JsonObject bevObj, ArrayList<String> taskOrder){

        Iterator itItem = bevObj.keySet().iterator();

        while(itItem.hasNext()){
            taskCount++;
            Object obj = itItem.next();

            JsonObject jsonObj = bevObj.getAsJsonObject(obj.toString());
            Iterator it = jsonObj.keySet().iterator();
            Ingredients mIng = new Ingredients();

            while(it.hasNext()){
                Object key = it.next();
                Object val = jsonObj.get(key.toString());
                Integer value = Integer.parseInt(val.toString());
                mIng.setData(key.toString(), value);
            }
            drinkList.setData(obj.toString(), mIng);
            taskOrder.add(obj.toString());
        }
    }


}
