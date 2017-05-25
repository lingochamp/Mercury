package com.liulishuo.mercury.library.qq

import android.app.Activity
import android.content.Context
import android.util.Log
import com.liulishuo.mercury.library.Mercury
import com.liulishuo.mercury.library.model.ILogin
import com.liulishuo.mercury.library.model.MercuryConstants
import com.liulishuo.mercury.library.model.LoginCallback
import com.tencent.connect.UserInfo
import com.tencent.connect.common.Constants
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import org.json.JSONObject

/**
 * Created by echo on 5/23/17.
 */
class QQLoginHelper(context: Context) : ILogin {

    companion object {
        private val TAG = QQLoginHelper::class.simpleName
    }

    private var mAppId = ""
    private var mTencent: Tencent
    lateinit var mCallback: LoginCallback
    private var mContext: Context


    init {
        mAppId = Mercury.instance.qqAppId
        mContext = context
        mTencent = Tencent.createInstance(mAppId, context)
    }


    override fun login(loginCallback: LoginCallback) {
        if (!mTencent.isSessionValid) {
            mCallback = loginCallback
            mTencent.login(mContext as Activity, "all", object : IUiListener {
                override fun onComplete(result: Any?) {
                    try {
                        var result = result as JSONObject
                        val token = result.getString(Constants.PARAM_ACCESS_TOKEN)
                        val expires = result.getString(Constants.PARAM_EXPIRES_IN)
                        val openId = result.getString(Constants.PARAM_OPEN_ID)

                        if (!token.isNullOrEmpty() && !expires.isNullOrEmpty()
                                && !openId.isNullOrEmpty()) {
                            mTencent.setAccessToken(token, expires)
                            mTencent.openId = openId
                            val qqToken  = mTencent.qqToken
                            val userInfo = UserInfo(mContext, qqToken)
                            userInfo.getUserInfo(object : IUiListener {
                                override fun onComplete(userResult: Any?) {
                                    val userResult = userResult as JSONObject
                                    val nickName = userResult.getString("nickname")
                                    val gender = userResult.getString("gender")
                                    val userMap = mapOf(MercuryConstants.QQ_NICK_NAME to nickName, MercuryConstants.QQ_GENDER to gender,
                                            MercuryConstants.QQ_USERID to openId, MercuryConstants.QQ_ACCESSTOKEN to qqToken.toString())
                                    mCallback.loginSuccess(userMap)
                                }

                                override fun onCancel() {
                                    mCallback.loginCancel()
                                }

                                override fun onError(p0: UiError?) {
                                    mCallback.loginError()
                                    val error = p0 as UiError
                                    Log.e(TAG, error?.errorMessage)
                                }

                            })

                        } else {
                            mCallback.loginError()
                        }
                    } catch (exception: Exception) {
                        mCallback.loginError()
                    }


                }

                override fun onCancel() {
                    mCallback.loginCancel()
                }

                override fun onError(p0: UiError?) {
                    mCallback.loginError()
                    val error = p0 as UiError
                    Log.e(TAG, error?.errorMessage)
                }

            })
        } else {
            mTencent.logout(mContext)
        }

    }
}