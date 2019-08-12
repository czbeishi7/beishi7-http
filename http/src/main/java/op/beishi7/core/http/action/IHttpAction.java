package op.beishi7.core.http.action;

import op.beishi7.core.http.HttpParams;
import op.beishi7.core.http.OnHttpRequestResultListener;

public interface IHttpAction {

    <T> HttpResponse<T> syncRequest(HttpParams<T> params);

    <T> void request(HttpParams<T> params, OnHttpRequestResultListener<T> listener);

}
