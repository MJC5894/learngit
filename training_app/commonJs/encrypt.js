/**
 * 对输入数据进行base64编码
 * @param {string} input 待编码信息 
 */
function base64Encode(input) {
    return new Encrypt().base64Encode(input);
}

/**
 * 对输入数据进行base64解码
 * @param {string} input 待解码信息
 */
function base64Decode(input) {
    return new Encrypt().base64Decode(value);
}

/**
 * 对输入数据进行sha256加密
 * @param {string} input 待加密数据
 */
function sha256Encrypt(input) {
    return new Encrypt().SHA256(input);
}