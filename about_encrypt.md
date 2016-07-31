# 加解密相关
### MD5加密
``` java
/**
* MD5加密
*/
public static String encryptMD5(String data) throws Exception {
    MessageDigest md5 = MessageDigest.getInstance("MD5");
    return new BigInteger(md5.digest(data.getBytes())).toString(16);
}
```

### SHA加密
```
/**
* SHA加密
*/
public static String encryptSHA(String data) throws Exception {
    MessageDigest sha = MessageDigest.getInstance("SHA");
    return new BigInteger(sha.digest(data.getBytes())).toString(32);
}
```
