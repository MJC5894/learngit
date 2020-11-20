function back(){
    backward();
}
function init(){
    var flag=loadValue("$pub.proflag");
    // if("999017"==flag){
    //     DATA.titlediv="定期整存整取";
    //     DATA.title="定期购买";
    //     saveValue("$pub.title", DATA.titlediv);
    //     vm.setData();
    // }else if("999016"==flag){
    //     DATA.titlediv="活期随时可取";
    //     DATA.title="活期购买";
    //     saveValue("$pub.title", DATA.titlediv);
    //     vm.setData();
    // }
   
    var proid=loadValue("$pub.proid");
   
    var url="demo/product.tml?flow=findproductbyid";
    params={};
    params.id=proid;
    sendRequest(url,params,"findproductbyid",onSuccess,onfailed);

   
    // 密码键盘操作
//$('AuthCode').setupEncode("aes","asdadasdasdghjkl","12345678");//加密方式及密钥

//密码校验，内容不得包含上述内容
// var ddd =["1234567890","8888888888888"];
// $('AuthCode').setParam("passcheck",ddd);

//获取焦点时回调
// $('AuthCode').addEventListener("focus", function(data){
// 	log("获取焦点时回调"+data);

//});
//失去焦点时回调
// $('AuthCode').addEventListener("blur", function(data){
// 	log("失去焦点时回调"+data);

// });
//收起键盘时回调
// $('AuthCode').addEventListener("keyboardDone", function(data){
// 	log("收起键盘时回调"+data);

// });
//输入内容变化时回调
// $('AuthCode').addEventListener("textChange", function(data, length){
// 	log("输入内容变化时回调"+data);

// });
//密码校验失败
// $('AuthCode').addEventListener("passCheck", function(code,message){
//code == 1001,message = "password length < "+maxLength
//code == 1000,message = "password is too simple"
// 	log("密码校验失败"+code+message);
// });

//用于清空输入内容
// $('AuthCode').setParam("codeValue","");

//点击确认按钮回调事件
$('AuthCode').addEventListener("done", function(data){
    log("done"+data);
    $('AuthCode').setParam("codeValue","");
    var paypwd=data;
    paramspay={};
    paramspay.id=proid;
    paramspay.paypwd=paypwd;
    paramspay.protitle=DATA.titlediv;
    paramspay.option=DATA.options.option_value;
    paramspay.rate=DATA.rate;
    paramspay.time=DATA.timesec;
    paramspay.way=DATA.way;
    paramspay.payway=DATA.payway;
    paramspay.price=DATA.price;
    saveValue("$pub.price",DATA.price);
    log("=====》"+paramspay.option_value);
    sendRequest("demo/account.tml?flow=regist",paramspay,"regist",onSuccess,onfailed);

});
//关闭键盘
$('AuthCode').setParam("closekeyboard","");
}
// select使用js
(function(){
	var select = $("select")
	select.addEventListener("focus", function(){
		Window.info("获取焦点");
	})
    select.focus();
    select.addEventListener("select", function(value, position){
        // Window.info("value:" + value + " position:" + position);
        var now = new Date();
        log("时间====》"+now+"value"+value);
        var num=value;
        if("3"==value){
            var newDate = DateAdd("m ", 3, now);
            saveValue("$pub.limit",3);
           
        }
        if("6"==value){
            var newDate = DateAdd("m ", 6, now);
            saveValue("$pub.limit",6);
         
        }
        if("12"==value){
            var newDate = DateAdd("m ", 12, now);
            saveValue("$pub.limit",12);
           
        }
        // 加上选择的月.
        DATA.time=formatDate(newDate);
        saveValue("$pub.way",DATA.way);
        saveValue("$pub.payway",DATA.payway);
        saveValue("$pub.paydate",formatDateSec(new Date()));
        DATA.timesec=formatDateSec(newDate);
        saveValue("$pub.time",DATA.timesec);
         vm.setData();
    });
})();
function onSuccess(dic,index){
    if("findproductbyid"==index){
        let options=[];
        let options3={};
        let options6={};
        let options12={};
        options3.option_value=3;
        options6.option_value=6;
        options12.option_value=12;
        saveValue("$pub.rate",dic.message.rate);
        options3.option_caption="3个月-("+(dic.message.rate*0.8).toFixed(2)+"%)";
        options6.option_caption="6个月-("+(dic.message.rate*0.9).toFixed(2)+"%)";
        options12.option_caption="12个月-("+dic.message.rate+"%)";
        options.push(options3);
        options.push(options6);
        options.push(options12);
        log("options======》》》"+options);
        DATA.options=options;
        DATA.rate=dic.message.rate;
        var t=dic.total;
        DATA.total=t.toFixed(2);
        DATA.titlediv=dic.message.name;
        vm.setData();
        saveValue("$pub.title", DATA.titlediv);
       
        log("===?》》》");
    }else if("regist"==index){
       if(999014==dic.message){
           Window.info("请填写完整数据");
       }else if(999018==dic.message){
           Window.info("请先登录");
       }else if(999012==dic.message){
           Window.info("支付密码错误");
       }else if(999019==dic.message){
           Window.info("余额不足，请更换支付方式");
       }else if(999020==dic.message){
           Window.info("不在购买时间，购买失败");
       }else if(999021==dic.message){
           Window.info("商品账户创建失败");
       }else if(999022==dic.message){
            Window.info("低于最低购买额");
            DATA.price="";
       }else if(999000==dic.message){
          DATA.nextPage="result";
          forward();
       }
    }
    
}
function onfailed(dic,index){
    if("regist"==index){
        log("购买失败");
    }
}





	/*
 *   功能:实现VBScript的DateAdd功能.时间相加
 *   参数:interval,字符串表达式，表示要添加的时间间隔.
 *   参数:number,数值表达式，表示要添加的时间间隔的个数.
 *   参数:date,时间对象.
 *   返回:新的时间对象.
 *   var now = new Date();
 *   var newDate = DateAdd( "d", 5, now);
 *---------------   DateAdd(interval,number,date)   -----------------
 */
function DateAdd(interval, number, date) {
    switch (interval) {
    case "y ": {
        date.setFullYear(date.getFullYear() + number);
        return date;
        break;
    }
    case "q ": {
        date.setMonth(date.getMonth() + number * 3);
        return date;
        break;
    }
    case "m ": {
        date.setMonth(date.getMonth() + number);
        return date;
        break;
    }
    case "w ": {
        date.setDate(date.getDate() + number * 7);
        return date;
        break;
    }
    case "d ": {
        date.setDate(date.getDate() + number);
        return date;
        break;
    }
    case "h ": {
        date.setHours(date.getHours() + number);
        return date;
        break;
    }
    case "m ": {
        date.setMinutes(date.getMinutes() + number);
        return date;
        break;
    }
    case "s ": {
        date.setSeconds(date.getSeconds() + number);
        return date;
        break;
    }
    default: {
        date.setDate(d.getDate() + number);
        return date;
        break;
    }
    }
}
// 时间格式化
function formatDate(date) {
    var d = new Date(date),
      month = '' + (d.getMonth() + 1),
      day = '' + d.getDate(),
      year = d.getFullYear();
   
    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;
    return year+"年"+month+"月"+day+"日";
}
function formatDateSec(date) {
    var d = new Date(date),
      month = '' + (d.getMonth() + 1),
      day = '' + d.getDate(),
      year = d.getFullYear();
   
    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;
    return year+"-"+month+"-"+day;
}