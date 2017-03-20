# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/vinci_mac/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*


-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.preference.Preference
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.annotation.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.design.**



-dontwarn android.support.v4.**
-dontwarn android.support.v7.**
-keep class android.support.v4.** { *; }



#==============bugly============================
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-keep public class im.vinci.headphoneapp.R$*{
public static final int *;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#==============jpush============================
-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

#==================gson==========================
-dontwarn com.google.**
-keep class com.google.gson.** {*;}


#==================protobuf==================
-dontwarn com.google.**
-keep class com.google.protobuf.** {*;}

#==================glide==================
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.integration.okhttp3.OkHttpGlideModule

#==================uemng===================
-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**
-keep public class javax.**
-keep public class java.**
-keep public class android.webkit.**
-dontwarn android.support.v4.**
-keep enum com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**

-keep public class com.umeng.socialize.* {*;}


-keep class com.facebook.**
-keep class com.facebook.** { *; }
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**
-keep class com.umeng.socialize.handler.**
-keep class com.umeng.socialize.handler.*
-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}

-keep class im.yixin.sdk.api.YXMessage {*;}
-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}

-dontwarn twitter4j.**
-keep class twitter4j.** { *; }

-keep class com.tencent.** {*;}
-dontwarn com.tencent.**
-keep public class com.umeng.soexample.R$*{
     public static final int *;
 }
-keep public class com.umeng.soexample.R$*{
     public static final int *;
}
-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}

-keep class com.sina.** {*;}
-dontwarn com.sina.**
-keep class  com.alipay.share.sdk.** {
    *;
}
-keepnames class * implements android.os.Parcelable {
     public static final ** CREATOR;
}

-keep class com.linkedin.** { *; }
-keepattributes Signature
-keep class com.umeng.**
-keep class activeandroid.**
-keep interface activeandroid.**
-keep enum activeandroid.**
#==========rxjava==========================================
-dontwarn sun.misc.**
-keep class rx.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
#==========okhttp3==========================================
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**

#==========butterknife==========================================
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#==========retrofit2==========================================
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# ========== EventBus ============
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

# ========== fastjson ============
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** {*;}

#==========DA==========================================
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static final java.lang.String TABLENAME;
}
-keep class **$Properties
-keep class org.greenrobot.**
-keep public class * extends org.greenrobot.greendao.AbstractDaoMaster

#==========sqlcipher==========================================
-keep public class net.sqlcipher.** {
*;
}

-keep public class net.sqlcipher.database.** {
*;
}
#================LeakCanary====================================
-keep class org.eclipse.mat.** { *; }
-keep class com.squareup.leakcanary.** { *; }

#      OkHttp相关

-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp3.** { *; }
-keep interface com.squareup.okhttp3.** { *; }
-dontwarn com.squareup.okhttp3.**

-keepattributes Signature
-keepattributes Annotation
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**

#      Okio相关

-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**

#     UIL相关

-keep class com.nostra13.universalimageloader.** { *; }
-keepclassmembers class com.nostra13.universalimageloader.** {*;}
-dontwarn com.nostra13.universalimageloader.**

#      Glide相关

-keep class com.bumptech.glide.Glide { *; }
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-dontwarn com.bumptech.glide.**

#     Picasso相关

-keep class com.squareup.picasso.Picasso { *; }
-dontwarn com.squareup.okhttp.**
-dontwarn com.squareup.picasso.**

#     xUtils3相关

-keepattributes Signature,*Annotation*
-keep public class org.xutils.** {
    public protected *;
}
-keep public interface org.xutils.** {
    public protected *;
}
-keepclassmembers class * extends org.xutils.** {
    public protected *;
}
-keepclassmembers @org.xutils.db.annotation.* class * {*;}
-keepclassmembers @org.xutils.http.annotation.* class * {*;}
-keepclassmembers class * {
    @org.xutils.view.annotation.Event <methods>;
}
-dontwarn org.xutils.**

# apache
-dontwarn org.apache.commons.**
-keep class org.apache.http.** { *; }
-dontwarn org.apache.http.**

