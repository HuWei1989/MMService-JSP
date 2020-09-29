package org.windmill.util;

import java.security.MessageDigest;

public class TextUtil {
    private static TextUtil instance=null;
    //十六进制下数字到字符的映射数组
    private final  String[] hexDigits = {"0", "1", "2", "3", "4",
            "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    private TextUtil(){}
    public static TextUtil getInstance(){
        if(instance==null){
            instance=new TextUtil();
        }
        return instance;
    }

    public  String encryptByMD5(String originStr){
        String encryptStr="";
        try {
            MessageDigest digest= MessageDigest.getInstance("MD5");
            encryptStr = byteArrayToHexString(digest.digest(originStr.getBytes("utf-8")));
        }catch (Exception ex){

        }
        return encryptStr;
    }

    /**
     * 转换字节数组为十六进制字符串
     * @return    十六进制字符串
     */
    private  String byteArrayToHexString(byte[] b){
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++){
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /** 将一个字节转化成十六进制形式的字符串     */
    private  String byteToHexString(byte b){
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
}
