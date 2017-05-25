package com.liulishuo.mercury.demo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.liulishuo.mercury.library.Mercury
import com.liulishuo.mercury.library.model.LoginCallback
import com.liulishuo.mercury.library.qq.QQLoginHelper
import com.liulishuo.mercury.library.wechat.WechatLoginHelper
import com.liulishuo.mercury.library.weibo.WeiboLoginHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    var mWeiboLoginHelper: WeiboLoginHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        qq_login_btn.setOnClickListener { _ ->
            //TODO 需要开发者取申请id
            Mercury.instance.initQQConfig("")
            var qqLoginHelper = QQLoginHelper(this@MainActivity)
            qqLoginHelper.login(object : LoginCallback {
                override fun loginError() {

                }

                override fun loginCancel() {

                }

                override fun loginSuccess(userInfoMap: Map<String, String>) {
                    //todo login success
                }

            })
        }



        wechat_login_btn.setOnClickListener { _ ->
            //TODO 需要开发者取申请id
            Mercury.instance.initWechatConfig("", "")
            val wechatLoginHelper = WechatLoginHelper(this@MainActivity)
            wechatLoginHelper.login(object : LoginCallback {
                override fun loginError() {
                }

                override fun loginCancel() {
                }

                override fun loginSuccess(userInfoMap: Map<String, String>) {
                }

            })
        }


        weibo_login_btn.setOnClickListener { _->
            //TODO 需要开发者取申请id
            Mercury.instance.initWeiboConfig("")
            mWeiboLoginHelper = WeiboLoginHelper(this@MainActivity)
            mWeiboLoginHelper!!.login(object : LoginCallback{
                override fun loginError() {

                }

                override fun loginCancel() {

                }

                override fun loginSuccess(map: Map<String, String>) {

                }

            })

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(mWeiboLoginHelper!=null){
            mWeiboLoginHelper!!.onActivityResult(requestCode, resultCode, data)
        }
    }

}
