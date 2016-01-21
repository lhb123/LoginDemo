package com.caiyq.thirdlogindemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.caiyq.thridloginlibary.login.LZTAuthListener;
import com.caiyq.thridloginlibary.login.LoginByQQ;
import com.caiyq.thridloginlibary.login.LoginByWB;
import com.caiyq.thridloginlibary.login.LoginByWX;
import com.caiyq.thridloginlibary.login.LoginImp;
import com.caiyq.thridloginlibary.login.QQGetDateListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class MainActivity extends Activity implements OnClickListener {
	private LoginImp mLoginImp;

	private Context mContext;
	private Button qqlogin;
	private Button qqlogout;
	private Button sinalogin;
	private Button wxlogin;
	private Button otherActicvity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		qqlogin = (Button) findViewById(R.id.qqlogin);
		qqlogin.setOnClickListener(this);
		qqlogout = (Button) findViewById(R.id.qqlogout);
		qqlogout.setOnClickListener(this);
		sinalogin = (Button) findViewById(R.id.sinalogin);
		sinalogin.setOnClickListener(this);
		wxlogin = (Button) findViewById(R.id.wxlogin);
		wxlogin.setOnClickListener(this);
		otherActicvity = (Button) findViewById(R.id.otherActicvity);
		otherActicvity.setOnClickListener(this);
		mContext = MainActivity.this;
		mLoginImp = new LoginImp();
	}

	private void showToast(String string) {
		Toast.makeText(mContext, string, Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.qqlogin:

//			mLoginImp.setAppId("1104973713");
			mLoginImp.setAppId("1104991018");//自己的
			mLoginImp.setmLogin(new LoginByQQ());
			mLoginImp.siginIn(mContext, new LZTAuthListener() {

				@Override
				public void onStart() {
					showToast("授权开始");

				}

				@Override
				public void onError(Object arg0) {
					showToast("授权错误:" + arg0.toString());

				}

				@Override
				public void onComplete(Object response) {
					showToast("授权登录成功" + response.toString());
					Log.e("MainActivity", "授权登录成功" + response.toString());
					LoginByQQ.getQQUserInfo(mContext, new QQGetDateListener() {

						@Override
						public void onStart() {
						}

						@Override
						public void onError(UiError arg0) {
							Log.e("MainActivity", "获取信息出错" + arg0.toString());
						}

						@Override
						public void onComplete(Object response) {
							showToast("获取信息成功" + response.toString());
							Log.e("MainActivity",
									"获取信息成功" + response.toString());
						}

						@Override
						public void onCancel() {
						}
					});

				}

				@Override
				public void onCancel() {
					// TODO Auto-generated method stub

				}
			});

			break;
		case R.id.qqlogout:

			Tencent mTencent = LoginByQQ.getmTencent();
			if (mTencent != null) {
				mTencent.logout(mContext);
			}
		

			break;
		case R.id.sinalogin:

			mLoginImp.setmLogin(new LoginByWB());
//			mLoginImp.setAppId("1515066051");
			mLoginImp.setAppId("2381722571");//自己的
			mLoginImp.siginIn(mContext, new LZTAuthListener() {

				@Override
				public void onStart() {
					showToast("授权开始");
				}

				@Override
				public void onError(Object arg0) {
					showToast("授权错误");
				}

				@Override
				public void onComplete(Object response) {
					// 新浪微博只需认证成功就会返回用户信息
					String json = response.toString();
					Log.e("sinaOnComplete", json);
					showToast("获取用户信息成功：" + json);
				}

				@Override
				public void onCancel() {
					showToast("授权取消");
				}
			});
		

			break;
		case R.id.wxlogin:

			mLoginImp.setmLogin(new LoginByWX());
			mLoginImp.setAppId("wxf55ec4325d132e83");
			mLoginImp.siginIn(mContext, new LZTAuthListener() {

				@Override
				public void onStart() {
					showToast("授权开始");
				}

				@Override
				public void onError(Object arg0) {
					showToast("授权错误");
				}

				@Override
				public void onComplete(Object response) {

				}

				@Override
				public void onCancel() {
					showToast("授权取消");
				}
			});
		

			break;
		case R.id.otherActicvity:

			break;

		default:
			break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constants.REQUEST_LOGIN
				|| requestCode == Constants.REQUEST_APPBAR) {
			IUiListener listener = LoginByQQ.getmUiListener();
			if (listener != null) {
				Tencent.onActivityResultData(requestCode, resultCode, data,
						listener);
			}
		}
		SsoHandler ssoHandler = LoginByWB.getmSsoHandler();
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}
}
