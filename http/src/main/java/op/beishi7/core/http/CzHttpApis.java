package op.beishi7.core.http;

import org.json.JSONObject;

import java.util.Map;

import op.beishi7.core.http.HttpParams.Builder;
import op.beishi7.core.http.HttpParams.HttpMethod;

public class CzHttpApis {

    // -----------  get methods -----------

    public static void get(String url, Map<String, String> params, OnHttpRequestResultListener<String> listener) {
        get(url, params, String.class, listener);
    }

    public static <T> void get(String url, Map<String, String> params, Class<T> resultClass, OnHttpRequestResultListener<T> listener) {
        get(url, null, params, resultClass, listener);
    }

    public static <T> void get(String url, Map<String, String> header, Map<String, String> params, Class<T> resultClass, OnHttpRequestResultListener<T> listener) {
        get(url, header, params, resultClass, false, listener);
    }

    public static <T> void get(String url, Map<String, String> header, Map<String, String> params, Class<T> resultClass, boolean gloablRequest, OnHttpRequestResultListener<T> listener) {
        get(url, header, params, resultClass, gloablRequest, true, true, listener);
    }

    public static <T> void get(String url, Map<String, String> header, Map<String, String> params, Class<T> resultClass, boolean globalRequest, boolean commonHeader, boolean commonParams, OnHttpRequestResultListener<T> listener) {
        finalRequest(HttpMethod.GET, url, header, params, null, resultClass, globalRequest, commonHeader, commonParams, listener);
    }

    // -----------  post form methods -----------

    public static void post(String url, Map<String, String> params, OnHttpRequestResultListener<String> listener) {
        post(url, params, String.class, listener);
    }

    public static <T> void post(String url, Map<String, String> params, Class<T> resultClass, OnHttpRequestResultListener<T> listener) {
        post(url, null, params, resultClass, listener);
    }

    public static <T> void post(String url, Map<String, String> header, Map<String, String> params, Class<T> resultClass, OnHttpRequestResultListener<T> listener) {
        post(url, header, params, resultClass, false, listener);
    }

    public static <T> void post(String url, Map<String, String> header, Map<String, String> params, Class<T> resultClass, boolean globalRequest, OnHttpRequestResultListener<T> listener) {
        post(url, header, params, resultClass, globalRequest, true, true, listener);
    }

    public static <T> void post(String url, Map<String, String> header, Map<String, String> params, Class<T> resultClass, boolean globalRequest, boolean commonHeader, boolean commonParams, OnHttpRequestResultListener<T> listener) {
        finalRequest(HttpMethod.POST, url, header, params, null, resultClass, globalRequest, commonHeader, commonParams, listener);
    }

    // -----------  post json methods -----------

    public static void postJson(String url, JSONObject postData, OnHttpRequestResultListener<String> listener) {
        postJson(url, postData, String.class, listener);
    }

    public static <T> void postJson(String url, JSONObject postData, Class<T> resultClass, OnHttpRequestResultListener<T> listener) {
        postJson(url, null, postData, resultClass, false, listener);
    }

    public static <T> void postJson(String url, Map<String, String> header, JSONObject postData, Class<T> resultClass, boolean globalRequest, OnHttpRequestResultListener<T> listener) {
        postJson(url, header, postData, resultClass, globalRequest, true, true, listener);
    }

    public static <T> void postJson(String url, Map<String, String> header, JSONObject postData, Class<T> resultClass, boolean globalRequest, boolean commonHeader, boolean commonParams, OnHttpRequestResultListener<T> listener) {
        finalRequest(HttpMethod.POST, url, header, null, postData, resultClass, globalRequest, commonHeader, commonParams, listener);
    }

    // -----------  put json methods -----------

    public static void put(String url, Map<String, String> params, OnHttpRequestResultListener<String> listener) {
        put(url, params, String.class, listener);
    }

    public static <T> void put(String url, Map<String, String> params, Class<T> resultClass, OnHttpRequestResultListener<T> listener) {
        put(url, null, params, resultClass, listener);
    }

    public static <T> void put(String url, Map<String, String> header, Map<String, String> params, Class<T> resultClass, OnHttpRequestResultListener<T> listener) {
        put(url, header, params, resultClass, false, listener);
    }

    public static <T> void put(String url, Map<String, String> header, Map<String, String> params, Class<T> resultClass, boolean globalRequest, OnHttpRequestResultListener<T> listener) {
        put(url, header, params, resultClass, globalRequest, true, true, listener);
    }

    public static <T> void put(String url, Map<String, String> header, Map<String, String> params, Class<T> resultClass, boolean globalRequest, boolean commonHeader, boolean commonParams, OnHttpRequestResultListener<T> listener) {
        finalRequest(HttpMethod.PUT, url, header, params, null, resultClass, globalRequest, commonHeader, commonParams, listener);
    }

    // -----------  delete json methods -----------

    public static void delete(String url, Map<String, String> params, OnHttpRequestResultListener<String> listener) {
        delete(url, params, String.class, listener);
    }

    public static <T> void delete(String url, Map<String, String> params, Class<T> resultClass, OnHttpRequestResultListener<T> listener) {
        delete(url, null, params, resultClass, listener);
    }

    public static <T> void delete(String url, Map<String, String> header, Map<String, String> params, Class<T> resultClass, OnHttpRequestResultListener<T> listener) {
        delete(url, header, params, resultClass, false, listener);
    }

    public static <T> void delete(String url, Map<String, String> header, Map<String, String> params, Class<T> resultClass, boolean globalRequest, OnHttpRequestResultListener<T> listener) {
        delete(url, header, params, resultClass, globalRequest, true, true, listener);
    }

    public static <T> void delete(String url, Map<String, String> header, Map<String, String> params, Class<T> resultClass, boolean globalRequest, boolean commonHeader, boolean commonParams, OnHttpRequestResultListener<T> listener) {
        finalRequest(HttpMethod.DELETE, url, header, params, null, resultClass, globalRequest, commonHeader, commonParams, listener);
    }

    // -----------  basic request methods -----------

    public static <T> void finalRequest(@HttpMethod String method, String url, Map<String, String> header, Map<String, String> params, JSONObject postData, Class<T> resultClass, OnHttpRequestResultListener<T> listener) {
        finalRequest(method, url, header, params, postData, resultClass, false, true, true, listener);
    }

    public static <T> void finalRequest(@HttpMethod String method, String url, Map<String, String> header, Map<String, String> params, JSONObject postData, Class<T> resultClass, boolean globalRequest, boolean commonHeader, boolean commonParams, OnHttpRequestResultListener<T> listener) {
        Builder<T> builder = new Builder<>();
        builder.method(method);
        builder.url(url);
        builder.addHeaders(header);
        builder.addParams(params);
        builder.postJSONObject(postData);
        builder.resultClass(resultClass);
        builder.globalRequest(globalRequest);
        builder.useCommonHeaders(commonHeader);
        builder.useCommonParams(commonParams);
        try {
            CzHttpEngine.getInstance().getHttpAction().request(builder.build(), listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
