# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:/android-sdk/tools/proguard/proguard-android.txt
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

-dontobfuscate
-keepattributes *Annotation*,Signature

# retrofit
-dontwarn rx.**
-dontwarn com.squareup.okhttp.**
-keep class com.google.gson.** { *; }
-keep class com.google.inject.** { *; }
-keep class org.apache.http.** { *; }
-keep class org.apache.james.mime4j.** { *; }
-keep class javax.inject.** { *; }
-keep class retrofit.** { *; }
-dontwarn retrofit.appengine.* # dont use appengine

# otto
-keep class ** { @com.squareup.otto.Subscribe public *; }
-keepclasseswithmembers class * { @com.squareup.otto.Subscribe public *; }
-keep class ** { @com.squareup.otto.Produce public *; }
-keepclasseswithmembers class * { @com.squareup.otto.Produce public *; }

# butterknife
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }
-keepnames class * { @butterknife.InjectView *; }

# fix for appcompat issue #78377
-repackageclasses "android.support.v7"
-keep class android.support.v7.widget.** { *; }
-keep interface android.support.v7.widget.** { *; }