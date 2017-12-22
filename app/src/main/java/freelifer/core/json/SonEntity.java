package freelifer.core.json;

import freelifer.core.json.annotations.LIMITJSON;
import freelifer.core.json.annotations.LIMITJSONVariable;

/**
 * @author kzhu on 2017/12/19.
 */
@LIMITJSON()
public class SonEntity extends Entity {
    @LIMITJSONVariable("see")
    public int a;
    public int eer;
    public int[] arraya;
    public String[] bb;
    public boolean[] cc;
    public long[] ff;
    public double[] ee;
}
