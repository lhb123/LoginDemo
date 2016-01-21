package com.example.commontest.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.caiyq.thridloginlibary.login.LoginByWX;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth.Resp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

	public static final String TAG = "WXEntryActivity";
	public static BaseResp mResp = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LoginByWX.api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		LoginByWX.api.handleIntent(intent, this);
		this.finish();
	}

	@Override
	public void onReq(BaseReq req) {
		// 发送请求时回调的方法
	}

	@Override
	public void onResp(BaseResp resp) {
		// 返回数据时回调的方法
		// code:03143691d6fdd081e46b1c8f01ad7daj

		// 注意，这个code每次授权返回的都不一样，但是并不影响获取个人用户信息的结果，需要将这个code传给我们自己公司的服务器，通过我们自己公司服务器调用两个微信的接口，服务器获取到个人信息后将信息返回给客户端

		String result = "";

		String name = resp.getClass().getName();// 测试resp具体的子类是哪个类
		Log.e("------------", name);

		Resp response = (Resp) resp;// 后来发现是SendAuth.Resp的code
		String code = response.code;
		Log.e("------------", "code:" + code);

		if (resp != null) {
			mResp = resp;
		}
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			result = "登录成功";
			Log.e(TAG, "当前信息" + resp.toString());
			Toast.makeText(this, result, Toast.LENGTH_LONG).show();
			finish();
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = "登录取消";
			Toast.makeText(this, result, Toast.LENGTH_LONG).show();
			finish();
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = "登录被拒绝";
			Toast.makeText(this, result, Toast.LENGTH_LONG).show();
			finish();
			break;
		default:
			finish();
			break;
		}
	}

}