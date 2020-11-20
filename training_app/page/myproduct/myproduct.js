function back(){
    backward();
}
//跳转商品详情页，标记活期数据
function jump(accountid){
    saveValue("$pub.accountid",accountid);
    // saveValue("$pub.proflag","999016");
    DATA.nextPage="accountdetails";
    forward();  
}
//跳转死期商品详情页
function jumptype(accountid){
    saveValue("$pub.accountid",accountid);
    // saveValue("$pub.proflag","999017");
    DATA.nextPage="accountdetails";
    forward();  
}
// js内部添加下拉刷新执行的事件:
$('listviewtypefirst').addEventListener('top', function(){
    // var url="demo/product.tml?flow=findproductbytype";
    // params={};
    // sendRequest(url,params,"findproductbytype",onSuccess,onfailed);
     $('listviewtypefirst').endRefreshTop();
});
$('listviewtypesecond').addEventListener('top', function(){
    // var url="demo/product.tml?flow=findproductbytype";
    // params={};
    // sendRequest(url,params,"findproductbytype",onSuccess,onfailed);
     $('listviewtypesecond').endRefreshTop();
});

function init(){
    var url="demo/account.tml?flow=getaccountinfo";
    params={};
  
    sendRequest(url,params,"getaccountinfo",onSuccess,onfailed);
}
function onSuccess(dic,index){
    //Window.info("onSuccess:"+dic.productbyType1);
  if("getaccountinfo"==index){
     DATA.list=dic.listFlex; 
     DATA.listtype=dic.listFlexed;
     DATA.total=dic.totalprice;
    //  log(dic.listFlex.length+"长度");
    //  var pri=0.0;
    //  for(var i=0;i<dic.listFlexed.length;i++){
    //     pri=pri+dic.listFlexed[i].realp;
    //  }
    //  log(pri+"价格");
     vm.setData();
  }
   
}
function onfailed(dic,index){
    Window.info("onfailed");
}

