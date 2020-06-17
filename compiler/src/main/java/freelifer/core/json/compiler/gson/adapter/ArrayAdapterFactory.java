package freelifer.core.json.compiler.gson.adapter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhukun on 2020-06-14.
 */
public class ArrayAdapterFactory implements AdapterFactory {
    private Pattern pattern;

    public ArrayAdapterFactory() {
        String regEx = "(\\S*)\\[\\]";
        pattern = Pattern.compile(regEx);
    }

    @Override
    public Adapter create(String type) {
        Matcher mat = pattern.matcher(type);
        if (mat.find()) {
            return new ArrayAdapter(mat.group(1));
        }

        return null;
    }
}
