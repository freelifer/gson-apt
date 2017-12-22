package freelifer.core.json;

import freelifer.core.json.annotations.LIMITJSON;

/**
 * @author kzhu on 2017/12/19.
 */
@LIMITJSON()
public class Entity {
    public int a;
    public String b;
    public boolean c;
//    private double d;

    public SonEntity son;
}
