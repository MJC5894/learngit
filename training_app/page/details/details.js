function back(){
    backward();
}
// 初始化页面，进行数据封装
function init(){
    var flag=loadValue("$pub.proflag");
    if("999017"==flag){
        DATA.titlediv="定期产品整存整取";
    }else if("999016"==flag){
        DATA.titlediv="活期产品随时可取";
    }
    var proid=loadValue("$pub.proid");
    var url="demo/product.tml?flow=findproductbyid";
    params={};
    params.id=proid;
    sendRequest(url,params,"findproductbyid",onSuccess,onfailed);
 
}
// 跳转购买支付页面
function jumppay(){
    DATA.nextPage="pay";
    forward();
}
function onSuccess(dic,index){
    DATA.rate=dic.message.rate;
    DATA.ratemin=(dic.message.rate*0.8).toFixed(2);
    DATA.ratemod=(dic.message.rate*0.9).toFixed(2);
    DATA.cyclicity=dic.message.cyclicity;
    DATA.titlediv=dic.message.name;
    DATA.minpp=dic.message.minpp;
    DATA.chtitle=dic.message.title;
    DATA.saletotal=dic.message.saletotal;
    DATA.dec=dic.message.describe;
    vm.setData();
}
function onfailed(dic,index){

}

