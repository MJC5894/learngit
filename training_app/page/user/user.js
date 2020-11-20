//用户页加载显示
function init(){
    if(loadValue("$pub.mobile")!=null){
        DATA.mobile=loadValue("$pub.mobile");
        DATA.tip="欢迎登录";
        vm.setData();
    }
   
    log(DATA.mobile);
}
//退出登录
function shutdown(){
    if(loadValue("$pub.mobile")!=null){
        removeValue("$pub.mobile");
        var params={};
        sendRequest("demo/userinfo.tml?flow=shutdown",params,"shutdown",onSuccess,onfailed);
    }else{
        Window.info("您还没有登录");
        Window.setTimeout(function(){}, 3000);
        var opts = {"url":"../nav.tml", "animate":"slideFromRight", "closePrevious":"true"}
        Navigator.present(opts);

    }
}
//添加图片
var options = {
    "crop": "true",
    "format":"jpg",
    "height": "50",
    "width": "50",
    "scale": true,
    "quality": "50"
}
function addimg(){
    Window.photo(options, function(path){
        log("====>"+path);
        var requeset=getRequest();
        requeset.url=BaseUrl+"demo/userinfo.tml?flow=addimg";
        requeset.addHeader("Content-Type","multipart/form-data;");//请求头必须指定
        requeset.file=path;//上传文件
        var params={};
        params.devid=System.info.deviceId;

        requeset.data=JSON.stringify(params);
        requeset.success=function(data){
            log("====>>>>"+data);
            log("=======>>>>>"+JSON.stringify(data));
            DATA.img=BaseUrl+"demo/image"+data.img;
            vm.setData();
            log("=====>>>>"+ DATA.img);
        };
        requeset.error=function(e){
            Window.info("请求失败");
        }
        requeset.send();
    } ,function(cancel){});
}
function acten() {
    //菜单列表每一个cell点击事件
    $("GridView").addEventListener("click",function(scheme){
    //scheme是一个object对象
    })
    
}
function onSuccess(dic,index){
    if(index=="shutdown"){
        if(999003==dic.message){
            var opts = {"url":"../nav.tml", "animate":"slideFromRight", "closePrevious":"true"}
            Navigator.present(opts);
        }
      
    }
}
function onfailed(dic,index){

}
