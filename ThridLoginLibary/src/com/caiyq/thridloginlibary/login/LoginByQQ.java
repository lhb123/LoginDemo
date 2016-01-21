package com.caiyq.thridloginlibary.login;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * 具体的QQ登入类
 * @author LZT
 * 
 */
public class LoginByQQ extends LoginImp implements Login {

	private static Tencent mTencent;
	private static IUiListener mUiListener;
	private static UserInfo mInfo;

	public static Tencent getmTencent() {
		return mTencent;
	}

	public static void setmTencent(Tencent mTencent) {
		LoginByQQ.mTencent = mTencent;
	}

	public static IUiListener getmUiListener() {
		return mUiListener;
	}

	public static void setmUiListener(IUiListener mUiListener) {
		LoginByQQ.mUiListener = mUiListener;
	}

	public static UserInfo getmInfo() {
		return mInfo;
	}

	public static void setmInfo(UserInfo mInfo) {
		LoginByQQ.mInfo = mInfo;
	}

	/**
	 * 登入方法，调用此方法完成登录操作，调用前请保证appid的正确性
	 */
	@Override
	public void siginIn(final Context context,
			final LZTAuthListener mAuthListener) {
		Toast.makeText(context, "LoginByQQ.siginIn", Toast.LENGTH_SHORT).show();
		if (!TextUtils.isEmpty(getAppId())) {
			if (mTencent == null) {
				mTencent = Tencent.createInstance(getAppId(), context);
			}
			mUiListener = new IUiListener() {

				@Override
				public void onError(UiError arg0) {
					if (mAuthListener != null) {
						mAuthListener.onError(arg0);
					}
				}

				@Override
				public void onComplete(Object response) {
					JSONObject jsonResponse = (JSONObject) response;
					initOpenidAndToken(jsonResponse);
					if (mAuthListener != null) {
						mAuthListener.onComplete(jsonResponse);
					}

				}

				@Override
				public void onCancel() {
					if (mAuthListener != null) {
						mAuthListener.onCancel();
					}
				}
			};

			if (!mTencent.isSessionValid()) {
				if (mAuthListener != null) {
					mAuthListener.onStart();
				}
				mTencent.login((Activity) context, "all", mUiListener);
			}
		}
	}

	/**
	 * 获取用户的QQ基本信息，请在授权成功后调用此方法获取QQ信息
	 * 
	 * @param context
	 * @param mDataListener
	 */
	public static void getQQUserInfo(Context context,
			final QQGetDateListener mDataListener) {
		if (mTencent != null && mTencent.isSessionValid()) {
			IUiListener listener = new IUiListener() {

				@Override
				public void onError(UiError arg0) {
					if (mDataListener != null) {
						mDataListener.onError(arg0);
					}
				}

				@Override
				public void onComplete(Object response) {
					if (mDataListener != null) {
						mDataListener.onComplete((JSONObject) response);
					}
				}

				@Override
				public void onCancel() {
					if (mDataListener != null) {
						mDataListener.onCancel();
					}
				}
			};
			mInfo = new UserInfo(context, mTencent.getQQToken());
			mInfo.getUserInfo(listener);
		}
	}

	private void initOpenidAndToken(JSONObject jsonObject) {
		try {
			String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
			String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
			String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
			if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
					&& !TextUtils.isEmpty(openId)) {
				mTencent.setAccessToken(token, expires);
				mTencent.setOpenId(openId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
