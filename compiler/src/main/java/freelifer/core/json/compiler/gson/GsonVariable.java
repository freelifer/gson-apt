package freelifer.core.json.compiler.gson;

import javax.lang.model.type.TypeMirror;

/**
 * @author kzhu on 2017/12/20.
 */
public interface GsonVariable {
    String variableName();

    String jsonName();

    TypeMirror typeMirror();
}
