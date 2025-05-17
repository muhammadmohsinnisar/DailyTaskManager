# Project-specific ProGuard rules

# Preserve Firebase classes and prevent stripping
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }

# Keep annotations used by Firebase and JSON parsing
-keepattributes *Annotation*

# Keep model classes
-keep class com.mohsin.dailytaskmanager.models.** { *; }

# Preserve Android support annotations and related metadata
-keepclassmembers class * {
    @androidx.annotation.Keep *;
}

# Keep Parcelable implementations
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Retain line number info for debugging
-keepattributes SourceFile,LineNumberTable

# Keep native method names
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep all enums
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class j$.** { *; }
-dontwarn j$.**
