package freelifer.core.json.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import freelifer.core.json.annotations.LIMITJSON;

/**
 * @author zhukun on 2020-06-13.
 */
@LIMITJSON()
public class ADSlotConfig {

//"slot_id": "62001",
//"slot_name": "spb_gift_box",
//"open_status": true,
//"is_auload": false,
//"show_priority": 2,
//"cache_size": 1,
//"load_num": 1,

    @SerializedName("slot_id")
    public String slot_id;
    @SerializedName("slot_name")
    public String slot_name;
    @SerializedName("open_status")
    public boolean open_status;
    @SerializedName("is_auload")
    public boolean is_auload;
    @SerializedName("show_priority")
    public int show_priority;
    @SerializedName("cache_size")
    public int cache_size;
    @SerializedName("load_num")
    public int load_num;
    @SerializedName("flow")
    public ArrayList<ArrayList<Flow>> flow;
}
