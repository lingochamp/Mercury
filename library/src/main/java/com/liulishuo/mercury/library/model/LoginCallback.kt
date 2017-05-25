package com.liulishuo.mercury.library.model

/**
 * Created by echo on 5/23/17.
 */
interface LoginCallback {

    fun loginError()
    fun loginCancel()
    fun loginSuccess(map: Map<String, String>)
}