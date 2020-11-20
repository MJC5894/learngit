function init(){
    DATA.time=loadValue("$pub.time");
    DATA.userid=loadValue("$pub.userid");
    vm.setData(); 
}
function btnok(){
    var url="demo/userinfo.tml?flow=userloss";
    params={};
    params.userid=DATA.userid;
    sendRequest(url,params,"userloss",onSuccess,onfailed);
}
function onSuccess(dic,index){
    if("userloss"==index){
        if(999000==dic.message){
            //挂失成功
            Window.confirm("挂失成功", function(index){
                removeValue("$pub.time");
                removeValue("$pub.userid");
                var opts = {"url":"../nav.tml", "animate":"slideFromRight", "closePrevious":"true"}
                Navigator.present(opts);
             })

        }else if(999021==dic.message){
            //挂失失败
            Window.confirm("挂失失败", function(index){
                var opts = {"url":"../nav.tml", "animate":"slideFromRight", "closePrevious":"true"}
                Navigator.present(opts);
            })
        }
    }
}
function onfailed(dic,index){

}
