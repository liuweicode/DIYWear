/**
 * 
 */
package com.yeegol.DIYWear.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.sina.weibo.sdk.WeiboSDK;
import com.sina.weibo.sdk.api.BaseResponse;
import com.sina.weibo.sdk.api.IWeiboAPI;
import com.sina.weibo.sdk.api.IWeiboDownloadListener;
import com.sina.weibo.sdk.api.IWeiboHandler;
import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.component.sso.OnAuthListener;
import com.tencent.weibo.sdk.android.component.sso.WeiboToken;
import com.tencent.weibo.sdk.android.network.HttpCallback;
import com.yeegol.DIYWear.util.LogUtil;
import com.yeegol.DIYWear.util.NotificUtil;
import com.yeegol.DIYWear.util.SNSUtil;

/**
 * @author sharl
 * 
 */
public class ShareActivity extends Activity implements IWeiboHandler.Response,
		IWeiboDownloadListener, OnAuthListener {

	Context mContext;

	String TAG = ShareActivity.class.getName();

	Bitmap mBitmap;

	String mMsg;

	IWeiboAPI mIWeiboAPI;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		mIWeiboAPI = WeiboSDK.createWeiboAPI(mContext, SNSUtil.SINA_APP_KEY,
				true);
		mIWeiboAPI.responseListener(getIntent(), this);
		mIWeiboAPI.registerWeiboDownloadListener(this);
		// get data
		Bundle data = getIntent().getExtras();
		mBitmap = data.getParcelable("bitmap");
		mMsg = data.getString("msg");
		if ("1".equals("1")) {
			mIWeiboAPI.registerApp();
			if (!mIWeiboAPI.isWeiboAppInstalled()
					|| !mIWeiboAPI.isWeiboAppSupportAPI()) {
				return;
			}
			SNSUtil.shareToSinaWeibo(this, mIWeiboAPI, "model", mBitmap);
		} else {
			SNSUtil.authOnTencentMicroblog(mContext, SNSUtil.TENCENT_APP_KEY,
					SNSUtil.TENCENT_APP_SECRET_KEY, this);
		}
	}

	@Override
	public void onResponse(BaseResponse arg0) {
		switch (arg0.errCode) {
		case com.sina.weibo.sdk.constant.Constants.ErrorCode.ERR_OK:
			NotificUtil.showShortToast("ok");
			break;
		case com.sina.weibo.sdk.constant.Constants.ErrorCode.ERR_CANCEL:
			NotificUtil.showShortToast("cancel");
			break;
		case com.sina.weibo.sdk.constant.Constants.ErrorCode.ERR_FAIL:
			NotificUtil.showShortToast("fail");
			break;
		}
	}

	@Override
	public void onCancel() {
		NotificUtil.showShortToast("download cancel");
	}

	@Override
	public void onAuthFail(int arg0, String arg1) {
		NotificUtil.showShortToast("auth fail");
	}

	@Override
	public void onAuthPassed(String arg0, WeiboToken arg1) {
		// store authorize data
		Util.saveSharePersistent(mContext, "ACCESS_TOKEN", arg1.accessToken);
		Util.saveSharePersistent(mContext, "EXPIRES_IN",
				String.valueOf(arg1.expiresIn));
		Util.saveSharePersistent(mContext, "OPEN_ID", arg1.openID);
		Util.saveSharePersistent(mContext, "REFRESH_TOKEN", "");
		Util.saveSharePersistent(mContext, "CLIENT_ID", Util.getConfig()
				.getProperty("APP_KEY"));
		Util.saveSharePersistent(mContext, "AUTHORIZETIME",
				String.valueOf(System.currentTimeMillis() / 1000l));
		// share it
		HttpCallback callback = new HttpCallback() {

			@Override
			public void onResult(Object arg0) {
				LogUtil.logDebug(arg0.toString(), TAG);
			}
		};
		SNSUtil.shareToTencentMicroblog(mContext, "", mBitmap, callback);
	}

	@Override
	public void onWeiBoNotInstalled() {
		NotificUtil.showShortToast("weibo not install");
	}

	@Override
	public void onWeiboVersionMisMatch() {
		NotificUtil.showShortToast("weibo version not match");
	}

}
