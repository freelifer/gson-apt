package freelifer.core.json.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kzhu on 2017/12/20.
 */
public class Collections {


    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<>();
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }

    public static boolean isEmpty(Map map) {
        return (map == null || map.size() <= 0);
    }

    public static boolean isEmpty(List list) {
        return (list == null || list.size() <= 0);
    }

    public static <E> ArrayList<E> listof(E... args) {
        ArrayList<E> list = newArrayList();
        for (E e : args) {
            list.add(e);
        }
        return list;
    }
}
