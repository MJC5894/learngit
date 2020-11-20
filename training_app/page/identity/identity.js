function init(){
    if(1==loadValue("$pub.isloss")){
        DATA.btntitle="解除挂失";
        vm.setData();
    }
}
function btnok(){
    params={}
    params.devid=System.info().deviceId;
    params.name=DATA.name;
    params.number=DATA.number;
    params.mobile=DATA.mobile;
    //用于判断是否为解挂失（1为解挂）
    if(1==loadValue("$pub.isloss")){
        params.isclearloss=1;
        url="demo/userinfo.tml?flow=losschekout";
        sendRequest(url,params,"clearloss",onSuccess,onfailed);
    }else{
        params.isclearloss=0;
        url="demo/userinfo.tml?flow=losschekout";
        sendRequest(url,params,"submitinfo",onSuccess,onfailed);
    }
    
    
}
function onSuccess(dic,index){
    if("submitinfo"==index){
        if(999006==dic.message){
            Window.info("请填写完整信息");
        }else if(999007==dic.message){
            DATA.mobile="";
            vm.setData();  
            Window.info("手机号码格式错误,请重新输入");   
        }else if(999013==dic.message){
            DATA.number="";
            vm.setData();  
            Window.info("证件号码格式错误,请重新输入");
        }else if(999025==dic.message){
            Window.info("信息不正确,请核对后提交");
        }else if(999000==dic.message){
            saveValue("$pub.time",dic.date);
            saveValue("$pub.userid",dic.userid);
            DATA.nextPage="loss";
            forward();
        }
    }else if("clearloss"==index){
        if(999006==dic.message){
            Window.info("请填写完整信息");
        }else if(999007==dic.message){
            DATA.mobile="";
            vm.setData();  
            Window.info("手机号码格式错误,请重新输入");   
        }else if(999013==dic.message){
            DATA.number="";
            vm.setData();  
            Window.info("证件号码格式错误,请重新输入");
        }else if(999025==dic.message){
            Window.info("信息不正确,请核对后提交");
        }else if(999021==dic.message){
            Window.confirm("解除挂失失败", function(index){
                removeValue("$pub.isloss");
                var opts = {"url":"../nav.tml", "animate":"slideFromRight", "closePrevious":"true"}
                Navigator.present(opts);
            });
        }else if(999024==dic.message){
            Window.confirm("解除挂失成功", function(index){
                removeValue("$pub.isloss");
                var opts = {"url":"../nav.tml", "animate":"slideFromRight", "closePrevious":"true"}
                Navigator.present(opts);
            });
        }
    }
   
}
function onfailed(dic,index){

}