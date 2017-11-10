package com.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088911437022414";
	// 商户的私钥
	public static String private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMzWgHxzjosV2aWSkeXUklnE4GaGGM5/O7w9gKVn2DrrWtrS1TRwI7yDeHh58fP/0cgWD3vd5V3ydHm+yrGDroJEJS8e8S3NNiH0DE+6kXJ6GuBRDgc6NHsYgM47LA9zvscpUqjcCEZ9/t9ZXvAHARuoLKB3Mj0/9NB+HfT4kcs/AgMBAAECgYAEh2WV2tQ2/Qev4nq5UybdNz4i5iocPrHqao2ymoYsX1HF7s1p+GTB4UJDn6aa13gi7WHwQiq1Fgw/ONmwbRHpoz1rSfdxozTo3attPA0Tgs+PC0rdc4cUurg0r4q8pQw0izJS7dOV7okth/NQrRt5YriblnqDuJLGBtBIYNcR+QJBAPx7JXcgdAZNBAwv/bLEQJ7mWStDbydH1XgcxES+Ls0OwbD97bD4KAhHo4Ef2Yc3mdBGDDpTawxqxmA93KM3ErsCQQDPsV1UpjehW/Ea3hqlsVuSOgfK0g9CWwXFriuTPZzYtnQsYn1OTocHc7uOmcmST7EoSdClFvfROKnnDUoiN2tNAkEAozzraC3vVfONXmPY3y/Lp/C+Th53SypCdjoDG37qu2GDEW/bscN//npy2BVDiw0DwYjIdx1f5NNUFv1oHY6J6QJACWV32H2QpL85IEMOJ5Lnlq0DbzyjrhfaIgBoPkgaUX+nr95g1usclsy4npH8mZ77X9w/EUkBuzh6D1b9lKC3PQJBAI4r/7I1xl6M7hp5NLBPkbUeJypBfyeCyhoPDoSODE5CGTesh7fJksvUrQ1EMzlyXqZ9EuyQZjUcMYr3tA3XM2k=";
	
	// 支付宝的公钥，无需修改该值
	public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "RSA";

}
