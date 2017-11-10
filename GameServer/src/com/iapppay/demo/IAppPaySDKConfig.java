package com.iapppay.demo;

/**
 *应用接入iAppPay云支付平台sdk集成信息 
 */
public class IAppPaySDKConfig{

	/**
	 * 应用名称：
	 * 应用在iAppPay云支付平台注册的名称
	 */
	public final static  String APP_NAME = "testFAQ";

	/**
	 * 应用编号：
	 * 应用在iAppPay云支付平台的编号，此编号用于应用与iAppPay云支付平台的sdk集成 
	 */
	public final static  String APP_ID = "3006139651";

	/**
	 * 商品编号：
	 * 应用的商品在iAppPay云支付平台的编号，此编号用于iAppPay云支付平台的sdk到iAppPay云支付平台查找商品详细信息（商品名称、商品销售方式、商品价格）
	 * 编号对应商品名称为：1
	 */
	public final static  int WARES_ID_1=1;

	/**
	 * 应用私钥：
	 * 用于对商户应用发送到平台的数据进行加密
	 */
	public final static String APPV_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJQloNtrmVZF3Iu7vNMTBIu4Fjy/OdTO+7bw9PhXGHtRx1KFY4jZBAP+gADpt3EWIh8PUceFNvcij+Wtok6+ZygXFj2G5L+hzY6jQ8iK24MTi4+6u7p08vMxqGVQonGh6ECuJ/Lp/jtakNhgF3uV3Ouxab0Z+lTcsEaFW6wA0+IlAgMBAAECgYBY9lupR+z1pNAyzRMwAVyadnpMEl0tEPqROhMxSkP2Uf6K7HUACaniqCK/6Zlx/GOTGUT3tmRydbvJpcIw3R/eRNX9W6gdHaENcb1EqbK4CN87htKh84DzkGH/+n5wN/QAaz/IuyEIV5uKl4otHtGPq8AXplPNroCmIUDJBdAl4QJBANn4I8HsMd+3KQVlUnKxmrvUYr6B2vLozZBkJlqAf+2T438uPDglBM/lby4Q4JpXQfsjvZzQkPV4bzJs+VUp0I0CQQCt/sqWceJrxeKrBhR5x7Yj+vrpBciLeOzDE0mr+wpPU6Bj8YYs4IWAGZHotETJRh62pU/DV2qgyZroO7UhzG35AkBI65fi14bz5g3GLViA4GP4tJv4Se8a1sF2rdqa6mRh8djpAmnJCrFqb8JbMfBCEjETuKhFnwzv4kOpJgXf9OY9AkA80vsDCKC8SBrq6l8yCyyS+2PHHexzRTyPHSj94+5AuN0y0vWyrnI8y7OEA7CgI9EMzFFL2j0+I/vw/x5cyFgRAkEArG6/i08eEp8HJFcutSEMQgAql3psQuASiDWL1nb0+g7sFcy1XsCkAZAnx/pPkLKv28hM1mH+sOxXieD8yXSFEA==";

	/**
	 * 平台公钥：
	 * 用于商户应用对接收平台的数据进行解密
	
	 */
	public final static String PLATP_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCaV9p1dl21mITGnkyckOySUVrCBlDowFaNpKBQPG+ORj01+RUjyH8FiU9cnLaCMwGU6tbI/sWvmjw814m/5SzHKvBJ0rYDarGpYNSuRphzFN6OPdrreviydhggoW6vyG14ye4zkzuydpicRH7Z50uk+W7f4DiBF0MygC65zAqM4QIDAQAB";

}