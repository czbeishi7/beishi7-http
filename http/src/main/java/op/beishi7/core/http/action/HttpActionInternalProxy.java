package op.beishi7.core.http.action;

import android.content.Context;

import op.beishi7.core.http.CzHttpEngine;
import op.beishi7.core.http.HttpConfiguration;
import op.beishi7.core.http.HttpParams;
import op.beishi7.core.http.OnHttpRequestLifeCycleCheckListener;
import op.beishi7.core.http.OnHttpRequestResultListener;
import op.beishi7.core.http.OnHttpRequestSecuritySignListener;
import op.beishi7.core.http.util.HttpUtils;

public class HttpActionInternalProxy extends BaseHttpAction {
    private BaseHttpAction mRealActionImpl;

    public HttpActionInternalProxy(BaseHttpAction realImpl) {
        mRealActionImpl = realImpl;
    }

    @Override
    public void init(Context context, HttpConfiguration configuration) {
        mRealActionImpl.init(context, configuration);
    }

    @Override
    public <T> void request(HttpParams<T> params, OnHttpRequestResultListener<T> listener) {
        prepareRequest(params);
        mRealActionImpl.request(params, listener);
    }

    @Override
    public <T> HttpResponse<T> syncRequest(HttpParams<T> params) {
        prepareRequest(params);
        return mRealActionImpl.syncRequest(params);
    }

    private void prepareRequest(HttpParams params) {
        if (null == params.getResultClass()) {
            new IllegalArgumentException("The result class cannot be null, you can set by method HttpParams.Builder.setResultClass(...)");
        }

        // 将url上的参数拆分到params中
        HttpUtils.separateParamsFromUrl(params);

        // 公共header处理
        CzHttpEngine httpClient = CzHttpEngine.getInstance();
        if (params.isUseCommonHeaders()) {
            params.getHeaders().putAll(httpClient.getCommonHeader());
        }

        // 公共params处理
        if (params.isUseCommonParams()) {
            params.getParams().putAll(httpClient.getCommonParams());
        }

        // 获取请求页面标识
        OnHttpRequestLifeCycleCheckListener lifeListener = httpClient.getLifeCycleCheckListener();
        if (null != lifeListener) {
            params.setContextFlag(lifeListener.getTopContextFlag());
        }

        // 生成请求标识，用于数据缓存
        params.setKey(HttpUtils.generateHttpRequestKey(params));

        // 安全签名回调给app处理
        OnHttpRequestSecuritySignListener signListener = httpClient.getSecuritySignListener();
        if (null != signListener) {
            signListener.doHttpRequestSecuritySign(params.getHeaders(), params.getParams());
        }
    }

}
