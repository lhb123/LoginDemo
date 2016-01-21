package com.caiyq.thridloginlibary.login;

import com.tencent.tauth.UiError;

/**
 * 用于获取授权成功后的获取用户信息的回调方法
 */
public interface QQGetDateListener {

	/**
	 * 获取开始
	 */
	public void onStart();

	/**
	 * 获取错误
	 * 
	 * @param arg0
	 */
	public void onError(UiError arg0);

	/**
	 * 获取成功
	 * 
	 * @param response
	 */
	public void onComplete(Object response);

	/**
	 * 获取取消
	 */
	public void onCancel();

}
