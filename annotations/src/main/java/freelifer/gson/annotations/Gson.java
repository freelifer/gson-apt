package freelifer.gson.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author zhukun on 2020-06-14 01:32:41
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Gson {
    /**
     * 是否是跟节点, 如果是 则创建对象的Gson TypeAdapter，
     * 让用户可以注册到Gson中[registerTypeAdapter]
     *
     * @return true false
     */
    boolean root() default false;
}
