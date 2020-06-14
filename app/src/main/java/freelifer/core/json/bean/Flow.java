package freelifer.core.json.bean;

import com.google.gson.annotations.SerializedName;

import freelifer.core.json.annotations.LIMITJSON;
import freelifer.gson.annotations.Gson;

/**
 * @author zhukun on 2020-06-13.
 */

@LIMITJSON()
@Gson()
public class Flow {
//"platform": "facebook",
//"open_status": true,
//"ad_clcik_enable": 1,
//"type": "native",
//"weight": 50,
//"native_style": 8,
//"key": "182964452160523_182968435493458"

    @SerializedName("platform")
    public String platform;
    @SerializedName("open_status")
    public boolean open_status;
    @SerializedName("ad_clcik_enable")
    public int ad_clcik_enable1111;
    @SerializedName("type")
    public String type;
    @SerializedName("weight")
    public int weight;
    @SerializedName("native_style")
    public int native_style;
    @SerializedName("key")
    public String key;
}
