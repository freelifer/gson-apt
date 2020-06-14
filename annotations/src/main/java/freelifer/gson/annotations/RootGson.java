//
//    ______             _ _  __
//    |  ___|           | (_)/ _|
//    | |_ _ __ ___  ___| |_| |_ ___ _ __
//    |  _| '__/ _ \/ _ \ | |  _/ _ \ '__|
//    | | | | |  __/  __/ | | ||  __/ |
//    \_| |_|  \___|\___|_|_|_| \___|_|
//
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
public @interface RootGson {
}
