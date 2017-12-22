package freelifer.core.json;

import freelifer.core.json.annotations.LIMITJSON;

/**
 * @author kzhu on 2017/12/20.
 */
@LIMITJSON(debug = true)
public class Config {

    //    "version": "201611291200",
//    "segment_id": "0",
//    "analytics_level": 1,
//    "fullChaningShowIntervalS": 900,
//    "preLoadAdIntervalS": 600,
//    "loadAndShowAdIntervalS": 600,
//    "realShowAdIntervalS": 1800,
    public String version;
    public String segment_id;
    public int analytics_level;
    public int fullChaningShowIntervalS;
    public int preLoadAdIntervalS;
    public int loadAndShowAdIntervalS;
    public int realShowAdIntervalS;
}
