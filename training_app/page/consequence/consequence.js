function init(){
    var tran=loadValue("$pub.tran");
    var way=loadValue("$pub.payway");
    var date=loadValue("$pub.date");
    DATA.tran=tran;
    DATA.way=way;
    DATA.time=date;
    vm.setData();
}
function btnok(){
    var accountid=loadValue("$pub.accountid");
    var url="demo/account.tml?flow=updateaccountinfo";
    params={};
    params.price=DATA.tran;
    params.accountid=accountid;
    sendRequest(url,params,"updateaccountinfo",onSuccess,onfailed);


   
}
function onSuccess(dic,index){
    if("updateaccountinfo"==index){
        if(999000==dic.message){
            removeValue("$pub.accountid");
            removeValue("$pub.rate");
            removeValue("$pub.realp");
            removeValue("$pub.limit");
            removeValue("$pub.stopdate");
            removeValue("$pub.tran");
            removeValue("$pub.payway");
            removeValue("$pub.date");
            var opts = {"url":"../nav.tml", "animate":"slideFromRight", "closePrevious":"true"}
            Navigator.present(opts);
        }else if(999021==dic.message){
            Window.confirm("支取失败", function(index){
               back();
            })

        }
    }
}
function onfailed(dic,index){

}
function back(){
    backward();
}