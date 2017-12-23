package freelifer.core.json;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import freelifer.core.json.activity.VerificationActivity;
import freelifer.core.json.bean.Config;


public class MainActivity extends AppCompatActivity {

    TextView limitJsonTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Entity$$CREATOR.create("", false);
        limitJsonTv = (TextView) findViewById(R.id.limitjson_ms);
        try {
            JSONObject root = new JSONObject("");
            root.opt("");
            root.optInt("");
            root.optBoolean("");
            root.optLong("");
            float aa = (float) root.optDouble("");
            root.optString("");

            root.optJSONObject("");
            root.optJSONArray("");
            int[] a;
            if (root.has("arr")) {
                JSONArray array = root.optJSONArray("");
                int size = array.length();
                a = new int[size];
                for (int i = 0; i < size; i++) {
                    a[i] = array.optInt(i);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        testLimitJson();

        LinearGraphView tu = (LinearGraphView) findViewById(R.id.line_graphic);

        ArrayList<Double> yList = new ArrayList<Double>();
        yList.add((double) 2.103);
        yList.add(4.05);
        yList.add(6.60);
        yList.add(3.08);
        yList.add(4.32);
        yList.add(2.0);
        yList.add(1115.0);

        ArrayList<String> xRawDatas = new ArrayList<String>();
        xRawDatas.add("05-19");
        xRawDatas.add("05-20");
        xRawDatas.add("05-21");
        xRawDatas.add("05-22");
        xRawDatas.add("05-23");
        xRawDatas.add("05-24");
        xRawDatas.add("05-25");
        xRawDatas.add("05-26");
        tu.setData(yList, xRawDatas, 8, 2);
    }

    public void testLimitJson() {
        long start = System.currentTimeMillis();
        try {
            String json = Files.readInputStream(getAssets().open("config.json"), "utf-8");
            for (int i = 0; i < 100; i++) {
            }

            Log.i("kzhu", "time: " + (System.currentTimeMillis() - start));
            limitJsonTv.setText((System.currentTimeMillis() - start) + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void toValidate(View view) {
        startActivity(new Intent(this, VerificationActivity.class));
    }
}
