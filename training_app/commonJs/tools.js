
/**
 * 显示网络请求进度窗
 */
function lock() {
    Window.lock();
}

/**
 * 关闭网络请求进度窗
 */
function unlock() {
    Window.unlock();
}

/**
 * 获取指定元素对象
 * @param {stirng} id 元素的id值
 */
function $(id) {
    return Document.getElementById(id);
}

/**
 * 获取指定元素的value值
 * @param {string} id  元素的id值
 * @returns {string} value 元素的value属性的值
 */
function getValue(id) {
    var element = $(id);
    return element.attributeForName("value");
}

/**
 * 设置指定元素的value值
 * @param {string} id   元素的id值
 * @param {string} value 设置的value值
 */
function setValue(id, value) {
    var element = $(id);
    element.setAttribute("value", isNull(value) ? "" : value);
}

/**
 * 获取指定元素指定属性名的值
 * @param {string} id 元素的id值
 * @param {string} attributeName 元素的属性名
 */
function getAttribute(id, attributeName) {
    var element = $(id);
    return element.attributeForName(attributeName);
}

/**
 * 设置指定元素的指定属性的值
 * @param {string} id 元素的id值
 * @param {string} attributeName 元素的属性名
 * @param {string} attributeValue 想要设置的属性值
 */
function setAttribute(id, attributeName, attributeValue) {
    var element = $(id);
    element.setAttribute(attributeName, attributeValue);
}

/**
 * 设置指定元素的css样式
 * @param {string} id 元素的id值
 * @param {sting} styleName css样式名
 * @param {any} styleValue css样式值
 */
function setStyle(id, styleName, styleValue) {
    var element = $(id);
    element.setStyle(styleName, styleValue);
}

/**
 * 移除指定父元素下的某个元素
 * @param {string} pid 父元素的id值
 * @param {string} id 被移除元素的id值
 */
function removeChild(pid, id) {
    var element = $(pid);
    element.removeChild(id);
}

/**
 * 清空指定父容器下的所有元素
 * @param {string} id 父容器id值
 */
function removeChildren(id) {
    var element = $(id);
    element.removeChild(id);
}

/**
 * 想指定元素插入指定的tml片段
 * @param {string} id 元素的id值
 * @param {string} direction 方向 "up" - 插入到头部，"other" - 插入到底部
 * @param {string} TMLString 要插入的tml片段
 */
function insertInnerTML(id, direction, TMLString) {
    var element = $(id);
    element.insertInnerTML(direction, TMLString);
}




/**
 * 弹出页面展示一段提示性的文字
 * @param {string} message 文字消息
 * @author shanghs
 */
function Alert(message) {
    Tips(message, "../../../image/public/tip_info.png")
}


/**
 * 保存数据
 * @param {string} key 缓存的key，需要指定$pub还是$local
 * @param {any} value 缓存的数据
 */
function saveValue(key, value) {
   
    Window.saveValue(key, value);
}


/**
 * 提取数据
 * @param {string} key 缓存的key，需要指定$pub还是$local
 */
function loadValue(key) {
    let value = Window.loadValue(key);
   
    return value
}

/**
 * 删除数据
 * @param {string} key 缓存的key，需要制定$pub还是$local
 */
function removeValue(key) {
    log("remove value, key: {}", key);
    Window.removeValue(key);
}




function toLoginPage() {
    let opts = {
        "url": "login.xml",
        "animate": "slideFromBottom",
        "closePrevious": "false"
    }
    Navigator.present(opts);
}

/**
 * 页面流进行页面跳转
 */
function forward() {
    Window.forward();
}

/**
 * 页面返回
 */
function backward() {
    Window.backward()
}

/**
 * 关闭键盘
 */
function closeKeyBoard() {
    Window.closeKeyBoard();
}

/**
 * 判断数值是否为空
 * @param str	判断的值
 * @returns {boolean}
 */
function isNull(str) {
    if (str == "null" || str == null || typeof (str) == "undefined" || str == "undefined" || str == "") {
        return true;
    }
    return false;
}

/**
 * 日志输入
 * @param {any} msg 消息
 */
function log(msg) {
    if (isNull(msg)) {
        return;
    }
    Console.print(msg);
}

/**
 * 判断是否是android设备
 */
function isAndroidVersion() {
    let version = System.version();
    if (version.indexOf('android') > -1 || version.indexOf('Android') > -1) {
        return true;
    }
    return false;
}

/**
 * 格式化手机号：13215650000 ==>> 132****0000
 */
function hideMobile(mobile) {
    mobile += ""
    return mobile.replace(/^(\d{3}).+(\d{4})$/, "$1****$2");
}


function isBangs(){

    //基准屏20 刘海屏44
    log("----gaodu------>"+System.statusBarHeight())
    if(System.statusBarHeight() >20){
        return true;
    }

}


/**
 * 弹出页面展示一段文字
 * @param {string} message 文字消息
 * @author shanghs
 */
function Tips(message) {
    saveValue("$pub.commonTipsContent", message);
    openPopUpWithAutoClose("file:///mainApp/page/popup/tips/tips.tml");
}

