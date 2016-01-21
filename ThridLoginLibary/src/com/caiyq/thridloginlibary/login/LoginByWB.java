package com.caiyq.thridloginlibary.login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
/**
 * 具体的微博登入类
 * @author LZT
 *
 */
public class LoginByWB extends LoginImp implements Login{
	/**
	* 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
	*
	* <p>
	* 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响，
	* 但是没有定义将无法使用 SDK 认证登录。
	* 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
	* </p>
	* 
	*/
	public static  String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
	/**
	* Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的
	* 微博核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权
	* 页中有权利选择赋予应用的功能。
	* 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接
	* 口的使用权限，高级权限需要进行申请。
	* 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
	*
	* 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
	* 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
	*/
	public static  String SCOPE = "email,direct_messages_read,direct_messages_write,"
			+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
			+ "follow_app_official_microblog," + "invitation_write";
	private AuthInfo mAuthInfo;
	private Bundle wbBundle;
	private LZTAuthListener mAuthListener;
	private static SsoHandler mSsoHandler;
	/**
	 * 注意了：：：：：设置回调的网址，在新浪开发者平台的高级信息处设置，这边的网址要和开发者平台上的网址保持一致，如果设置的不正确将不能正常授权登入
	 * @param rEDIRECT_URL
	 */
	public static void setREDIRECT_URL(String rEDIRECT_URL) {
		REDIRECT_URL = rEDIRECT_URL;
	}

	public static void setSCOPE(String sCOPE) {
		SCOPE = sCOPE;
	}
	public static SsoHandler getmSsoHandler() {
		return mSsoHandler;
	}
	public void setmSsoHandler(SsoHandler mSsoHandler) {
		this.mSsoHandler = mSsoHandler;
	}
/**
 * 登入方法，调用此方法完成登录操作，调用前请保证appid和REDIRECT_URL的正确性
 */
	@Override
	public void siginIn(Context context, LZTAuthListener authListener) {
		if (!TextUtils.isEmpty(getAppId())) {
			if (authListener != null) {
				authListener.onStart();
				this.mAuthListener=authListener;
			}
			if (mAuthInfo == null) {
				Log.e("siginIn", REDIRECT_URL+getAppId());
				mAuthInfo = new AuthInfo(context, getAppId(), REDIRECT_URL,
						SCOPE);
			}
			if (mSsoHandler == null) {
				mSsoHandler = new SsoHandler((Activity) context, mAuthInfo);
			}

			mSsoHandler.authorize(new AuthListener());
		}
	}
	/**
	 * 新浪授权回调方法
	 * 
	 * @author lzt
	 * 
	 */
	private class AuthListener implements WeiboAuthListener {
		@Override
		public void onCancel() {
			if (mAuthListener != null) {
				mAuthListener.onCancel();
				
			}
		}

		@Override
		public void onComplete(Bundle bundle) {
//			Toast.makeText(mContext, "***成功***", Toast.LENGTH_SHORT).show();
			if (mAuthListener != null) {
				mAuthListener.onComplete(bundle);
			}
		}

		@Override
		public void onWeiboException(WeiboException e) {
			if (mAuthListener != null) {
				mAuthListener.onError(e);
			}
		}

	}

}
