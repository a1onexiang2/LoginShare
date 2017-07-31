# LoginShare

LoginShare is a fast and efficient open source sso manager and share manager for Android.

## Download

Download the [the lastest AAR](download/loginsharesdk-1.1.aar)

or with Gradle:

```groovy
repositories{
    flatDir{
        dirs 'libs'
    }
}

dependencies {
    compile(name:'loginsharesdk-1.1', ext:'aar')
    compile "com.qihoo360.replugin:replugin-host-lib:$kotlin_version"
}
```

## How to use

#### 1. Initilaze

Initilaze the LoginShare in your `Application.onCreate()` as codes following:
  
```java
	private void initSdk() {
		LoginShare.init(this)
		LoginShare.initQq("QQ_APP_KEY", "QQ_SECRET_KEY")
		LoginShare.initWechat("WECHAT_KEY", "WECHAT_SECRET", "WECHAT_SCOPE", "WECHAT_STATE")
		LoginShare.initWeibo("WEIBO_KEY", "WEIBO_SECRET", "WEIBO_CALLBACK_URL", "WEIBO_SCOPE")
	}
```

#### 2. SsoLogin

Only 3 platforms (QQ, Wechat, Sina Weibo) are supported in current version. 

First, just call like this:

```java
	LoginShare.login(Activity activity, Platforms platform, boolean needUserInfo)
```

or if `needUserInfo` is `false`, just call:

```java
	LoginShare.login(Activity activity, Platforms platform)
```

*Once `needUserInfo` flag is set to `true`, `UserInfoResult` will be sent instead of `AuthResult`.*

Secondary, override the `onAcitivtyResult(int requestCode, int resultCode, Intent data)` in `Activity` like this:

```java
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data)
        LoginShare.onActivityResult(requestCode, resultCode, data)
    }
```

And the last step, get the sso data by the `EventBus` event in Activity like this:

```java
	@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
    public <T> void onEvent(final AuthEvent<T> event) {
        if (event.isSuccess()) {
            T data = event.getData();
            if (data instanceof Void) {
                log.d(TAG, "some exception uncatched.");
                return;
            }
            if (data instanceof QqAuthResult) {
                //TODO handle the QqAuthResult
            } else if (data instanceof WechatAuthResult) {
                //TODO handle the WechatAuthResult
            } else if (data instanceof WeiboAuthResult) {
                //TODO handle the WeiboAuthResult
            } else if (data instanceof QqUserInfoResult) {
                //TODO handle the QqUserInfoResult
            } else if (data instanceof WechatUserInfoResult) {
                //TODO handle the WechatUserInfoResult
            } else if (data instanceof WeiboUserInfoResult) {
                //TODO handle the WeiboUserInfoResult
            } else if (data instanceof Throwable) {
                //TODO handle the Throwable
            }
            log.d(TAG, event.toString());
        } else if (event.isCancel()) {
            log.d(TAG, "something cancel");
        } else if (event.isFailure()) {
            log.d(TAG, "something failure");
        } else if (event.isError()) {
            log.d(TAG, "something error");
        }
    }
```

#### 3. Share

First, generate the `ShareContent` like this:

```java
	ShareContent shareContent = new ShareContent()
                .setTitle("TITLE")
                .setContent("CONTENT")
                .setUrl("URL")
                .setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setLocalImages("IMAGE_FILE_PATH")
                .setNetworkImages("IMAGE_URL");
```

*platforms have their own behaviors, some attributes can be null but `title` is required anyway, check the source code for it.*

Then generate the `SharePlatforms` you want to support like this:

```java
	SharePlatform[] shareArray = new SharePlatform[]{new SharePlatform(Platforms.QQ, "QQ"),
	            new SharePlatform(Platforms.QZONE, "QZONE"),
	            new SharePlatform(Platforms.WECHAT, "WECHAT"),
	            new SharePlatform(Platforms.MOMENTS, "MOMENTS"),
	            new SharePlatform(Platforms.WEIBO, "WEIBO"),
	            new SharePlatform(Platforms.SYSTEM, "SYSTEM")};
```

*platform icon can be set by calling another constructor `SharePlatform(Platforms platform, String name, @DrawableRes int resId)`.*

and generate the `ShareConfig` with the `ShareContent` and `SharePlatform` like this:

```java
	ShareConfig shareConfig = new ShareConfig()
                .setContent(shareContent)
                .setStyle(new BottomStyle())
                .setPlatforms(shareArray)
```

*`setStyle` receives an `BaseStyle` argument. `BottomStyle`, `CenterStyle`, `DropdownStyle`, `FullscreenStyle` is
supported in the library. If these styles don't match the requirement, just build CustomStyle extending `BaseStyle` is supported with a CustomShareDialog extending `BaseShareDialog` as an argument to `ShareConfig`. For example:*

```java
	public class CustomStyle extends BaseStyle {

    public CustomStyle(boolean corner) {
        super(corner);
    }

    @NotNull
    @Override
    public String TAG() {
        return "Custom";
    }

    @NotNull
    @Override
    public BaseShareDialog getDialog(@NotNull ShareConfig config) {
        return BaseShareDialog.wrap(config, CustomShareDialog.class);
    }
}


```

At last, just call like this:

```java
	LoginShare.share(Activity, shareConfig);
``` 


## ProGuard

If you are using ProGuard you might need to add the following options:

```
-keep class com.tencent.**{*;}
-dontwarn com.tencent.**
-keep class com.sina.**{*;}
-dontwarn com.sina.**
-keep class com.alipay.**{*;}
-dontwarn com.alipay.**
```