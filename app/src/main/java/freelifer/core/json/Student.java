package freelifer.core.json;

import freelifer.core.json.annotations.LJSON;

/**
 * @author kzhu on 2017/12/20.
 */

@LJSON()
public class Student {
    public String name;
    public int age;
    public boolean sex;
    public Role role;

    @Override
    public String toString() {
        return String.format("name:%s age:%d role:%s", name, age, role.toString());
    }
}
