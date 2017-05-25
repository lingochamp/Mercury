package com.liulishuo.mercury.library.weibo

import android.app.Activity
import android.content.Intent
import com.liulishuo.mercury.library.Mercury
import com.liulishuo.mercury.library.model.ILogin
import com.liulishuo.mercury.library.model.LoginCallback
import com.liulishuo.mercury.library.model.MercuryConstants
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.auth.*
import com.sina.weibo.sdk.auth.sso.SsoHandler

/**
 * Created by echo on 5/24/17.
 */
class WeiboLoginHelper(context: Activity) : ILogin {

    companion object {
        val REDIRECT_URL = "http://www.liulishuo.com"
        val SCOPE = "friendships_groups_read,friendships_groups_write,statuses_to_me_read," + "follow_app_official_microblog"
    }

    lateinit var mCallBack: LoginCallback
    var mContext: Activity

    private var mAppId = ""

    lateinit var mSsoHandler: SsoHandler
    lateinit var mAuthInfo: AuthInfo

    init {
        mAppId = Mercury.instance.weiboAppId
        mContext = context
    }

    override fun login(loginCallback: LoginCallback) {
        mCallBack = loginCallback
        AccessTokenKeeper.clear(mContext)
        mAuthInfo = AuthInfo(mContext, mAppId, REDIRECT_URL, SCOPE)
        WbSdk.install(mContext, mAuthInfo)
        mSsoHandler = SsoHandler(mContext)
        mSsoHandler.authorize(object : WbAuthListener {
            override fun onSuccess(accessToken: Oauth2AccessToken?) {
                if (accessToken != null && accessToken.isSessionValid) {
                    AccessTokenKeeper.writeAccessToken(mContext, accessToken)
                    var map = mapOf(MercuryConstants.WEIBO_UID to accessToken.uid)
                    mCallBack.loginSuccess(map)
                }
            }

            override fun onFailure(p0: WbConnectErrorMessage?) {
                mCallBack.loginError()
            }

            override fun cancel() {
                mCallBack.loginCancel()
            }

        })
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (this.mSsoHandler != null) {
            this.mSsoHandler.authorizeCallBack(requestCode, resultCode, data)
        }

    }
}