//回退，框架跳转
function back(){
    var opts = {"url":"../nav.tml", "animate":"slideFromRight", "closePrevious":"true"}
    Navigator.present(opts);
}
//安全键盘
function keybord(){
    var options11={"textButton":"Go","title":"SecureKeyboard","textAlign":"left","clearButton":"ALWAY","textHint":"textHint","textTip":"textTip","openKeyboard":"ture","bottomOffset":"20"};
    $('userPwd').setParam("options",options11);
}

function init(){
	//自定义键盘
	$('AuthCode').setupEncode("aes","asdadasdasdghjkl","12345678");//加密方式及密钥

	//密码校验，内容不得包含上述内容
	var ddd =["1234567890","8888888888888"];
	$('AuthCode').setParam("passcheck",ddd);

	//获取焦点时回调
	$('AuthCode').addEventListener("focus", function(data){
		log(data);

	});
	//失去焦点时回调
	$('AuthCode').addEventListener("blur", function(data){
		log(data);

	});
	//收起键盘时回调
	$('AuthCode').addEventListener("keyboardDone", function(data){
		log(data);

	});
	//输入内容变化时回调
	$('AuthCode').addEventListener("textChange", function(data, length){
		log(data);

	});
	//密码校验失败
	$('AuthCode').addEventListener("passCheck", function(code,message){
	//code == 1001,message = "password length < "+maxLength
	//code == 1000,message = "password is too simple"
		log(code+message);
	});

	//用于清空输入内容
	$('AuthCode').setParam("codeValue","");

	//点击确认按钮回调事件
	$('AuthCode').addEventListener("done", function(data){
		log("done"+data);
	})
	//关闭键盘
	$('AuthCode').setParam("closekeyboard","");
}


