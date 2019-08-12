package op.beishi7.core.http.util;

import android.net.Uri;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import op.beishi7.core.http.HttpParams;

public class HttpUtils {
    
    public static void separateParamsFromUrl(HttpParams params) {
        if (null == params || !params.getUrl().contains("?")) {
            return;
        }
        Uri uri = Uri.parse(params.getUrl());
        Set<String> urlParamsKeys = uri.getQueryParameterNames();
        if (null != urlParamsKeys && !urlParamsKeys.isEmpty()) {
            for (String key : urlParamsKeys) {
                params.getParams().put(key, uri.getQueryParameter(key));
            }
            params.setUrl(uri.getScheme() + "://" + uri.getHost() + uri.getPath());
        }
    }
    
    public static String generateUrl(String url, Map<String, String> params) {
        if (null == params || params.isEmpty()) {
            return url;
        }
        StringBuilder urlBuilder = new StringBuilder(url);
        if (!url.contains("?")) {
            urlBuilder.append("?");
        } else if (!url.endsWith("?")) {
            urlBuilder.append("&");
        }
        Set<String> keys = params.keySet();
        boolean isFirst = true;
        for (String key : keys) {
            if (!isFirst) {
                urlBuilder.append("&");
            } else {
                isFirst = false;
            }
            urlBuilder.append(URLEncoder.encode(key)).append("=").append(URLEncoder.encode(params.get(key)));
        }
        return urlBuilder.toString();
    }
    
    public static String generateHttpRequestKey(HttpParams params) {
        StringBuilder sb = new StringBuilder();
        sb.append(params.getUrl());
        sb.append(params.getMethod());
        sb.append(params.getResultClass().getName());
        if (!params.getHeaders().isEmpty()) {
            List<String> keys = getMapKeysWithList(params.getHeaders());
            for (String key : keys) {
                sb.append(key).append(params.getHeaders().get(key));
            }
        }
        if (!params.getParams().isEmpty()) {
            List<String> keys = getMapKeysWithList(params.getParams());
            for (String key : keys) {
                sb.append(key).append(params.getParams().get(key));
            }
        }
        if (null != params.getPostObject()) {
            sb.append(params.getPostObject().toString());
        }
        return String.valueOf(sb.toString().hashCode());
    }
    
    public static List<String> getMapKeysWithList(Map<String, String> map) {
        if (null != map && map.size() > 0) {
            List<String> keyList = new ArrayList<>(map.size());
            for (String key : map.keySet()) {
                keyList.add(key);
            }
            Collections.sort(keyList);
            return keyList;
        }
        return null;
    }
    
}
