package freelifer.core.json.bean;

import freelifer.core.json.annotations.LIMITJSON;

/**
 * @author zhukun on 2020-06-13.
 */
@LIMITJSON()
public class SDKConfig {


//"facebook_status": true,
//"admob_status": true,
//"mopub_status": true,
//"facebook_lifetime": 9000000,
//"admob_lifetime": 2700000,
//"mopub_lifetime": 21600000

    public boolean facebook_status;
    public boolean admob_status;
    public boolean mopub_status;
    public long facebook_lifetime;
    public long admob_lifetime;
    public long mopub_lifetime;
}
