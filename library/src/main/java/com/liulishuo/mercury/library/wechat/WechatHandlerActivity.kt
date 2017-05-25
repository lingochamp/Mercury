package com.liulishuo.mercury.library.wechat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.liulishuo.mercury.library.Mercury
import com.liulishuo.mercury.library.model.LoginCallback
import com.liulishuo.mercury.library.model.MercuryConstants
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler


/**
 * Created by echo on 5/23/17.
 */
open class WechatHandlerActivity : Activity(), IWXAPIEventHandler {

    companion object {
        //BaseResp的getType函数获得的返回值，1:第三方授权， 2:分享
        private var TYPE_LOGIN = 1
    }

    private lateinit var mContext: Context
    lateinit var mIWXAPI: IWXAPI
    lateinit var mCallBack: LoginCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this@WechatHandlerActivity
        mIWXAPI = WechatLoginHelper.getIWXAPI()
        handlerIntent(intent, this)
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handlerIntent(intent, this)
    }


    fun handlerIntent(intent: Intent?, handler: IWXAPIEventHandler) {
        try {
            if (!isFinishing && mIWXAPI != null) {
                mIWXAPI.handleIntent(intent, handler)
            }
        } catch (ex: Exception) {
            finish()
        }
        finish()
    }


    override fun onResp(response: BaseResp) {
        mCallBack = WechatLoginHelper.getLoginCallBack()
        when (response.errCode) {
            BaseResp.ErrCode.ERR_OK ->
                if (response.type == TYPE_LOGIN) {
                    val code = (response as SendAuth.Resp).code
                    var resultMap = mapOf(MercuryConstants.WECHAT_APPID to Mercury.instance.wechatAppId,
                            MercuryConstants.WECHAT_SECRET to Mercury.instance.wechatSecret,
                            MercuryConstants.WECHAT_CODE to code, MercuryConstants.WECHAT_GRANT_TYPE to "authorization_code")
                    //resultMap 封装了请求微信用户信息所需要的参数
                    mCallBack.loginSuccess(resultMap)
                }

            BaseResp.ErrCode.ERR_USER_CANCEL ->
                mCallBack.loginCancel()


            BaseResp.ErrCode.ERR_SENT_FAILED ->
                mCallBack.loginError()
            else ->
                mCallBack.loginError()
        }

    }

    override fun onReq(request: BaseReq) {
        finish()
    }
}