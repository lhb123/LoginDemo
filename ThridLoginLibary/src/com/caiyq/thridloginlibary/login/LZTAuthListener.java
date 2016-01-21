package com.caiyq.thridloginlibary.login;

/**
 * 用于验证登录授权的回调方法
 * 
 * @author zhangyan@lzt.com.cn
 * 
 */
public interface LZTAuthListener {

	/**
	 * 授权开始
	 */
	public void onStart();

	/**
	 * 授权错误
	 * 
	 * @param arg0
	 */
	public void onError(Object arg0);

	/**
	 * 授权成功
	 * 
	 * @param response
	 */
	public void onComplete(Object response);

	/**
	 * 授权取消
	 */
	public void onCancel();

}
