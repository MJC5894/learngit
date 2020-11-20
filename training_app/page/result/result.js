function back(){
    backward();
}
function init(){
    DATA.way=loadValue("$pub.way");//续存方式
    removeValue("$pub.way");
    DATA.payway=loadValue("$pub.payway");//付款方式
    removeValue("$pub.payway");
    DATA.price=loadValue("$pub.price");//购买价格
    removeValue("$pub.price");
    DATA.paydate=loadValue("$pub.paydate");//付款时间
    removeValue("$pub.paydate");
    DATA.time=loadValue("$pub.time");//到期时间
    removeValue("$pub.time");
    DATA.limit=loadValue("$pub.limit");//利率周期
    var limit=loadValue("$pub.limit");//利率周期
    removeValue("$pub.limit");
    var rate=loadValue("$pub.rate");//利率
    removeValue("$pub.rate");
    DATA.titlediv=loadValue("$pub.title");//产品标题
    removeValue("$pub.title");
    if(3 == limit){
        var num = rate*0.8;
        DATA.rate = num.toFixed(2);
    }else if(6 == limit){
        var num = rate*0.9;
        DATA.rate = num.toFixed(2);
    }else{
        DATA.rate = rate;
    }
    vm.setData();
}
function btnok(){
    removeValue("$pub.proid");
    removeValue("$pub.proflag");
    var opts = {"url":"../nav.tml", "animate":"slideFromRight", "closePrevious":"true"}
    Navigator.present(opts);
}