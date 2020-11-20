function init(){
    log("注册页面初始化");
    if(loadValue("$pub.findPwd")=="pwd"){
        DATA.style="pwdfind";
        log("pwdfind显示");
        vm.setData();
    }
}

countdown1 = $('CountdownButton1');

//监听验证码结束事件
countdown1.addEventListener('stop',function(){
    log("================stop");
    log("stop:");

});
//验证码点击事件
countdown1.addEventListener('click',function(){
    var params = {};
    DATA.devId=System.info().deviceId;
    params.devid=DATA.devId;
    params.mobile=DATA.mobile;
    if(loadValue("$pub.findPwd")!="pwd"){
        //标记不是密码找回
        removeValue("$pub.findPwd");
        params.findpwd="999009";
    }else{
        // 密码找回
        removeValue("$pub.findPwd");
        saveValue("$pub.changPwd","login");
        params.findpwd="999010";
       
    }
    sendRequest("demo/code.tml?flow=sendcode",params,"sendcode",onSuccess,onfailed);
    //log("================click1");
    // var params = {};
    // DATA.devId=System.info().deviceId;
    // params.devid=DATA.devId;
    // params.mobile=DATA.mobile;
    // sendRequest("demo/code.tml",params,"2",onSuccess,onfailed);
    //log("click1:");
});

    //验证码倒计时开始方法
//countdown1.countdownButtonStart();

// const { V4MAPPED } = require("dns");

if(loadValue("$pub.findPwd")=="pwd"){
    DATA.title="密码找回";
    vm.setData();
}
//用户注册或密码找回
function testRequest(){
    var  url = "demo/code.tml?flow=compare";
    var params = {};
    params.pwd=DATA.pwd;
    sendRequest(url,params,"compare",onSuccess,onfailed);
}

function onSuccess(dic,index){
    if(index=="sendcode"){
        log("success == "+ dic.code);
        log("success == "+ JSON.stringify(dic));
        log("=====>"+dic);
        if(999006==dic.message){
            DATA.mobile="";
            vm.setData();
            Window.info("前填写手机号码");
        }else if(999007==dic.message){
            DATA.mobile="";
            vm.setData();
            Window.info("手机号码格式错误");
        }else if(999008==dic.message){
            DATA.mobile="";
            vm.setData();
            Window.info("手机号码未注册，请检查您的手机号码");
        }else if(999011==dic.message){
            DATA.pwd=dic.code;
            vm.setData();
            Window.info("验证码："+dic.code);

        }
      
    }else if(index=="compare"){
        if(!dic.message){
            Window.info("验证码错误");
        }else{
            DATA.nextPage="pwd";
            saveValue("$pub.flag","login");
            forward(); 
        }
       
    }
        
}
//账户挂失
function loss(){
    DATA.nextPage="identity";
    forward();
}
//页面回退
function back(){
    backward();
}
//组件title值
function showSheet(){
    var sheet = new ActionSheet();
    sheet.options = {"buttons":["账号挂失", "原手机号不在使用", "登录遇到问题","取消"]};
    sheet.click = function(index) {
    window.alert(index)
    };
    sheet.show();
}
function onfailed(dic,index){
    if(index=="sendcode"){
        Window.info("验证码获取失败");
    }


}



