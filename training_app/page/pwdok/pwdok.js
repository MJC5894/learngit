var gestureLock = {};
//页面进入，判断访问请求（注册/找回密码）
function init() {
    gestureLock = $('GestureLock');
    // 返回手势密码 0-8 组合的字符串
    // gestureLock.setupEncode("aes","asdadasdasdghjkl","12345678");//加密方式及密钥
    // gestureLock.setupDecode("aes","asdadasdasdghjkl","12345678");//解密方式及密钥
    gestureLock.addEventListener('finish',function(password){
        log("1234---"+password)
        log("12345---"+DATA.password)
        var params = {};
        params.pwd= DATA.password;
        params.pwdok=password;
        if("login"!=loadValue("$pub.changPwd")){
            params.changpwd="999009";    
         
            //正常注册
            sendRequest("demo/userinfo.tml?flow=registerandfindpwd",params,"registerand",onSuccess,onfailed);
        }else{
            //密码找回
            params.changpwd="999010"; 
            
            sendRequest("demo/userinfo.tml?flow=registerandfindpwd",params,"findpwd",onSuccess,onfailed);
        }
    });
    gestureLock.addEventListener('begin',function(){
        log("开始绘制");
    }); 
}
function back(){
    backward();
}
function clean() {
    // 清除手势连线
    gestureLock.setParam('clean','');
}

function setValue() {
    // 设置数值到手势密码中，如果使用加密传输，这里传输的是加密的数据，需要提前通知解密方式
    gestureLock.setParam('value','12587');
}
function onSuccess(dic,index){
        if(index=="registerand"){
            if(!dic.message){
                Window.info("两次绘制不一样，请重新绘制");
                clean();
            }else{
                saveValue("$pub.mobile",dic.mobile);
                //手势密码设置成功，进行电子开户
                 DATA.nextPage="fund";
                 forward();
            }
        }else if(index=="findpwd"){
            if(!dic.message){
                Window.info("两次绘制不一样，请重新绘制");
                clean();
            }else{
                //密码找回成功后跳回主框架
                saveValue("$pub.mobile",dic.mobile);
                var opts = {"url":"../nav.tml", "animate":"slideFromRight", "closePrevious":"true"}
                Navigator.present(opts);
            }
        }
        // removeValue("$pub.changPwd");   
}
function onfailed(dic,index){

}