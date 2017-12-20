package freelifer.core.json;

import java.io.Closeable;

/**
 * @author kzhu on 2017/12/18.
 */
public class IOs {

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
