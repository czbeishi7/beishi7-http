package op.beishi7.core.http;

public class HttpConfiguration {
    private int connectTimeout = 10_000;
    private int readTimeout = 10_000;
    private int writeTimeout = 10_000;
    private int httpThreadNums = 12;
    
    private HttpConfiguration(){}
    
    public int getConnectTimeout() {
        return connectTimeout;
    }
    
    public int getReadTimeout() {
        return readTimeout;
    }
    
    public int getWriteTimeout() {
        return writeTimeout;
    }
    
    public int getHttpThreadNums() {
        return httpThreadNums;
    }
    
    public static class Builder {
        private HttpConfiguration configuration;
        
        public Builder(){
            configuration = new HttpConfiguration();
        }
        
        public Builder connectionTimeout(int timeMill) {
            configuration.connectTimeout = timeMill;
            return this;
        }
        
        public Builder readTimeout(int timeMill) {
            configuration.readTimeout = timeMill;
            return this;
        }
        
        public Builder writeTimeout(int timeMill) {
            configuration.writeTimeout = timeMill;
            return this;
        }
        
        public Builder httpThreadNums(int nums) {
            configuration.httpThreadNums = nums;
            return this;
        }
        
        public HttpConfiguration build() {
            HttpConfiguration tmpConfig = new HttpConfiguration();
            tmpConfig.connectTimeout = configuration.connectTimeout;
            tmpConfig.readTimeout = configuration.readTimeout;
            tmpConfig.writeTimeout = configuration.writeTimeout;
            tmpConfig.httpThreadNums = configuration.httpThreadNums;
            return tmpConfig;
        }
        
    }
    
}
