var gestureLock = {};
//页面显示加载进行，访问者判断
function init() {
    gestureLock = $('GestureLock');
    if(loadValue("$pub.flag")=="index"){
        DATA.title="忘记密码";
        DATA.pass="pass";
        DATA.pa="pa";
        vm.setData();
    }
    // 返回手势密码 0-8 组合的字符串
    // gestureLock.setupEncode("aes","asdadasdasdghjkl","12345678");//加密方式及密钥
    // gestureLock.setupDecode("aes","asdadasdasdghjkl","12345678");//解密方式及密钥
    gestureLock.addEventListener('finish',function(password){
        log(loadValue("$pub.flag"));
        if(loadValue("$pub.flag")=="index"){//用户登录跳转手势密码页
            removeValue("$pub.flag");
            var params={};
            params.pwd=password;
            params.devid=System.info().deviceId;
            sendRequest("demo/userinfo.tml?flow=login",params,"login",onSuccess,onfailed);
            clean();
        }else if(loadValue("$pub.flag")=="login"){//忘记密码，重新设置密码
            removeValue("$pub.flag");
            log(password.length);
            if(password.length<6){
                Window.info("图形过于简单,请重新绘制");
                clean();
                return ;
            }else{
                DATA.password = password;
              //  DATA.title="请设置手势密码";
                vm.setData();
                DATA.nextPage="pwdOk";
                forward(); 
                clean();
            }
           
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

//找回密码
function findPwd(){
    saveValue("$pub.findPwd","pwd");
    DATA.nextPage="login";
    forward();
}
function onSuccess(dic,index){
    if(index=="login"){
        if(999001==dic.message){
            //判断是否解挂失
            if(1==dic.isloss){
                Window.confirm("系统检测到您的账户正处于挂失状态，是否解除挂失？", function(index){
                    if(0==index){
                        saveValue("$pub.mobile",dic.mobile);
                        // 标记解挂
                        saveValue("$pub.isloss",1);
                        DATA.nextPage="identity";
                        forward();
                    }else{
                        var opts = {"url":"../nav.tml", "animate":"slideFromRight", "closePrevious":"true"}
                        Navigator.present(opts);
                    }
                });
               
            }else{
                saveValue("$pub.mobile",dic.mobile);
                Window.info("登录成功");
                var opts = {"url":"../nav.tml", "animate":"slideFromRight", "closePrevious":"true"}
                Navigator.present(opts);
            }
            
        }else if(999002==dic.message){
            clean();
            Window.info("手势错误");
        }
    }
    
}
function onfailed(dic,index){

}
