package freelifer.core.json;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author kzhu on 2017/12/18.
 */
public class Files {

    public static String readFile(File file, String encoding) throws IOException {
        if (file == null) {
            return null;
        }

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
            byte[] buffer = new byte[1024];

            int size;
            while ((size = fis.read(buffer)) > 0) {
                bos.write(buffer, 0, size);
            }

            return new String(bos.toByteArray(), encoding);
        } finally {
            IOs.close(fis);
        }
    }

    public static String readInputStream(InputStream is, String encoding) throws IOException {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(is.available());
            byte[] buffer = new byte[1024];

            int size;
            while ((size = is.read(buffer)) > 0) {
                bos.write(buffer, 0, size);
            }

            return new String(bos.toByteArray(), encoding);
        } finally {
            IOs.close(is);
        }
    }
}
