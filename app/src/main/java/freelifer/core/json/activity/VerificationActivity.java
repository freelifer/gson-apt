package freelifer.core.json.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import freelifer.core.json.R;
import freelifer.core.json.bean.AdConfig;
import freelifer.core.json.bean.AdConfig$$CREATOR;
import freelifer.core.json.bean.AdConfigBean;
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
        StringBuilder stringBuilder = new StringBuilder();
//        String srcData = readAssetsFile("goods.json");
        String srcData = readAssetsFile("default_ad_config.json");
        srcDataText.setText(srcData);
        Gson gson = new GsonBuilder().create();
        int count = 100;


        long start = SystemClock.elapsedRealtime();
        for (int i = 0; i < count; i++) {
//            Goods goods = Goods$$CREATOR.create(srcData, false);
            AdConfig$$CREATOR.create(srcData, false);

        }
        long end = SystemClock.elapsedRealtime();
        long selfTime = end - start;


        String srcData1 = readAssetsFile("ad_config.json");
        start = SystemClock.elapsedRealtime();
        for (int i = 0; i < count; i++) {
            gson.fromJson(srcData1, AdConfigBean.class);
        }

        end = SystemClock.elapsedRealtime();
        stringBuilder.append(String.format("LimitJSON time: %d\n Gson Time: %d\n", selfTime, (end - start)));


        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(AdConfigBean.class, new AdConfigBeanCreator());
        gson = gsonBuilder.create();

        start = SystemClock.elapsedRealtime();
        for (int i = 0; i < count; i++) {
            gson.fromJson(srcData1, AdConfigBean.class);
        }
        end = SystemClock.elapsedRealtime();
        stringBuilder.append(String.format("AdConfigBeanCreator Gson Time: %d\n", (end - start)));


        gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(AdConfigBean.class, new AdConfigBeanAdapter());
        gson = gsonBuilder.create();
        start = SystemClock.elapsedRealtime();
        for (int i = 0; i < count; i++) {
            gson.fromJson(srcData1, AdConfigBean.class);
        }
        end = SystemClock.elapsedRealtime();
        stringBuilder.append(String.format("AdConfigBeanAdapter Gson Time: %d\n", (end - start)));


        dstDataText.setText(stringBuilder.toString());
    }



    private static class AdConfigBeanCreator implements InstanceCreator<AdConfigBean> {
        @Override
        public AdConfigBean createInstance(Type type) {
            return new AdConfigBean();
        }
    }

    static class AdConfigBeanAdapter extends TypeAdapter<AdConfigBean> {
        @Override
        public AdConfigBean read(com.google.gson.stream.JsonReader reader) throws IOException {
            AdConfigBean adConfigBean = new AdConfigBean();
            reader.beginObject();
            String fieldname = null;

            while (reader.hasNext()) {
                JsonToken token = reader.peek();

                if (token.equals(JsonToken.NAME)) {
                    //get the current token
                    fieldname = reader.nextName();
                }
                if ("version".equals(fieldname)) {
                    adConfigBean.setVersion(reader.nextString());
                } else if ("segment_id".equals(fieldname)) {
                    adConfigBean.setSegmentId(reader.nextString());
                } else if ("update_interval".equals(fieldname)) {
                    adConfigBean.setUpdateInterval(reader.nextInt());
                } else if ("dsp_info".equals(fieldname)) {
                    adConfigBean.setDspInfoList(readDspInfo(reader));
                } else if ("slot_list".equals(fieldname)) {
                    adConfigBean.setSlotInfoList(readSlotInfo(reader));
                } else if ("extra".equals(fieldname)) {
                    adConfigBean.setExtraInfoList(readExtraInfo(reader));
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return adConfigBean;
        }

        ArrayList<AdConfigBean.DspInfo> readDspInfo(com.google.gson.stream.JsonReader reader) throws IOException {
            ArrayList<AdConfigBean.DspInfo> dspInfos = new ArrayList<>();
            String fieldname = null;
            reader.beginArray();
            while (reader.hasNext()) {
                AdConfigBean.DspInfo dspInfo = new AdConfigBean.DspInfo();
                reader.beginObject();
                while (reader.hasNext()) {
                    JsonToken token = reader.peek();

                    if (token.equals(JsonToken.NAME)) {
                        //get the current token
                        fieldname = reader.nextName();
                    }
                    if ("name".equals(fieldname)) {
                        dspInfo.setName(reader.nextString());
                    } else if ("preload_interval".equals(fieldname)) {
                        dspInfo.setPreloadInterval(reader.nextInt());
                    } else if ("lifetime".equals(fieldname)) {
                        dspInfo.setLifetime(reader.nextInt());
                    }  else if ("timeout".equals(fieldname)) {
                        dspInfo.setTimeOut(reader.nextLong());
                    } else {
                        reader.skipValue();
                    }
                }
                dspInfos.add(dspInfo);
                reader.endObject();
            }
            reader.endArray();
            return dspInfos;
        }


        ArrayList<AdConfigBean.SlotInfo> readSlotInfo(com.google.gson.stream.JsonReader reader) throws IOException {
            ArrayList<AdConfigBean.SlotInfo> dspInfos = new ArrayList<>();
            String fieldname = null;
            reader.beginArray();
            while (reader.hasNext()) {
                AdConfigBean.SlotInfo slotInfo = new AdConfigBean.SlotInfo();
                reader.beginObject();
                while (reader.hasNext()) {
                    JsonToken token = reader.peek();

                    if (token.equals(JsonToken.NAME)) {
                        //get the current token
                        fieldname = reader.nextName();
                    }
                    if ("slot_id".equals(fieldname)) {
                        slotInfo.setmSlotId(reader.nextString());
                    } else if ("slot_name".equals(fieldname)) {
                        slotInfo.setmSlotName(reader.nextString());
                    } else if ("open_status".equals(fieldname)) {
                        slotInfo.setmOpenStatus(reader.nextBoolean());
                    }  else if ("cache_strategy".equals(fieldname)) {
                        slotInfo.setmCacheStrategy(reader.nextInt());
                    } else if ("load_strategy".equals(fieldname)) {
                        slotInfo.setmLoadStrategy(reader.nextInt());
                    } else if ("native_switch".equals(fieldname)) {
                        slotInfo.setmNativeSwitch(reader.nextInt());
                    }  else if ("preload_n_flow".equals(fieldname)) {
                        slotInfo.setmPreloaDnFlow(reader.nextInt());
                    }  else if ("ad_best_line_high".equals(fieldname)) {
                        slotInfo.setmAdBestLineHigh(reader.nextInt());
                    } else if ("ad_best_line_low".equals(fieldname)) {
                        slotInfo.setmAdBestLineLow(reader.nextInt());
                    } else if ("global_reserve_price".equals(fieldname)) {
                        slotInfo.setmGlobalReservePrice(reader.nextInt());
                    }  else if ("sequence_flow".equals(fieldname)) {
                        slotInfo.setmSequenceFlow(readDspEngine(reader));
                    }  else {
                        reader.skipValue();
                    }
                }
                dspInfos.add(slotInfo);
                reader.endObject();
            }
            reader.endArray();
            return dspInfos;
        }


        ArrayList<AdConfigBean.DspEngine> readDspEngine(com.google.gson.stream.JsonReader reader) throws IOException {
            ArrayList<AdConfigBean.DspEngine> dspEngines = new ArrayList<>();
            String fieldname = null;
            reader.beginArray();
            while (reader.hasNext()) {
                AdConfigBean.DspEngine dspEngine = new AdConfigBean.DspEngine();
                reader.beginObject();
                while (reader.hasNext()) {
                    JsonToken token = reader.peek();

                    if (token.equals(JsonToken.NAME)) {
                        //get the current token
                        fieldname = reader.nextName();
                    }
                    if ("ad_unit_id".equals(fieldname)) {
                        dspEngine.setAdUnitId(reader.nextString());
                    } else if ("dsp_name".equals(fieldname)) {
                        dspEngine.setName(reader.nextString());
                    } else if ("impressions".equals(fieldname)) {
                        dspEngine.setmImpressions(reader.nextInt());
                    }  else if ("limit".equals(fieldname)) {
                        dspEngine.setmAdLimit(reader.nextInt());
                    } else if ("ad_size".equals(fieldname)) {
                        dspEngine.setmAdSize(reader.nextString());
                    } else if ("ad_error_num".equals(fieldname)) {
                        dspEngine.setmAdErrorNum(reader.nextInt());
                    }  else if ("fb_click_area".equals(fieldname)) {
                        dspEngine.setmFbClickArea(reader.nextInt());
                    }  else if ("admob_type".equals(fieldname)) {
                        dspEngine.setmAdmobType(reader.nextInt());
                    } else if ("reserve_price".equals(fieldname)) {
                        dspEngine.setReservePrice(reader.nextDouble());
                    } else {
                        reader.skipValue();
                    }
                }
                dspEngines.add(dspEngine);
                reader.endObject();
            }
            reader.endArray();
            return dspEngines;
        }

        ArrayList<AdConfigBean.ExtraInfo> readExtraInfo(com.google.gson.stream.JsonReader reader) throws IOException {
            ArrayList<AdConfigBean.ExtraInfo> extraInfos = new ArrayList<>();
            String fieldname = null;
            reader.beginArray();
            while (reader.hasNext()) {
                AdConfigBean.ExtraInfo extraInfo = new AdConfigBean.ExtraInfo();
                reader.beginObject();
                while (reader.hasNext()) {
                    JsonToken token = reader.peek();

                    if (token.equals(JsonToken.NAME)) {
                        //get the current token
                        fieldname = reader.nextName();
                    }
                    if ("key".equals(fieldname)) {
                        extraInfo.setKey(reader.nextString());
                    } else if ("value".equals(fieldname)) {
                        extraInfo.setValue(reader.nextString());
                    } else {
                        reader.skipValue();
                    }
                }
                extraInfos.add(extraInfo);
                reader.endObject();
            }
            reader.endArray();
            return extraInfos;
        }
        @Override
        public void write(JsonWriter writer, AdConfigBean student) throws IOException {
        }
    }


}
