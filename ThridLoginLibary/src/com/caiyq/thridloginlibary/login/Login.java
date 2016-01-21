package com.caiyq.thridloginlibary.login;

import android.content.Context;
/**
 * 登入接口
 * @author LZT
 *
 */
public interface Login {
	/**
	 * 登入方法，实现了这个接口的方法通过调用此方法，完成登录功能。调用前需要设置相应的appid和具体的登入类。
	 * @param context
	 * @param mAuthListener
	 */
	public void siginIn(Context context, LZTAuthListener mAuthListener);

}
