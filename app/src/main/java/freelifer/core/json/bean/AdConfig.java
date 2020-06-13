package freelifer.core.json.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import freelifer.core.json.annotations.LIMITJSON;

/**
 * @author kzhu on 2017/12/20.
 */
@LIMITJSON()
public class AdConfig {

//"version": "201611041540",
//"version_desc": "description",
//"open_status": true,
//"refresh_frequence": 3600000,
//"refresh_time_limite": 300000,
//"segment_id": 100,
//"refresh_cache_by_batterystatus": 70,
//"night_mod_time": 19,
//"day_mod_time": 7,
//"refresh_url": "http://www.baidu.com",
//"gift_url": "http://download.wifimaster.mobi/swiftwifi/2016/white_floatball.gif",
//"battery_gift_url": "http://cdn.doghot.info/browser/2016/floatball.gif",
//    "reprot_config"

    @SerializedName("version")
    public String version;
    @SerializedName("version_desc")
    public String version_desc;
    @SerializedName("open_status")
    public boolean open_status;
    @SerializedName("refresh_frequence")
    public long refresh_frequence;
    @SerializedName("refresh_time_limite")
    public long refresh_time_limite;
    @SerializedName("segment_id")
    public int segment_id;
    @SerializedName("refresh_cache_by_batterystatus")
    public int refresh_cache_by_batterystatus;
    @SerializedName("night_mod_time")
    public int night_mod_time;
    @SerializedName("day_mod_time")
    public int day_mod_time;
    @SerializedName("refresh_url")
    public String refresh_url;
    @SerializedName("gift_url")
    public String gift_url;
    @SerializedName("battery_gift_url")
    public String battery_gift_url;
    @SerializedName("reprot_config")
    public ReprotConfig reprot_config;
    @SerializedName("SDK_Config")
    public SDKConfig SDK_Config;
    @SerializedName("ADSlot_Config")
    public ArrayList<ADSlotConfig> ADSlot_Config;
}
