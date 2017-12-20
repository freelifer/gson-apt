package freelifer.core.json.compiler;

import javax.lang.model.type.TypeMirror;

/**
 * @author kzhu on 2017/12/20.
 */
public interface LIMITJSONVariable {
    String variableName();

    String jsonName();

    TypeMirror typeMirror();
}
