package com.liulishuo.mercury.library.wechat

import android.content.Context
import android.widget.Toast
import com.liulishuo.mercury.library.Mercury
import com.liulishuo.mercury.library.model.ILogin
import com.liulishuo.mercury.library.model.LoginCallback
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory

/**
 * Created by echo on 5/23/17.
 */
class WechatLoginHelper(context: Context) : ILogin {

    companion object {

        private val SCOPE = "snsapi_userinfo"

        private val STATE = "wechat_sdk_demo_lls"

        private lateinit var mIWXAPI: IWXAPI

        private lateinit var mCallBack: LoginCallback


        fun getIWXAPI(): IWXAPI {
            return mIWXAPI
        }


        fun getLoginCallBack(): LoginCallback {
            return mCallBack
        }

    }

    init {
        val wechatAppId = Mercury.instance.wechatAppId
        mIWXAPI = WXAPIFactory.createWXAPI(context, wechatAppId, true)
        if (!mIWXAPI.isWXAppInstalled) {
            Toast.makeText(context, "请安装微信~", Toast.LENGTH_SHORT).show()
        } else {
            mIWXAPI.registerApp(wechatAppId)
        }
    }

    override fun login(loginCallback: LoginCallback) {
        var req = SendAuth.Req()
        req.scope = SCOPE
        req.state = STATE
        mIWXAPI.sendReq(req)
        mCallBack = loginCallback
    }


}