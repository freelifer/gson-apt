package freelifer.core.json.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhukun on 2017/12/19.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface LIMITJSON {
    boolean debug() default false;
}
