### What is Mercury

> Mercury 在古希腊他也是众神的信差，这边用来作为第三方登录组件的 `CodeName`，

Mercury是[ShareLoginLib](https://github.com/lingochamp/ShareLoginLib)的Kotlin版本，当前1.0版本只提供登录功能。


### Get Started

* Add it in your root build.gradle at the end of repositories:

```
allprojects {
 		repositories {
 			...
 			maven { url 'https://jitpack.io' }
 		}
 	}
```

* Add the dependency

```
dependencies {
  	        compile 'com.github.lingochamp:Mercury:1.0.0'
  	}
```



### Usage

* QQ登录

```
	Mercury.instance.initQQConfig("1233xxx")
	var qqLoginHelper = QQLoginHelper(context)
            qqLoginHelper.login(object : LoginCallback {
                override fun loginError() {
                }
                override fun loginCancel() {
                }
                override fun loginSuccess(userInfoMap: Map<String, String>) {
                    //todo login success  这边回调的是qq登录返回的参数
                }

            })
```

* 微信登录

```
//TODO 需要开发者取申请id
Mercury.instance.initWechatConfig("", "")
val wechatLoginHelper = WechatLoginHelper(context)
wechatLoginHelper.login(object : LoginCallback {
      override fun loginError() {
      }

      override fun loginCancel() {
      }

      override fun loginSuccess(userInfoMap: Map<String, String>) {
      }
})

```

* 微博登录

```
Mercury.instance.initWeiboConfig("")
val weiboLoginHelper = WeiboLoginHelper(context)
weiboLoginHelper!!.login(object : LoginCallback{
    override fun loginError() {
    }
    override fun loginCancel() {

    }
    override fun loginSuccess(map: Map<String, String>) {
    }

})

```

### Important

1. 三个平台需要注册对应package的app_id才能使用
2. 微信需要在app内申明 WXEntryActivity.kt 才能接收到回调
3. 微博平台如果需要采用SSO登录需要回调onActivityResult()

> 以上具体可以参考Demo实现






