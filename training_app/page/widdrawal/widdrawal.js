function back(){
    backward();
}
function init(){
    var accountid=loadValue("$pub.accountid");
   
    var rate=loadValue("$pub.rate");
   
    var realp=loadValue("$pub.realp");
  
    var limit=loadValue("$pub.limit");
  
    var stopdate=loadValue("$pub.stopdate");
   
     var bool=compare(stopdate);
    // var bool=compare("2020-11-12");
    log("比较时间"+bool);
    // 比较当前时间是否为期限时间以内,判断当前支取的利息
    if(bool){
        DATA.ratenow=rate;
    }else{
        DATA.ratenow=1.0;
    }
    var ratenow=DATA.ratenow;
    DATA.rate=rate;
    DATA.price=realp*(1+ratenow/100).toFixed(2);
    DATA.incomnow=realp*(ratenow/100).toFixed(2);
    var numincom=realp*(rate/100);
    DATA.incom=numincom.toFixed(2);
    DATA.totalpricenow=(realp*(1+ratenow/100)).toFixed(2);
    DATA.totalprice=(realp*(1+rate/100)).toFixed(2);
    vm.setData();

//点击确认按钮回调事件
$('AuthCode').addEventListener("done", function(data){
    log("done"+data);
    var url="demo/account.tml?flow=datacheck";
    params={};
    params.paypwd=data;
    params.price=DATA.price;
    params.accountid=accountid;
    sendRequest(url,params,"datacheck",onSuccess,onfailed);
    

});
//关闭键盘
$('AuthCode').setParam("closekeyboard","");
}
//跳转登录注册,判断是否已经注册
function jump() {
    if(DATA.logo!="银行logo"){
        Window.info("您已登录");
    }else{
        var url="demo/userinfo.tml?flow=judge";
        params={};
        params.devid=System.info().deviceId;
        sendRequest(url,params,"judge",onSuccess,onfailed);
    }
}
function onSuccess(dic,index){
    log("=====>"+JSON.stringify(dic));
    if("datacheck"==index){
        if(999012==dic.message){
            Window.info("支付密码错误");
            $('AuthCode').setParam("codeValue","");
        }else if(999023==dic.message){
           // Window.info("商品为活期存储，已为您重新计息售出");
            Window.confirm("商品为活期存储，已为您重新计息售出", function(index){
                $('AuthCode').setParam("codeValue","");
                var tran=dic.tran;
                var date=dic.date;
                var cid=dic.accountid;
                saveValue("$pub.accountid",cid);//账户id
                saveValue("$pub.tran",tran);//实际本息
                saveValue("$pub.payway",DATA.payway);//支付方式
                saveValue("$pub.date",date);//交易时间
                DATA.nextPage="consequence";
                forward();          
               
            })
        }else if(999000==dic.message){
            $('AuthCode').setParam("codeValue","");
            var tran=dic.tran;
            var date=dic.date;
            var cid=dic.accountid;
            saveValue("$pub.accountid",cid);//账户id
            saveValue("$pub.tran",tran);//实际本息
            saveValue("$pub.payway",DATA.payway);//支付方式
            saveValue("$pub.date",date);//交易时间
            // removeValue("$pub.accountid");
            // removeValue("$pub.rate");
            // removeValue("$pub.realp");
            // removeValue("$pub.limit");
            // removeValue("$pub.stopdate");
            DATA.nextPage="consequence";
            forward();          
        }else if(999018==dic.message){
            Window.confirm("您未登录，请先登录", function(index){
                if(1==index){
                    jump();
                }
               
            })
        }
    }else if(index=="judge"){
        if(999004==dic.message){
            DATA.nextPage="login";
            vm.setData();
            Window.forward();   
        }else if(999005==dic.message){
            DATA.nextPage="pwd";
            vm.setData();
            saveValue("$pub.flag","index");
            Window.forward();  
        }
    }
}
function onfailed(dic,index){
    //用于清空输入内容
    $('AuthCode').setParam("codeValue","");
}
function btnok(){

}
// 时间比较
function compare(begintime){
    var curTime = new Date();
    //把字符串格式转化为日期类
    var stopttime = new Date(Date.parse(begintime));
    //进行比较
    return curTime>=stopttime;
}                                                             