package freelifer.core.json;

import freelifer.core.json.annotations.LJSON;
import freelifer.core.json.annotations.LJSONVariable;

/**
 * @author kzhu on 2017/12/19.
 */
@LJSON()
public class SonEntity extends Entity {
    @LJSONVariable("see")
    public int a;
    public int s;
    public int[] arraya;
}
