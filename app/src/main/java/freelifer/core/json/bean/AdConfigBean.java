package freelifer.core.json.bean;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdConfigBean implements Serializable {
    @SerializedName("version")
    private String mVersion = "0";

    @SerializedName("segment_id")
    private String mSegmentId = "";

    @SerializedName("update_interval")
    private int mUpdateInterval = 10800;    // Unit: second

    @SerializedName("dsp_info")
    private ArrayList<DspInfo> mDspInfoList = new ArrayList<>();

    @SerializedName("slot_list")
    private ArrayList<SlotInfo> mSlotInfoList = new ArrayList<>();

    @SerializedName("extra")
    private ArrayList<ExtraInfo> mExtraInfoList = new ArrayList<>();

    public AdConfigBean() {
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public void setVersion(String mVersion) {
        this.mVersion = mVersion;
    }

    public void setSegmentId(String mSegmentId) {
        this.mSegmentId = mSegmentId;
    }

    public void setUpdateInterval(int mUpdateInterval) {
        this.mUpdateInterval = mUpdateInterval;
    }

    public void setDspInfoList(ArrayList<DspInfo> mDspInfoList) {
        this.mDspInfoList = mDspInfoList;
    }

    public void setSlotInfoList(ArrayList<SlotInfo> mSlotInfoList) {
        this.mSlotInfoList = mSlotInfoList;
    }

    public void setExtraInfoList(ArrayList<ExtraInfo> mExtraInfoList) {
        this.mExtraInfoList = mExtraInfoList;
    }

    public String getVersion() {
        return mVersion;
    }

    public String getSegmentId() {
        return mSegmentId;
    }

    protected long getUpdateInterval() {
        return mUpdateInterval * 1000;
    }

    public List<DspInfo> getDspInfoList() {
        return mDspInfoList;
    }

    public ArrayList<ExtraInfo> getExtraInfoList() {
        return mExtraInfoList;
    }

    public List<SlotInfo> getSlotInfoList() {
        return mSlotInfoList;
    }

    public String findExtraInfoByName(String name) {
        if (mExtraInfoList == null || mExtraInfoList.size() == 0 || TextUtils.isEmpty(name)) {
            return null;
        }
        for (ExtraInfo extraInfo : mExtraInfoList) {
            if (extraInfo != null && name.equals(extraInfo.mKey)) {
                return extraInfo.getValue();
            }
        }
        return null;
    }

    public DspInfo findDspInfoByName(String DspName) {
        for (DspInfo dspInfo : mDspInfoList) {
            if (dspInfo != null && dspInfo.getName() != null && dspInfo.getName().equals(DspName)) {
                return dspInfo;
            }
        }
        return null;
    }

    public SlotInfo findSlotInfoById(String slotId) {
        for (SlotInfo slotInfo : mSlotInfoList) {
            if (slotInfo != null && slotInfo.getSlotId() != null && slotInfo.getSlotId().equals(slotId)) {
                return slotInfo;
            }
        }
        return null;
    }

    protected boolean isValid() {
        if (TextUtils.isEmpty(mVersion)) {
            return false;
        }
        if (mUpdateInterval < 60) {
            return false;
        }
        if (mUpdateInterval > 86400) {
            return false;
        }
        if (mDspInfoList == null) {
            return false;
        }
        if (mSlotInfoList == null || mSlotInfoList.isEmpty()) {
            return false;
        }
        for (int idx = 0; idx < mDspInfoList.size(); ++idx) {
            DspInfo dspInfo = mDspInfoList.get(idx);
            if (dspInfo == null) {
                return false;
            }
            if (!dspInfo.isValid(idx)) {
                return false;
            }
        }
        for (int idx = 0; idx < mSlotInfoList.size(); ++idx) {
            SlotInfo slotInfo = mSlotInfoList.get(idx);
            if (slotInfo == null) {
                return false;
            }
            if (!slotInfo.isValid(idx)) {
                return false;
            }
        }
        return true;
    }

    public static class DspInfo implements Serializable {
        @SerializedName("name")
        private String mName = null;

        @SerializedName("preload_interval")
        private int mPreloadInterval = 300;  // Unit: second

        @SerializedName("lifetime")
        private int mLifetime = 2700;    // Unit: second

        @SerializedName("timeout")
        private long mTimeOut = 2000;

        public DspInfo() {
        }

        public void setName(String mName) {
            this.mName = mName;
        }

        public void setPreloadInterval(int mPreloadInterval) {
            this.mPreloadInterval = mPreloadInterval;
        }

        public void setLifetime(int mLifetime) {
            this.mLifetime = mLifetime;
        }

        public void setTimeOut(long mTimeOut) {
            this.mTimeOut = mTimeOut;
        }

        public String getName() {
            return mName;
        }

        public long getLifetime() {
            return mLifetime * 1000L;
        }

        public long getTimeOut() {
            return mTimeOut;
        }

        public long getPreloadInterval() {
            return mPreloadInterval * 1000L;
        }

        protected boolean isValid(int idx) {
            if (TextUtils.isEmpty(mName)) {
                return false;
            }
            if (mLifetime < 0) {
                return false;
            }
            return true;
        }
    }

    public static class ExtraInfo implements Serializable {

        @SerializedName("key")
        private String mKey = null;

        @SerializedName("value")
        private String mValue = null;

        public ExtraInfo() {
        }

        public String getKey() {
            return mKey;
        }

        public void setKey(String mKey) {
            this.mKey = mKey;
        }

        public String getValue() {
            return mValue;
        }

        public void setValue(String mValue) {
            this.mValue = mValue;
        }

        @Override
        public String toString() {
            return "ExtraInfo{" +
                    "mKey='" + mKey + '\'' +
                    ", mValue='" + mValue + '\'' +
                    '}';
        }
    }


    public static class SlotInfo implements Serializable {
        @SerializedName("slot_id")
        private String mSlotId = null;

        @SerializedName("slot_name")
        private String mSlotName = null;

        @SerializedName("open_status")
        private boolean mOpenStatus = true;

        @SerializedName("cache_strategy")
        private int mCacheStrategy = 0;

        // nativead 0 缓存优先 1 flow优先 2 flow瀑布流
        // Interstitial 0 flow优先  2 flow瀑布流
        @SerializedName("load_strategy")
        private int mLoadStrategy = 0;

        @SerializedName("native_switch")
        private int mNativeSwitch = 0;

        @SerializedName("preload_n_flow")
        private int mPreloaDnFlow = 1;

        @SerializedName("ad_best_line_high")
        private int mAdBestLineHigh = 0;

        @SerializedName("ad_best_line_low")
        private int mAdBestLineLow = 0;

        @SerializedName("global_reserve_price")
        private double mGlobalReservePrice = 0;

        @SerializedName("sequence_flow")
        private ArrayList<DspEngine> mSequenceFlow = new ArrayList<>();

        public SlotInfo() {
        }

        public void setmSlotId(String mSlotId) {
            this.mSlotId = mSlotId;
        }

        public void setmSlotName(String mSlotName) {
            this.mSlotName = mSlotName;
        }

        public void setmOpenStatus(boolean mOpenStatus) {
            this.mOpenStatus = mOpenStatus;
        }

        public void setmCacheStrategy(int mCacheStrategy) {
            this.mCacheStrategy = mCacheStrategy;
        }

        public void setmLoadStrategy(int mLoadStrategy) {
            this.mLoadStrategy = mLoadStrategy;
        }

        public void setmNativeSwitch(int mNativeSwitch) {
            this.mNativeSwitch = mNativeSwitch;
        }

        public void setmPreloaDnFlow(int mPreloaDnFlow) {
            this.mPreloaDnFlow = mPreloaDnFlow;
        }

        public void setmAdBestLineHigh(int mAdBestLineHigh) {
            this.mAdBestLineHigh = mAdBestLineHigh;
        }

        public void setmAdBestLineLow(int mAdBestLineLow) {
            this.mAdBestLineLow = mAdBestLineLow;
        }

        public void setmGlobalReservePrice(double mGlobalReservePrice) {
            this.mGlobalReservePrice = mGlobalReservePrice;
        }

        public void setmSequenceFlow(ArrayList<DspEngine> mSequenceFlow) {
            this.mSequenceFlow = mSequenceFlow;
        }

        public String getSlotId() {
            return mSlotId;
        }

        public String getSlotName() {
            return mSlotName;
        }

        public int getCacheStrategy() {
            return mCacheStrategy;
        }

        public int getLoadStrategy() {
            return mLoadStrategy;
        }

        public int getNativeSwitch() {
            return mNativeSwitch;
        }

        public boolean isFlowMode() {
            return mNativeSwitch == 1;
        }

        public int getAdBestLineHigh() {
            return mAdBestLineHigh;
        }

        public int getAdBestLineLow() {
            return mAdBestLineLow;
        }

        public int getmPreloaDnFlow() {
            return mPreloaDnFlow;
        }

        public double getGlobalReservePrice() {
            return mGlobalReservePrice;
        }

        public List<DspEngine> getSequenceFlow() {
            return mSequenceFlow;
        }

        public boolean isSlotEnable() {
            return mOpenStatus;
        }

        protected boolean isValid(int slot_idx) {
            if (TextUtils.isEmpty(mSlotId)) {
                return false;
            }
            if (TextUtils.isEmpty(mSlotName)) {
                return false;
            }
            if (mSequenceFlow == null) {
                return false;
            }
            for (int flow_idx = 0; flow_idx < mSequenceFlow.size(); ++flow_idx) {
                DspEngine dspEngine = mSequenceFlow.get(flow_idx);
                if (dspEngine == null) {
                    return false;
                }
                if (!dspEngine.isValid(slot_idx, flow_idx)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public String toString() {
            return "SlotInfo{" +
                    "mSlotId='" + mSlotId + '\'' +
                    ", mSlotName='" + mSlotName + '\'' +
                    ", mOpenStatus=" + mOpenStatus +
                    ", mCacheStrategy=" + mCacheStrategy +
                    ", mLoadStrategy=" + mLoadStrategy +
                    ", mNativeSwitch=" + mNativeSwitch +
                    ", mPreloaDnFlow=" + mPreloaDnFlow +
                    ", mAdBestLineHigh=" + mAdBestLineHigh +
                    ", mAdBestLineLow=" + mAdBestLineLow +
                    ", mSequenceFlow=" + mSequenceFlow +
                    '}';
        }
    }

    public static class DspEngine implements Serializable {
        @SerializedName("dsp_name")
        private String mName = null;

        @SerializedName("ad_unit_id")
        private String mAdUnitId = null;

        @SerializedName("impressions")
        private int mImpressions = 1;

        @SerializedName("limit")
        private int mAdLimit = 1000;

        @SerializedName("ad_size")
        private String mAdSize = null;

        @SerializedName("ad_error_num")
        private int mAdErrorNum = 0;

        @SerializedName("fb_click_area")
        private int mFbClickArea = 0;

        @SerializedName("admob_type")
        private int mAdmobType = 0;

        @SerializedName("reserve_price")
        private double mReservePrice = 0.0;

        public DspEngine() {
        }

        public void setName(String mName) {
            this.mName = mName;
        }

        public void setAdUnitId(String mAdUnitId) {
            this.mAdUnitId = mAdUnitId;
        }

        public void setmImpressions(int mImpressions) {
            this.mImpressions = mImpressions;
        }

        public void setmAdLimit(int mAdLimit) {
            this.mAdLimit = mAdLimit;
        }

        public void setmAdSize(String mAdSize) {
            this.mAdSize = mAdSize;
        }

        public void setmAdErrorNum(int mAdErrorNum) {
            this.mAdErrorNum = mAdErrorNum;
        }

        public void setmFbClickArea(int mFbClickArea) {
            this.mFbClickArea = mFbClickArea;
        }

        public void setmAdmobType(int mAdmobType) {
            this.mAdmobType = mAdmobType;
        }

        public void setReservePrice(double mReservePrice) {
            this.mReservePrice = mReservePrice;
        }

        public String getName() {
            return mName;
        }

        public String getAdUnitId() {
            return mAdUnitId;
        }

        public int getAdErrorNum() {
            return mAdErrorNum;
        }

        public int getFbClickArea() {
            return mFbClickArea;
        }

        public int getAdmobType() {
            return mAdmobType;
        }

        public int getImpressions() {
            if (mImpressions == 0) {
                mImpressions = 1;
            }
            return mImpressions;
        }

        public int getAdLimit() {

            if (mAdLimit == 0) {
                mAdLimit = 1000;
            }

            return mAdLimit;
        }

        public int getAdWidth() {
            try {
                String items[] = mAdSize.split("x");
                if (items.length != 2) {
                    return AdConfigConstant.INVALID_INT;
                }
                return Integer.valueOf(items[0]);
            } catch (Exception e) {
            }
            return AdConfigConstant.INVALID_INT;
        }

        public int getAdHeight() {
            try {
                String items[] = mAdSize.split("x");
                if (items.length != 2) {
                    return AdConfigConstant.INVALID_INT;
                }
                return Integer.valueOf(items[1]);
            } catch (Exception e) {
            }
            return AdConfigConstant.INVALID_INT;
        }

        public double getReservePrice() {
            return mReservePrice;
        }

        protected boolean isValid(int slot_idx, int flow_idx) {
            if (TextUtils.isEmpty(mName)) {
                return false;
            }
            if (TextUtils.isEmpty(mAdUnitId)) {
                return false;
            }
            return true;
        }
    }

}
