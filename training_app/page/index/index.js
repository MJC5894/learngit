function acten() {
    //九宫格每一个cell点击事件
    $("GridView").addEventListener("click",function(scheme){
    //scheme是一个object对象
    log(scheme);
    })
    
    //全部按钮点击事件
    $("GridView").addEventListener("fucitemclick",function(){
   
    })
    
    //完成按钮点击事件
    $("GridView").addEventListener("endEdit",function(scheme){
    //scheme是一个Array对象
    log(scheme);
    })
    
}
//产品详情页跳转
function product(){
    DATA.nextPage="product";
    forward();
}
//页面运行加载,判断是否有用户登录（手机号码）
function init() {
    var info = System. checkRoot()
    log("是否====>"+info);
    if(!info){
        if(loadValue("$pub.mobile")!=null){
            DATA.logo=loadValue("$pub.mobile")+",您好！";
            DATA.submit="欢迎使用";
            vm.setData();
        }
    }else{
        log("安全问题，强制退出！！！");
        Window.setTimeout(function(){}, 3000);//延时3秒
        Window.exitApp()
    }
    

//     log("Hello World");
//     //网络请求回来的数据源
//     $('AIRecommend').setAIRecommend("数据源");
//     //点击相对的cell返回跳转路径
// $('AIRecommend').addEventListener("schemeEvent",function(scheme){
// 	log(scheme);
// })

// $('AIRecommend').addEventListener("recommendMore",function(){
// 	log('recommendMore');
// })

// $('AIRecommend').addEventListener("recommendSetting",function(){
// 	log('recommendSetting');
// })
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
//存款跳转
function deposit(){
    if(loadValue("$pub.mobile")!=null){
        log("deposit");
        DATA.nextPage="deposit";
        forward();
    }else{
        Window.info("请先登录");
        jump();
    }
   
}
//账户信息查看
function countinfo(){
    if(loadValue("$pub.mobile")!=null){
        // var url="";
        // params={};
        // params.devid=System.info().deviceId;
        // params.mobile=loadValue("$pub.mobile");
        // sendRequest(url,params,"1",onSuccess,onfailed);
        DATA.nextPage="myproduct";
        forward();
    }else{
        Window.info("请先登录");
        jump();
    }
}
function onSuccess(dic,index){
    if(index=="judge"){
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


}

// saveValue("$pub.aa","123");
// log(loadValue("$pub.aa"));
