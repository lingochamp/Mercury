package com.liulishuo.mercury.library

import android.util.Log

/**
 * Created by echo on 5/23/17.
 */
class Mercury private constructor(){

    init {
        Log.d("Mercury","This ($this) is a singleton")
    }

    private object Holder {
        val INSTANCE = Mercury()
    }

    companion object {
        val instance: Mercury by lazy { Holder.INSTANCE }
    }


    var wechatAppId: String = ""
    var wechatSecret: String = ""
    var qqAppId: String = ""
    var weiboAppId: String = ""



    fun initWechatConfig(wechatAppId: String, wechatSecret: String) {
        this.wechatAppId = wechatAppId
        this.wechatSecret = wechatSecret
    }


    fun initWeiboConfig(weiboAppId: String) {

        this.weiboAppId = weiboAppId
    }

    fun initQQConfig(qqAppId: String) {

        this.qqAppId = qqAppId
    }

}