package freelifer.core.json.bean;

import freelifer.core.json.annotations.LIMITJSON;
import freelifer.gson.annotations.Gson;

/**
 * @author zhukun on 2020-06-13.
 */
@LIMITJSON()
@Gson()
public class ReprotConfig {

//"report_status": true,
//"report_time": 300000
    public boolean report_status;
    public long report_time;
//    public ArrayList<Flow> flow;
}
