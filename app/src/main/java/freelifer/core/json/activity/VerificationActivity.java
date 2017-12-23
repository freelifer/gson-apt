package freelifer.core.json.activity;

import android.os.Bundle;
import android.widget.TextView;

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
        start();
    }

    private void start() {
        String srcData = readAssetsFile("goods.json");
        srcDataText.setText(srcData);

        Goods goods = Goods$$CREATOR.create(srcData, false);

        dstDataText.setText(goods.toString());

    }


}
