package op.beishi7.core.http.action;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import op.beishi7.core.http.CzHttpEngine;
import op.beishi7.core.http.HttpParams;
import op.beishi7.core.http.OnHttpRequestLifeCycleCheckListener;
import op.beishi7.core.http.OnHttpRequestResultListener;
import op.beishi7.core.http.HttpLogger;

class HttpResultDispatcher {

    private static HttpResultDispatcher sInstance;

    public static HttpResultDispatcher getInstance() {
        if (null == sInstance) {
            sInstance = new HttpResultDispatcher();
        }
        return sInstance;
    }

    private Handler mHandler;

    private HttpResultDispatcher() {
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                doDispatch(msg);
            }
        };
    }

    public <T> void dispatch(HttpParams<T> httpParams, HttpResponse<T> response, OnHttpRequestResultListener<T> listener) {
        DispatchParams<T> params = new DispatchParams<>();
        params.params = httpParams;
        params.response = response;
        params.listener = listener;

        Message message = Message.obtain(mHandler);
        message.obj = params;
        message.sendToTarget();
    }

    private void doDispatch(Message msg) {
        if (null != msg && msg.obj instanceof DispatchParams) {
            DispatchParams params = (DispatchParams) msg.obj;
            // check the http result can callback to app
            OnHttpRequestLifeCycleCheckListener lifeListener = CzHttpEngine.getInstance().getLifeCycleCheckListener();
            if (!params.params.isGlobalRequest() && null != lifeListener && !lifeListener.isContextFlagValid(params.params.getContextFlag())) {
                HttpLogger.logD(params.params.getContextFlag() + " is invalid!");
                params.release();
                return;
            }

            if (null != params.listener) {
                params.listener.onHttpResult(params.response);
            }
            params.release();
        }
    }

    private static class DispatchParams<T> {
        HttpParams<T> params;
        HttpResponse<T> response;
        OnHttpRequestResultListener<T> listener;

        void release() {
            params = null;
            listener = null;
            response = null;
        }
    }

}
