package freelifer.core.json;

import freelifer.core.json.annotations.LIMITJSON;

/**
 * @author kzhu on 2017/12/20.
 */
@LIMITJSON()
public class Role {
    public String name;
    public String desc;

    @Override
    public String toString() {
        return String.format("name:%s desc:%s", name, desc);
    }
}
