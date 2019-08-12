package op.beishi7.core.http;

import org.json.JSONObject;

import java.util.Map;

import op.beishi7.core.http.HttpParams.Builder;
import op.beishi7.core.http.HttpParams.HttpMethod;
import op.beishi7.core.http.action.HttpResponse;

public class CzHttpSyncApis {

    // -----------  get methods -----------

    public static HttpResponse<String> get(String url, Map<String, String> params) {
        return get(url, params, String.class);
    }

    public static <T> HttpResponse<T> get(String url, Map<String, String> params, Class<T> resultClass) {
        return get(url, null, params, resultClass);
    }

    public static <T> HttpResponse<T> get(String url, Map<String, String> header, Map<String, String> params, Class<T> resultClass) {
        return get(url, header, params, resultClass, false);
    }

    public static <T> HttpResponse<T> get(String url, Map<String, String> header, Map<String, String> params, Class<T> resultClass, boolean gloablRequest) {
        return get(url, header, params, resultClass, gloablRequest, true, true);
    }

    public static <T> HttpResponse<T> get(String url, Map<String, String> header, Map<String, String> params, Class<T> resultClass, boolean globalRequest, boolean commonHeader, boolean commonParams) {
        return finalRequest(HttpMethod.GET, url, header, params, null, resultClass, globalRequest, commonHeader, commonParams);
    }

    // -----------  post form methods -----------

    public static HttpResponse<String> post(String url, Map<String, String> params) {
        return post(url, params, String.class);
    }

    public static <T> HttpResponse<T> post(String url, Map<String, String> params, Class<T> resultClass) {
        return post(url, null, params, resultClass);
    }

    public static <T> HttpResponse<T> post(String url, Map<String, String> header, Map<String, String> params, Class<T> resultClass) {
        return post(url, header, params, resultClass, false);
    }

    public static <T> HttpResponse<T> post(String url, Map<String, String> header, Map<String, String> params, Class<T> resultClass, boolean globalRequest) {
        return post(url, header, params, resultClass, globalRequest, true, true);
    }

    public static <T> HttpResponse<T> post(String url, Map<String, String> header, Map<String, String> params, Class<T> resultClass, boolean globalRequest, boolean commonHeader, boolean commonParams) {
        return finalRequest(HttpMethod.POST, url, header, params, null, resultClass, globalRequest, commonHeader, commonParams);
    }

    // -----------  post json methods -----------

    public static HttpResponse<String> postJson(String url, JSONObject postData) {
        return postJson(url, postData, String.class);
    }

    public static <T> HttpResponse<T> postJson(String url, JSONObject postData, Class<T> resultClass) {
        return postJson(url, null, postData, resultClass, false);
    }

    public static <T> HttpResponse<T> postJson(String url, Map<String, String> header, JSONObject postData, Class<T> resultClass, boolean globalRequest) {
        return postJson(url, header, postData, resultClass, globalRequest, true, true);
    }

    public static <T> HttpResponse<T> postJson(String url, Map<String, String> header, JSONObject postData, Class<T> resultClass, boolean globalRequest, boolean commonHeader, boolean commonParams) {
        return finalRequest(HttpMethod.POST, url, header, null, postData, resultClass, globalRequest, commonHeader, commonParams);
    }

    // -----------  put json methods -----------

    public static HttpResponse<String> put(String url, Map<String, String> params, OnHttpRequestResultListener<String> listener) {
        return put(url, params, String.class);
    }

    public static <T> HttpResponse<T> put(String url, Map<String, String> params, Class<T> resultClass) {
        return put(url, null, params, resultClass);
    }

    public static <T> HttpResponse<T> put(String url, Map<String, String> header, Map<String, String> params, Class<T> resultClass) {
        return put(url, header, params, resultClass, false);
    }

    public static <T> HttpResponse<T> put(String url, Map<String, String> header, Map<String, String> params, Class<T> resultClass, boolean globalRequest) {
        return put(url, header, params, resultClass, globalRequest, true, true);
    }

    public static <T> HttpResponse<T> put(String url, Map<String, String> header, Map<String, String> params, Class<T> resultClass, boolean globalRequest, boolean commonHeader, boolean commonParams) {
        return finalRequest(HttpMethod.PUT, url, header, params, null, resultClass, globalRequest, commonHeader, commonParams);
    }

    // -----------  delete json methods -----------

    public static HttpResponse<String> delete(String url, Map<String, String> params) {
        return delete(url, params, String.class);
    }

    public static <T> HttpResponse<T> delete(String url, Map<String, String> params, Class<T> resultClass) {
        return delete(url, null, params, resultClass);
    }

    public static <T> HttpResponse<T> delete(String url, Map<String, String> header, Map<String, String> params, Class<T> resultClass) {
        return delete(url, header, params, resultClass, false);
    }

    public static <T> HttpResponse<T> delete(String url, Map<String, String> header, Map<String, String> params, Class<T> resultClass, boolean globalRequest) {
        return delete(url, header, params, resultClass, globalRequest, true, true);
    }

    public static <T> HttpResponse<T> delete(String url, Map<String, String> header, Map<String, String> params, Class<T> resultClass, boolean globalRequest, boolean commonHeader, boolean commonParams) {
        return finalRequest(HttpMethod.DELETE, url, header, params, null, resultClass, globalRequest, commonHeader, commonParams);
    }

    // -----------  basic request methods -----------

    public static <T> HttpResponse<T> finalRequest(@HttpMethod String method, String url, Map<String, String> header, Map<String, String> params, JSONObject postData, Class<T> resultClass) {
        return finalRequest(method, url, header, params, postData, resultClass, false, true, true);
    }

    public static <T> HttpResponse<T> finalRequest(@HttpMethod String method, String url, Map<String, String> header, Map<String, String> params, JSONObject postData, Class<T> resultClass, boolean globalRequest, boolean commonHeader, boolean commonParams) {
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
        return CzHttpEngine.getInstance().getHttpAction().syncRequest(builder.build());
    }

}
