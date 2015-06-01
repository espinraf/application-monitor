package se.voipbusiness.core;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.text.DecimalFormat;
import java.util.concurrent.ConcurrentNavigableMap;

/**
 * Created by rafaespi on 22.05.15.
 *
 * In-memory DB using MapDB
 */
public class MonitorDB {

    DB db;
    ConcurrentNavigableMap monitorMap;
    DecimalFormat df2 = new DecimalFormat("###.##");
    Double tps = 0.0;


    // Initialize  Monitor DB
    public void init(){

        db = DBMaker.newMemoryDB().make();
        monitorMap = db.getTreeMap("Monitor");
    }

    public void stop(){
        db.close();
    }

    public ConcurrentNavigableMap getMonitorMap(){

        return monitorMap;
    }

    // Checks if a Application Id exist in the DB
    public boolean exsistApp(String json){

        JsonObject jo = JsonObject.readFrom(json);
        String id = (String)jo.get("Id").asString();
        JsonObject jodb = (JsonObject)monitorMap.get(id);

        if (jodb != null) {
            return true;
        }
        else{
            return false;
        }

    }

    // Add a application in the DB
    public String addApp(String json){
        JsonObject jo = JsonObject.readFrom(json);
        String id = (String)jo.get("Id").asString();

        // Add time to the Json Msg
        long time = System.currentTimeMillis();
        jo.add("time", String.valueOf(time));

        System.out.println("Storing in DB");
        monitorMap.put(id, jo);
        JsonObject jo2 = (JsonObject)monitorMap.get(id);

        db.commit();
        return jo2.toString();

    }

    /*
    {
    "Id" : "i001", # Mandatory
    "Name" : "Customer", # Mandatory
    "Type" : "JMS" # Mandatory
    "Status" : "OK", # Mandatory

    "data": #User defined
    [
        { "name" : "msgRecv", "value" : "1", "type" : "C" },
        { "name": "msgSend", "value" : "5", "type" : "C" },
        { "name": "Errors", "value" : "0", "type" : "C" },
        { "name": "ErrorXSLT", "value" : "1", "type" : "B" },
        { "name": "LastError", "value" : "0", "type" : "S" }
    ]
}
     */

    // Update an Application
    public String updateApp(String json){
        JsonObject jo = JsonObject.readFrom(json);
        String id = (String)jo.get("Id").asString();
        JsonObject jodb = (JsonObject)monitorMap.get(id);

        if (jodb == null){
            addApp(json);
            jodb = (JsonObject)monitorMap.get(id);
            return jodb.toString();
        }

        jodb.set("Name", jo.get("Name").asString());
        jodb.set("Type", jo.get("Type").asString());
        jodb.set("Status", jo.get("Status").asString());

        // Get time from Json before update it
        long t1 = Long.valueOf(jodb.get("time").asString());
        // Add time to the Json Msg
        long t2 = System.currentTimeMillis();
        Float res = new Float((t2 - t1) / 1000.0);
        if (res == 0.0){
            tps = 0.0;
        }
        else {
            tps = (double) (1 / res);
            tps = Double.valueOf(df2.format(tps));
        }
        System.out.println("TPS: " + tps);
        jodb.set("time", String.valueOf(t2));


        JsonArray joa = jo.get("data").asArray();
        JsonArray joadb = jodb.get("data").asArray();

        int s = joa.size();
        for(int i = 0; i < s ; i++){
            JsonObject dElem = (JsonObject) joa.get(i).asObject();
            updateDataElement(dElem, joadb);
        }

        jodb = (JsonObject)monitorMap.get(id);
        db.commit();
        return jodb.toString();
    }

    public void updateDataElement(JsonObject jo, JsonArray joadb){

        String name = jo.get("name").asString();
        String value = jo.get("value").asString();;
        String type = jo.get("type").asString();;

        String namedb = null;
        String valuedb = null;
        String typedb = null;

        JsonObject jodb;
        Boolean found = false;

        int s = joadb.size();
        for(int i = 0; i < s ; i++){
            jodb = (JsonObject) joadb.get(i).asObject();
            namedb = jodb.get("name").asString();

            if (name.equals(namedb)){
                found = true;
                typedb = jodb.get("type").asString();
                valuedb = jodb.get("value").asString();

                // Check new Msg type in case the type has been updated
                if(type.equals("C")){
                    Integer c = Integer.parseInt(value);
                    Integer cdb = Integer.parseInt(valuedb);
                    cdb = cdb + c;
                    jodb.set("value", cdb.toString());
                    jodb.set("type", type);
                }
                else if(type.equals("V")){
                    Double c = Double.parseDouble(value);
                    Double cdb = Double.parseDouble(valuedb);
                    cdb = (cdb + c)/2;
                    cdb = Double.valueOf(df2.format(cdb));
                    jodb.set("value", cdb.toString());
                    jodb.set("type", type);
                }
                else if(type.equals("T")){
                    // Update this param base on the msg time of arrive
                    if (tps > 0.0 ) {
                        jodb.set("value", tps.toString());
                        jodb.set("type", type);
                    }
                }
                else {
                    jodb.set("value", value);
                    jodb.set("type", type);
                }

            }
        }
        if(!found) {
            joadb.add(jo);
        }

    }

}
