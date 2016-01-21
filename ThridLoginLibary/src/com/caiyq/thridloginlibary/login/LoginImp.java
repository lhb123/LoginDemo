package com.caiyq.thridloginlibary.login;

import android.content.Context;

import com.sina.weibo.sdk.utils.LogUtil;

/**
 * 登入的实现类，这还不是具体的登入类，具体的登入类要继承这个类。
 * 
 * @author LZT
 * 
 */
public class LoginImp implements Login {
	private static final String TAG = LoginImp.class.getSimpleName();
	private static String AppId;
	private static LoginImp mLogin;

	/**
	 * 取得AppId
	 * 
	 * @return
	 */
	public String getAppId() {
		return AppId;
	}

	/**
	 * 设置AppId
	 * 
	 * @param appId
	 */
	public void setAppId(String appId) {
		LoginImp.AppId = appId;
	}

	/**
	 * 取得具体的登录类，目前有QQ，微信，微博(LoginByQQ,LoginByWX,LoginByWB)
	 * 
	 * @return
	 */
	public LoginImp getmLogin() {
		return mLogin;
	}

	/**
	 * 设置具体的登录类
	 * 
	 * @param mLogin
	 */
	public void setmLogin(LoginImp mLogin) {
		LoginImp.mLogin = mLogin;
	}

	/**
	 * 登入方法，调用此方法完成登入，调用这个方法前需要设置具体的登录类，也就是说要先调用setmLogin(LoginImp
	 * mLogin)方法，和setAppId(String appId)方法。
	 */
	@Override
	public void siginIn(Context context, LZTAuthListener mAuthListener) {

		mLogin.siginIn(context, mAuthListener);
	}

}