# webkit error
-keep public class android.net.http.SslError
-keep public class android.webkit.WebViewClient

-dontwarn android.webkit.WebView
-dontwarn android.net.http.SslError
-dontwarn android.webkit.WebViewClient

# ut
-dontwarn com.ut.mini.**

#databinding
-dontwarn android.databinding.**
-keep class android.databinding.** { *; }

#Bean
-keep class im.vinci.headphoneapp.data.db.** { *; }
-keep class im.vinci.headphoneapp.data.net.common.** { *; }
-keep class im.vinci.headphoneapp.data.net.download.ProgressDownBean
-keep class im.vinci.headphoneapp.data.net.download.ProgressResponseBody
-keep class im.vinci.headphoneapp.data.net.syncdb.** { *; }
-keep class im.vinci.headphoneapp.user.pair.** { *; }


-keep class im.vinci.headphoneapp.data.net.token.** { *; }
-keep class im.vinci.headphoneapp.data.net.user.** { *; }
-keep class im.vinci.headphoneapp.data.net.user.bean.** { *; }
-keep class im.vinci.headphoneapp.data.net.vcircle.** { *; }
-keep class im.vinci.headphoneapp.data.net.Repository
-keep class im.vinci.headphoneapp.data.filesync.** { *; }
-keep class im.vinci.headphoneapp.data.sync.bean.** { *; }
-keep class im.vinci.headphoneapp.discovery.bean.** { *; }
-keep class im.vinci.headphoneapp.download.dbcontrol.** { *; }
-keep class im.vinci.headphoneapp.download.DownLoader
-keep class im.vinci.headphoneapp.download.DownLoadFileInfo
-keep class im.vinci.headphoneapp.download.TaskInfo
-keep class im.vinci.headphoneapp.music.MusicPlayInfo
-keep class im.vinci.headphoneapp.music.player.PlayInfo
-keep class im.vinci.headphoneapp.music.MusicInfosParcel
-keep class im.vinci.headphoneapp.musicplay.** { *; }
-keep class im.vinci.headphoneapp.my.bean.** { *; }
-keep class im.vinci.headphoneapp.user.pair.HeadSetInfo
-keep interface im.vinci.headphoneapp.data.** { *; }
-keep class im.vinci.vincisecret.** { *; }

#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

#zbar
-keep class me.dm7.barcodescanner.** { *; }
-keep class net.sourceforge.zbar.** { *; }
-keep interface me.dm7.barcodescanner.** { *; }
#SwitchButton
-keep class im.vinci.headphoneapp.widget.SwitchButton{
    void set*(***);
    *** get*();
}
-keep class im.vinci.headphoneapp.widget.SwitchButton { *; }

#meiqia
-keepattributes InnerClasses
-keep class **.R$* {
    <fields>;
}

#json
-keep class im.vinci.headphoneapp.data.net.SpecialStatus$* { *; }

#growingio
-keep class com.growingio.android.sdk.** {
 *;
}
-dontwarn com.growingio.android.sdk.**
-keepnames class * extends android.view.View

-keep class * extends android.app.Fragment {
 public void setUserVisibleHint(boolean);
 public void onHiddenChanged(boolean);
 public void onResume();
 public void onPause();
}
-keep class android.support.v4.app.Fragment {
 public void setUserVisibleHint(boolean);
 public void onHiddenChanged(boolean);
 public void onResume();
 public void onPause();
}
-keep class * extends android.support.v4.app.Fragment {
 public void setUserVisibleHint(boolean);
 public void onHiddenChanged(boolean);
 public void onResume();
 public void onPause();
}
#webview
-keepclassmembers class * extends android.webkit.WebChromeClient{
       public void openFileChooser(...);
       public void onShowFileChooser(...);
}
#高德定位
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}
-keep interface com.amap.api.location.AMapLocationListener
-dontwarn com.amap.api.location.**
-dontwarn com.amap.api.fence.**
-dontwarn com.amap.api.**
-dontwarn com.loc.**
-dontwarn com.autonavi.aps.amapapi.model.**