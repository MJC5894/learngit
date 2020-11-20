function init(){
    var accountid=loadValue("$pub.accountid");
    removeValue("$pub.accountid");
    saveValue("$pub.accountid",accountid);
    params={};
    params.accountid=accountid;
    var url="demo/account.tml?flow=getaccountbyid"
    sendRequest(url,params,"getaccountbyid",onSuccess,onfailed);
}
// 返回上一页面
function back(){
    backward();
}
//点击跳转
function btnok(){
    DATA.nextPage="widdrawal";
    vm.setData();
    forward();  
}
function onSuccess(dic,index){
   
    // DATA.stopdate=dec.message.space1;
    DATA.list=dic.message;
    vm.setData();
    var rate=DATA.list.space4
    var realp=DATA.list.realp;
    var t=realp*(1+rate/100);
    DATA.price=t.toFixed(2);
    DATA.stopdate=DATA.list.space1;
    DATA.creatdate=DATA.list.creatdate;
    DATA.limit=MonthsBetw(DATA.list.creatdate,DATA.list.space1);
    vm.setData();
    saveValue("$pub.rate",rate);
    saveValue("$pub.realp",realp);
    saveValue("$pub.limit",DATA.limit);
    saveValue("$pub.stopdate",DATA.stopdate);
   // log(JSON.stringify(DATA.list)+"=====>");
    // DATA.price=realp*(1+rate);
   
}
function onfailed(dic,index){

}

// 计算月数差
function MonthsBetw(date1, date2) { //date1和date2是2019-3-12格式
    //用-分成数组
    date1 = date1.split("-");
    date2 = date2.split("-");
    //获取年,月数
    var year1 = parseInt(date1[0]),
        month1 = parseInt(date1[1]),
        year2 = parseInt(date2[0]),
        month2 = parseInt(date2[1]),
        //通过年,月差计算月份差
        months = (year2 - year1) * 12 + (month2 - month1);
    return months;
}