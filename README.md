Beishi7-http
======
基于android应用开发常用的业务场景，封装了一套方便使用的网络请求工具。
* 网络请求默认使用OkHttp，也支持app自己管理网络请求
* 支持http/https get/post-form/post-json/put/delete
* 支持同步和异步请求
* 支持全局Headers、Params
* 支持返回javabean
* 支持请求关联页面，请求是否发送和结果回调用户可自定义
* 支持用户自定义Header、Params安全加密

初始化
------
```java
CzHttpEngine.getInstance().init(Context)
CzHttpEngine.getInstance().init(Context,HttpConfiguration)
```

网络请求
------
使用CzHttpEngine.getInstance().getHttpAction()获得IHttpAction发起网络请求：
``` java
// 使用IHttpAction发起网络请求
IHttpAction httpAction = CzHttpEngine.getInstance().getHttpAction();
String url = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=";
HttpParams<String> params = HttpParams.builder().url(url).build();
httpAction.request(params, new OnHttpRequestResultListener<String>() {
    @Override
    public void onHttpResult(HttpResponse<T> result) {
        // TODO handle result
    }
});
``` 

框架内也提供了通用异步请求接口帮组类CzHttpApis：
``` java
// 使用CzHttpApis发起网络请求
String url = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm";
Map<String, String> params = new HashMap<String, String>();
params.put("tel", "13500000000");
CzHttpApis.get(url, params, new OnHttpRequestResultListener<String>() {
    @Override
    public void onHttpResult(HttpResponse<T> result) {
        // TODO handle result
    }
});

```

框架内也提供了通用同步请求接口帮组类CzHttpSyncApis：
``` java
// 使用CzHttpSyncApis发起网络请求
String url = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm";
Map<String, String> params = new HashMap<String, String>();
params.put("tel", "13500000000");
HttpResponse<T> result = CzHttpSyncApis.get(url, params);
// TODO handle result

```

全局配置
------
* 自定义网络请求管理：CzHttpEngine.getInstance().registeHttpActionImpl(...)
* 全局Header：CzHttpEngine.getInstance().setCommonHeaders(...)
* 全局Params：CzHttpEngine.getInstance().setCommonParams(...)
* 日志配置：CzHttpEngine.getInstance().logConfig(...)
* 网络请求生命周期管理：CzHttpEngine.getInstance()..setOnHttpRequestLifeCycleCheckListener(..)
* 安全签名管理：CzHttpEngine.getInstance().setOnHttpReqeustSecuritySignListener(..)

Release
------
```
implementation("op.beishi7.core:http:1.0.2")

repositories {
    jcenter()     
}
```

License
-------
```
Copyright 2019 beishi7, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```