package freelifer.core.json.activity;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

import freelifer.core.json.Files;

/**
 * @author kzhu on 2017/12/23.
 */
public class BaseActivity extends AppCompatActivity {

    @SuppressWarnings("unchecked")
    protected <T> T find(int id) {
        return (T) findViewById(id);
    }

    protected String readAssetsFile(String fileName) {
        try {
            return Files.readInputStream(getAssets().open(fileName), "utf-8");
        } catch (IOException e) {
            return "error " + e.getMessage();
        }
    }

    protected long runByAverage(int num, int count, Runnable runnable) {
        long sumTime = 0L;
        for (int i = 0; i < num; i++) {
            sumTime += runByCount(count, runnable);
        }

        return sumTime / num;
    }

    protected long runByCount(int count, Runnable runnable) {
        long start = SystemClock.elapsedRealtime();
        for (int i = 0; i < count; i++) {
            runnable.run();
        }
        long end = SystemClock.elapsedRealtime();
        return end - start;
    }
}
