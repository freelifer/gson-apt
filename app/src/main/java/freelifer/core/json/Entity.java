package freelifer.core.json;

import freelifer.core.json.annotations.LJSON;

/**
 * @author kzhu on 2017/12/19.
 */
@LJSON()
public class Entity {
    public int a;
    public String b;
    public boolean c;
//    private double d;

    public SonEntity son;
}
