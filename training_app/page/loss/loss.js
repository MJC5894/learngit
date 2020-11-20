// js内部添加下拉刷新执行的事件:
$('listviewtypefirst').addEventListener('top', function(){
    // var url="demo/product.tml?flow=findproductbytype";
    // params={};
    // sendRequest(url,params,"findproductbytype",onSuccess,onfailed);
     $('listviewtypefirst').endRefreshTop();
});

function init(){
    var url="demo/account.tml?flow=getaccountinfoall";
    params={};
    params.userid=loadValue("$pub.userid");
    sendRequest(url,params,"getaccountinfoall",onSuccess,onfailed);
}
function onSuccess(dic,index){
    //Window.info("onSuccess:"+dic.productbyType1);
  if("getaccountinfoall"==index){
     DATA.list=dic.list; 
     vm.setData();
  }
   
}
function onfailed(dic,index){
    Window.info("onfailed");
}
function btnok(){
    DATA.nextPage="losssuccess";
    forward();
}
function back(){
    backward();
}