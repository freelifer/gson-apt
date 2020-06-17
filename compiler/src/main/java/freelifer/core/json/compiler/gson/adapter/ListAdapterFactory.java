package freelifer.core.json.compiler.gson.adapter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhukun on 2020-06-14.
 */
public class ListAdapterFactory implements AdapterFactory {
    private Pattern pattern;

    public ListAdapterFactory() {
        String regEx = "java\\.util\\.(Array)?List<(\\S*)>";
        pattern = Pattern.compile(regEx);
    }

    @Override
    public Adapter create(String type) {
        Matcher mat = pattern.matcher(type);
        if (mat.find()) {
            return new ListAdapter(mat.group(2));
        }

        return null;
    }
}
