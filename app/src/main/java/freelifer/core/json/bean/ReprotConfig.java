package freelifer.core.json.bean;

import freelifer.core.json.annotations.LIMITJSON;

/**
 * @author zhukun on 2020-06-13.
 */
@LIMITJSON()
public class ReprotConfig {

//"report_status": true,
//"report_time": 300000
    public boolean report_status;
    public long report_time;
}
