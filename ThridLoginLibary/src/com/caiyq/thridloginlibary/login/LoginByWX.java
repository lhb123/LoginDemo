package com.caiyq.thridloginlibary.login;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.sina.weibo.sdk.utils.LogUtil;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
/**
 * 具体的微信登入类
 * @author LZT
 *
 */
public class LoginByWX extends LoginImp implements Login{
	/**
	 * 在WXEntryActivity类里通过调用这个属性，来完成发送请求和接受数据。具体请参考微信开发平台。
	 */
	public static IWXAPI api;
	/**
	 * 登入方法，调用此方法完成登录操作，调用前请保证appid的正确性
	 */
	@Override
	public void siginIn(Context context, LZTAuthListener mAuthListener) {
		Log.e("LoginByWX", getAppId()+"appid");
		LogUtil.e("LoginByWX", getAppId()+"appid");
		if (mAuthListener != null) {
			mAuthListener.onStart();
		}
		api = WXAPIFactory.createWXAPI(context, getAppId(), true);
		api.registerApp(getAppId());
		final SendAuth.Req req = new SendAuth.Req();
		req.scope = "snsapi_userinfo";
		req.state = "wechat_sdk_demo_test";
		boolean state = api.sendReq(req);
		if (!state) {
			if (mAuthListener != null) {
				mAuthListener.onError(null);
			}
		}
	}

}
