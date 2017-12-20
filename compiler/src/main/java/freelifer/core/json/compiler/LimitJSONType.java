package freelifer.core.json.compiler;

import com.squareup.javapoet.MethodSpec;

/**
 * @author zhukun on 2017/12/21.
 */

public interface LimitJSONType {

    void addStatement(MethodSpec.Builder builder, LIMITJSONVariable limitjsonVariable);

}
