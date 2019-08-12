package op.beishi7.core.http;

/**
 * 网络请求生命周期检测监听器
 * <p>
 * 网络请求发起时，会通过{@link #getTopContextFlag()}方法获取发起请求的页面标识。当接口请求成功后，会通过{@link #isContextFlagValid(String)}方法来判断，是否将数据返回app
 * </p>
 */
public interface OnHttpRequestLifeCycleCheckListener {
    
    /**
     * 获取网络请求关联页面标识
     * @return flag
     */
    String getTopContextFlag();
    
    /**
     * 页面标识是否无效。有效时接口返回数据会被{@link OnHttpRequestResultListener}回调；无效时则返回数据将被忽略，不会回调给app
     *
     * @param flag 网络请求页面标识，由{@link #getTopContextFlag()}获取
     *
     * @return true 有效，false 无效
     */
    boolean isContextFlagValid(String flag);
    
}
