package se.voipbusiness.core;

import java.util.Map;
import com.eclipsesource.json.JsonObject;

/**
 * Created by espinraf on 10/05/15.
 */
public class JsonFormat {

    String intId;
    String intName;
    String type;
    String status;

    Map<String, String> params;

    JsonObject json;

    public JsonObject jsonToObject(String jsonStr){
        json = JsonObject.readFrom(jsonStr);
        intId = json.get("intId").asString();
        intName = json.get("intName").asString();
        type = json.get("type").asString();
        status = json.get("status").asString();

        return json;
    }

    public String objectToJson(JsonFormat obj){
        return json.toString();
    }

    public String getIntId() {
        return intId;
    }

    public void setIntId(String intId) {
        this.intId = intId;
    }

    public String getIntName() {
        return intName;
    }

    public void setIntName(String intName) {
        this.intName = intName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
