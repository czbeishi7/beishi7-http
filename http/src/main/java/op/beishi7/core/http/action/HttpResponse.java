package op.beishi7.core.http.action;

public class HttpResponse<T> {
    public boolean isSuccess; // 是否请求成功
    /**
     * 小于等于{@value op.beishi7.core.http.OnHttpRequestResultListener#ERROR_CODE_UNSUPPORT_METHOD}时，定义在{@link op.beishi7.core.http.OnHttpRequestResultListener}
     * <p>大于{@value op.beishi7.core.http.OnHttpRequestResultListener#ERROR_CODE_UNSUPPORT_METHOD}时为http请求结果的code</p>
     */
    public int code;
    public String msg; // 消息信息
    public T data; // 结果数据

    @Override
    public String toString() {
        return "HttpResponse{" + "isSuccess=" + isSuccess + ", code=" + code + ", msg='" + msg + '\'' + ", data=" + data + '}';
    }
}
