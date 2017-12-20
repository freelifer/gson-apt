package freelifer.core.json.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author kzhu on 2017/12/20.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface LJSONVariable {
    String value();
}
