/**
 * 获取一个定制化的Request对象
 */
function getRequest() {
  let language = System.appLang();
  // let token = "token:vZ68yeG0IxYMr0UUij1TpQ==";
  let token = loadValue("$pub.token");
  let request = new Request();
  log("----------------");
  request.method = "POST";
  request.timeout = config.net.timeout * 1000;
  request.addHeader("Content-Type", "application/json; charset=utf-8");
  request.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
  request.addHeader("Content-Encoding", "gzip");
  request.addHeader("token", isNull(token) ? "" : token);
  request.addHeader("version", "1.2");
  request.addHeader("merchantId", "");
  request.addHeader("systemLanguage", "zh");
  return request;
}

/**
 * 通过特定的Request对象发送网络请求
 * @param {string} name 请求对应的接口规范json文件名
 * @param {object} header 请求需所需要的请求头
 * @param {function} onsuccess 业务请求成功的回调方法
 * @param {function} onfailed 业务请求失败的回调方法
 * @param {function} onerror 请求失败的回调方法
 */
function sendRequest(url,params,index, onsuccess, onfailed) {
  if (loadValue("$pub.blockRequest") === "1") {
    return;
  }
  let request = getRequest();
  if(isNull(params)){
    params = {};
  }
  request.data = JSON.stringify(params)
  request.url = BaseUrl + url;

  log("requeset url == " + request.url);
  log("requeset params == " + request.data);
  log("requeset header == " + request.headers);
  // if (!isNull(header)) {
  //   for (let key in header) {
  //     request.addHeader(key, header[key]);
  //   }
  // }
  request.success = function (data) {
    try {
      log("dic.robj=====>"+data)
      var dic = eval('(' + data + ')');
      onsuccess(dic,index);
      unlock();
    } catch (err) {
      log("=== onSuccess catch ===")
      unlock();
      
    }
  };
  request.error = function (data) {
    try{
      var error_dic = eval('(' + data + ')');
      onfailed(error_dic,index);
      unlock();
    } catch (err) {
      log("=== onFailed catch ===");
      unlock();
    }
  };
  request.send();
  lock();
  
}

