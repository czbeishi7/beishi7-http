package op.beishi7.core.http;

import android.support.annotation.StringDef;

import org.json.JSONObject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

public class HttpParams<T> {
    private String url;
    private Map<String, String> params;
    private Map<String, String> headers;
    private JSONObject postObject;
    private Class<T> resultClass;
    private @HttpMethod
    String method = HttpMethod.GET;
    private boolean isGlobalRequest;
    private boolean useCommonHeaders = true;
    private boolean useCommonParams = true;
    private String key;
    private String contextFlag;
    
    public static Builder<String> builder() {
        Builder<String> builder = new Builder<>();
        builder.resultClass(String.class);
        return builder;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public Map<String, String> getParams() {
        if (null == params) {
            params = new HashMap<>();
        }
        return params;
    }
    
    public Map<String, String> getHeaders() {
        if (null == headers) {
            headers = new HashMap<>();
        }
        return headers;
    }
    
    public JSONObject getPostObject() {
        return postObject;
    }
    
    public Class<T> getResultClass() {
        return resultClass;
    }
    
    public String getMethod() {
        return method;
    }
    
    public boolean isGlobalRequest() {
        return isGlobalRequest;
    }
    
    public boolean isUseCommonHeaders() {
        return useCommonHeaders;
    }
    
    public boolean isUseCommonParams() {
        return useCommonParams;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getContextFlag() {
        return contextFlag;
    }
    
    public void setContextFlag(String flag) {
        contextFlag = flag;
    }
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HttpParams{ ");
        stringBuilder.append("Url = " + url);
        stringBuilder.append(", Headers = " + headers);
        stringBuilder.append(", Params = " + params);
        stringBuilder.append(", PostData = " + postObject);
        stringBuilder.append(", Method = " + method)
                     .append(", ResultClass=" + resultClass.getSimpleName())
                     .append(", IsGlobalRequest = " + isGlobalRequest)
                     .append(", UseCommonHeaders = " + useCommonHeaders)
                     .append(", UseCommonParams = " + useCommonParams)
                     .append(", ContextFlag = " + contextFlag)
                     .append(", RequestKey =" + key);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
    
    public static class Builder<T> {
        private HttpParams<T> mHttpParams;
        
        public Builder() {
            mHttpParams = new HttpParams<>();
        }
        
        public Builder<T> url(String url) {
            mHttpParams.url = url;
            return this;
        }
        
        public Builder<T> addHeaders(Map<String, String> header) {
            if (null == mHttpParams.headers) {
                mHttpParams.headers = new HashMap<>();
            }
            if (null != header) {
                mHttpParams.headers.putAll(header);
            }
            return this;
        }
        
        public Builder<T> addHeader(String key, String value) {
            if (null == mHttpParams.headers) {
                mHttpParams.headers = new HashMap<>();
            }
            mHttpParams.headers.put(key, value);
            return this;
        }
        
        public Builder<T> addParams(Map<String, String> params) {
            if (null == mHttpParams.params) {
                mHttpParams.params = new HashMap<>();
            }
            if (null != params) {
                mHttpParams.params.putAll(params);
            }
            return this;
        }
        
        public Builder<T> addParams(String key, String value) {
            if (null == mHttpParams.params) {
                mHttpParams.params = new HashMap<>();
            }
            mHttpParams.params.put(key, value);
            return this;
        }
        
        public Builder<T> postJSONObject(JSONObject postObject) {
            mHttpParams.postObject = postObject;
            return this;
        }
        
        public Builder<T> resultClass(Class<T> resultClass) {
            mHttpParams.resultClass = resultClass;
            return this;
        }
        
        public Builder<T> method(@HttpMethod String method) {
            mHttpParams.method = method;
            return this;
        }
        
        /**
         * 是否是全局网络请求，为true时将不受{@link OnHttpRequestLifeCycleCheckListener}约束
         * @param isGlobalRequest globalRequest
         * @return this
         */
        public Builder<T> globalRequest(boolean isGlobalRequest) {
            mHttpParams.isGlobalRequest = isGlobalRequest;
            return this;
        }
        
        public Builder<T> useCommonHeaders(boolean isUse) {
            mHttpParams.useCommonHeaders = isUse;
            return this;
        }
        
        public Builder<T> useCommonParams(boolean isUse) {
            mHttpParams.useCommonParams = isUse;
            return this;
        }
        
        public HttpParams<T> build() {
            HttpParams<T> result = new HttpParams<>();
            result.url = mHttpParams.url;
            result.headers = mHttpParams.headers;
            result.params = mHttpParams.params;
            result.postObject = mHttpParams.postObject;
            result.resultClass = mHttpParams.resultClass;
            result.method = mHttpParams.method;
            result.isGlobalRequest = mHttpParams.isGlobalRequest;
            result.useCommonHeaders = mHttpParams.useCommonHeaders;
            result.useCommonParams = mHttpParams.useCommonParams;
            return mHttpParams;
        }
    }
    
    @StringDef({HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HttpMethod {
        String GET = "get";
        String POST = "post";
        String PUT = "put";
        String DELETE = "delete";
    }
    
}
