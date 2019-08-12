package op.beishi7.core.http.action;

import android.content.Context;

import com.google.gson.Gson;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import op.beishi7.core.http.HttpConfiguration;
import op.beishi7.core.http.HttpParams;
import op.beishi7.core.http.HttpParams.HttpMethod;
import op.beishi7.core.http.OnHttpRequestResultListener;
import op.beishi7.core.http.HttpLogger;
import op.beishi7.core.http.util.HttpUtils;

/** http request base on okhttp3 */
public class OkHttpActionImpl extends BaseHttpAction {
    private OkHttpClient mOkHttpClient;
    private ExecutorService mExecutorService;
    private Gson mGson;

    public OkHttpActionImpl() {
        mGson = new Gson();
    }

    @Override
    public void init(Context context, HttpConfiguration configuration) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (null != configuration) {
            builder.connectTimeout(configuration.getConnectTimeout(), TimeUnit.MILLISECONDS);
            builder.writeTimeout(configuration.getWriteTimeout(), TimeUnit.MILLISECONDS);
            builder.readTimeout(configuration.getReadTimeout(), TimeUnit.MILLISECONDS);
        }
        mOkHttpClient = builder.build();
        mExecutorService = Executors.newFixedThreadPool(configuration.getHttpThreadNums());
    }

    @Override
    public <T> void request(HttpParams<T> params, OnHttpRequestResultListener<T> listener) {
        OkHttpRunnable<T> runnable = new OkHttpRunnable<>(mOkHttpClient, mGson, params, listener);
        mExecutorService.execute(runnable);
    }

    @Override
    public <T> HttpResponse<T> syncRequest(HttpParams<T> params) {
        OkHttpRunnable<T> runnable = new OkHttpRunnable<>(mOkHttpClient, mGson, params, null);
        HttpResponse<T> response = runnable.syncRun();
        runnable.release();
        return response;
    }

    static class OkHttpRunnable<T> implements Runnable {
        private OkHttpClient mClient;
        private Gson mGson;
        private HttpParams<T> mParams;
        private OnHttpRequestResultListener<T> mListener;

        public OkHttpRunnable(OkHttpClient client, Gson gson, HttpParams<T> params, OnHttpRequestResultListener<T> listener) {
            mClient = client;
            mGson = gson;
            mParams = params;
            mListener = listener;
        }

        @Override
        public void run() {
            HttpResultDispatcher dispatcher = HttpResultDispatcher.getInstance();
            HttpResponse<T> response = syncRun();
            dispatcher.dispatch(mParams, response, mListener);
            release();
        }

        public HttpResponse<T> syncRun() {
            HttpResponse<T> httpResponse = new HttpResponse<>();
            httpResponse.isSuccess = false;
            try {
                Request request = buildRequest();
                HttpLogger.logD(mParams.toString());
                HttpLogger.logD(request.url().toString());
                Response response = mClient.newCall(request).execute();
                HttpLogger.logD(response.toString());
                // handle http result data
                httpResponse.code = response.code();
                if (response.isSuccessful()) {
                    httpResponse.isSuccess = true;
                    httpResponse.msg = "success";
                    if (mParams.getResultClass() == String.class) {
                        httpResponse.data = (T) response.body().string();
                    } else {
                        httpResponse.data = mGson.fromJson(response.body().charStream(), mParams.getResultClass());
                    }
                } else {
                    httpResponse.msg = "error http code : " + response.code();
                }
            } catch (IllegalArgumentException e) {
                HttpLogger.log(e);
                httpResponse.msg = e.getMessage();
                httpResponse.code = OnHttpRequestResultListener.ERROR_CODE_UNSUPPORT_METHOD;
            } catch (Exception e) {
                HttpLogger.log(e);
                httpResponse.msg = e.getMessage();
                httpResponse.code = OnHttpRequestResultListener.ERROR_CODE_HTTP_EXCEPTION;
            }
            return httpResponse;
        }

        private Request buildRequest() throws IllegalArgumentException {
            Request.Builder reqBuilder = new Request.Builder();
            // handle http request header
            for (String key : mParams.getHeaders().keySet()) {
                reqBuilder.addHeader(key, mParams.getHeaders().get(key));
            }

            // handle http request params
            if (HttpMethod.GET.equals(mParams.getMethod())) {
                reqBuilder.get();
                reqBuilder.url(HttpUtils.generateUrl(mParams.getUrl(), mParams.getParams()));
            } else {
                reqBuilder.url(mParams.getUrl());
                RequestBody requestBody;
                if (null != mParams.getPostObject()) {
                    MediaType jsonType = MediaType.get("application/json; charset=utf-8");
                    requestBody = RequestBody.create(jsonType, mParams.getPostObject().toString());
                } else {
                    FormBody.Builder builder = new Builder();
                    for (String key : mParams.getParams().keySet()) {
                        builder.add(key, mParams.getParams().get(key));
                    }
                    requestBody = builder.build();
                }
                if (HttpMethod.POST.equals(mParams.getMethod())) {
                    reqBuilder.post(requestBody);
                } else if (HttpMethod.PUT.equals(mParams.getMethod())) {
                    reqBuilder.put(requestBody);
                } else if (HttpMethod.DELETE.equals(mParams.getMethod())) {
                    reqBuilder.delete(requestBody);
                } else {
                    throw new IllegalArgumentException("Unsupport method : " + mParams.getMethod());
                }
            }
            return reqBuilder.build();
        }

        public void release() {
            mClient = null;
            mGson = null;
            mParams = null;
            mListener = null;
        }

    }

}
