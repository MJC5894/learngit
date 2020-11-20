function back(){
    backward();
}
//跳转商品详情页，标记活期数据
function jump(productid){
    saveValue("$pub.proid",productid);
    saveValue("$pub.proflag","999016");
    DATA.nextPage="details";
    forward();  
}
//跳转死期商品详情页
function jumptype(productid){
    saveValue("$pub.proid",productid);
    saveValue("$pub.proflag","999017");
    DATA.nextPage="details";
    forward();  
}
// js内部添加下拉刷新执行的事件:
$('listviewtypefirst').addEventListener('top', function(){
    var url="demo/product.tml?flow=findproductbytype";
    params={};
    sendRequest(url,params,"findproductbytype",onSuccess,onfailed);
    $('listviewtypefirst').endRefreshTop();
});
$('listviewtypesecond').addEventListener('top', function(){
    var url="demo/product.tml?flow=findproductbytype";
    params={};
    sendRequest(url,params,"findproductbytype",onSuccess,onfailed);
    $('listviewtypesecond').endRefreshTop();
});

function init(){
    var url="demo/product.tml?flow=findproductbytype";
    params={};
    sendRequest(url,params,"findproductbytype",onSuccess,onfailed);
}
function onSuccess(dic,index){
    //Window.info("onSuccess:"+dic.productbyType1);
   
    DATA.list=dic.productbyType0;
    DATA.listtype=dic.productbyType1;
    vm.setData();
   
}
function onfailed(dic,index){
    Window.info("onfailed");
}

