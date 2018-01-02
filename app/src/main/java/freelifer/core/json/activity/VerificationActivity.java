package freelifer.core.json.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import freelifer.core.json.R;
import freelifer.core.json.bean.Goods;
import freelifer.core.json.bean.Goods$$CREATOR;

/**
 * @author kzhu on 2017/12/23.
 */
public class VerificationActivity extends BaseActivity {
    TextView srcDataText;
    TextView dstDataText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        srcDataText = find(R.id.tv_src_data);
        dstDataText = find(R.id.tv_dst_data);
//        start();
        time();
    }

    private void start() {
        String srcData = readAssetsFile("goods.json");
        srcDataText.setText(srcData);

        Goods goods = Goods$$CREATOR.create(srcData, false);
        dstDataText.setText(goods.toString());

    }

    private void time() {
        String srcData = readAssetsFile("goods.json");
        srcDataText.setText(srcData);
        Gson gson = new GsonBuilder().create();
        int count = 10000;

        long start = SystemClock.elapsedRealtime();
        for (int i = 0; i < count; i++) {
            Goods goods = Goods$$CREATOR.create(srcData, false);
        }
        long end = SystemClock.elapsedRealtime();
        long selfTime = end - start;


        start = SystemClock.elapsedRealtime();
        for (int i = 0; i < count; i++) {
            gson.fromJson(srcData, Goods.class);
        }

        end = SystemClock.elapsedRealtime();
        dstDataText.setText(String.format("LimitJSON time: %d\n Gson Time: %d", selfTime, (end - start)));
    }

}
