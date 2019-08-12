package op.beishi7.core.http;

import java.util.Map;

/**
 * 网络请求安全签名监听器
 * <p>
 * {@link #doHttpRequestSecuritySign(Map, Map)}会在网络请求发起前回调给app, 由app实现安全签名逻辑
 * </p>
 */
public interface OnHttpRequestSecuritySignListener {
    
    void doHttpRequestSecuritySign(Map<String, String> finalHeader, Map<String, String> finalParams);
    
}
