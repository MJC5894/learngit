function back(){
    backward();
}
//提交电子账户信息
function submitinfo(){
    var url="demo/fund.tml?flow=register";
    params={};
    params.paypwd=DATA.paypwd;
    params.card=DATA.card;
    params.name=DATA.name;
    params.cardnumber=DATA.cardnumber;
    params.paypwd= DATA.paypwd;
    params.confirmpwd=DATA.confirmpwd;
    sendRequest(url,params,"submitinfo",onSuccess,onfailed);

}
function onSuccess(dic,index){
    if(index=="submitinfo"){
        if(999014==dic.message){
            Window.info("请填写完整数据");
        }else if(999013==dic.message){
            Window.info("身份证格式有误");
            DATA.cardnumber="";
        }else if(999012==dic.message){
            Window.info("两次输入密码不一致，请重新输入。");
            DATA.paypwd="";
            DATA.confirmpwd="";
            vm.setData();
        }else if(999015==dic.message){
            Window.info("支付密码为6位数字");
            DATA.paypwd="";
            DATA.confirmpwd="";
            vm.setData();
        }else if(999999==dic.message){
            Window.info("账户生成失败！！！");
            backward();
        }else if(999000==dic.message){
            var opts = {"url":"../nav.tml", "animate":"slideFromRight", "closePrevious":"true"};
            Navigator.present(opts);
        }
    }
        
}
function onfailed(dic,index){


}