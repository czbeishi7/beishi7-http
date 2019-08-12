package op.beishi7.core.http;

import android.content.Context;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import op.beishi7.core.http.action.BaseHttpAction;
import op.beishi7.core.http.action.HttpActionInternalProxy;
import op.beishi7.core.http.action.IHttpAction;
import op.beishi7.core.http.action.OkHttpActionImpl;

public class CzHttpEngine {
    private static CzHttpEngine sCzHttpEngine;
    
    public static CzHttpEngine getInstance() {
        if (null == sCzHttpEngine) {
            sCzHttpEngine = new CzHttpEngine();
        }
        return sCzHttpEngine;
    }
    
    public final String CHANNEL_OKHTTP = "OkHttp";
    private HttpConfiguration mConfiguration;
    private Context mContext;
    private Map<String, Class<? extends BaseHttpAction>> mHttpActionImplMap;
    private Map<String, BaseHttpAction> mCacheedHttpAction;
    private Map<String, String> mCommonHeaders;
    private Map<String, String> mCommonParams;
    private OnHttpRequestSecuritySignListener mSecuritySignListener;
    private OnHttpRequestLifeCycleCheckListener mLifeCycleCheckListener;
    
    private CzHttpEngine() {
        mHttpActionImplMap = new HashMap<>();
        mHttpActionImplMap.put(CHANNEL_OKHTTP, OkHttpActionImpl.class);
        mCacheedHttpAction = new HashMap<>();
    }
    
    public void init(Context context){
        init(context, new HttpConfiguration.Builder().build());
    }
    
    public void init(Context context, HttpConfiguration configuration) {
        mContext = context.getApplicationContext();
        mConfiguration = configuration;
    }
    
    public IHttpAction getHttpAction() {
        return getHttpAction(CHANNEL_OKHTTP);
    }
    
    /**
     * 获取网络请求实现类实例
     *
     * @param channel 为{@link #CHANNEL_OKHTTP}或{@link #registeHttpActionImpl(String, Class)}注册过的channel。如果channel
     *                不识别，会返回@link #CHANNEL_OKHTTP}实例
     *
     * @return {@link IHttpAction}
     */
    public IHttpAction getHttpAction(String channel){
        BaseHttpAction httpAction = mCacheedHttpAction.get(channel);
        if (null != httpAction){
            return httpAction;
        }
        if (null == mContext){
            throw new NullPointerException("Must be set context first with call method CzHttpEngine.getInstance().init(...)");
        }
        if (null == mConfiguration) {
            mConfiguration = new HttpConfiguration.Builder().build();
        }
        Class<? extends BaseHttpAction> actionClass = mHttpActionImplMap.get(channel);
        if (null == actionClass) {
            return getHttpAction();
        }
        try {
            httpAction = actionClass.newInstance();
            BaseHttpAction proxyAction = new HttpActionInternalProxy(httpAction);
            proxyAction.init(mContext, mConfiguration);
            mCacheedHttpAction.put(channel, proxyAction);
            return proxyAction;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 外部可以注册网络请求实现类{@link BaseHttpAction}，框架只管理请求封装、数据缓存等业务逻辑。框架内部由okhttp负责实际网络请求
     *
     * @param channel   实现类标识
     * @param implClass {@link BaseHttpAction}实现类
     */
    public void registeHttpActionImpl(String channel, Class<? extends BaseHttpAction> implClass) {
        if (null != implClass) {
            mHttpActionImplMap.put(channel, implClass);
        }
    }
    
    public Context getContext() {
        return mContext;
    }
    
    public void setCommonHeaders(Map<String, String> headers) {
        this.mCommonHeaders = headers;
    }
    
    public Map<String, String> getCommonHeader() {
        if (null == mCommonHeaders) {
            mCommonHeaders = new HashMap<>();
        }
        return mCommonHeaders;
    }
    
    public void setCommonParams(Map<String, String> params) {
        this.mCommonParams = params;
    }
    
    public Map<String, String> getCommonParams() {
        if (null == mCommonParams) {
            mCommonParams = new HashMap<>();
        }
        return mCommonParams;
    }
    
    /**
     * @see OnHttpRequestSecuritySignListener
     * @param listener OnHttpRequestSecuritySignListener
     */
    public void setOnHttpReqeustSecuritySignListener(OnHttpRequestSecuritySignListener listener) {
        mSecuritySignListener = listener;
    }
    
    public OnHttpRequestSecuritySignListener getSecuritySignListener() {
        return mSecuritySignListener;
    }
    
    /**
     * @see OnHttpRequestLifeCycleCheckListener
     * @param listener OnHttpRequestSecuritySignListener
     */
    public void setOnHttpRequestLifeCycleCheckListener(OnHttpRequestLifeCycleCheckListener listener) {
        mLifeCycleCheckListener = listener;
    }
    
    public OnHttpRequestLifeCycleCheckListener getLifeCycleCheckListener() {
        return mLifeCycleCheckListener;
    }
    
    public void logConfig(boolean isEnable, String logTag) {
        HttpLogger.sLogEnable = isEnable;
        if (!TextUtils.isEmpty(logTag)) {
            HttpLogger.sLogTag = logTag;
        }
    }
    
}
