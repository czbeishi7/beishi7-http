package op.beishi7.core.http;

import op.beishi7.core.http.action.HttpResponse;

public interface OnHttpRequestResultListener<T> {
    int ERROR_CODE_UNSUPPORT_METHOD = -10000;
    int ERROR_CODE_HTTP_EXCEPTION = -10001;

    void onHttpResult(HttpResponse<T> result);

}
