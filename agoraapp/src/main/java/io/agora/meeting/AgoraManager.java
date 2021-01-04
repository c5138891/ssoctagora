package io.agora.meeting;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import io.agora.base.PreferenceManager;
import io.agora.base.ToastManager;
import io.agora.base.network.RetrofitManager;
import io.agora.log.LogManager;
import io.agora.rtc.Constants;
import io.agora.rtc.video.VideoEncoderConfiguration;
import io.agora.sdk.manager.RtcManager;
import io.agora.sdk.manager.RtmManager;
import okhttp3.logging.HttpLoggingInterceptor;

public class AgoraManager {
    public static Context instance;

    public static String appId;
    public static String displayName;
    public static String memberId;
    public static String meetingNo;
    public static String password;
    public static String hostName;

    public static void Init(Context context) {
        instance = context;

        LogManager.init(context, "AgoraMeeting");
        PreferenceManager.init(context);
        ToastManager.init(context);
        RetrofitManager.instance().setLogger(new HttpLoggingInterceptor.Logger() {
            private final LogManager log = new LogManager(RetrofitManager.class.getSimpleName());

            @Override
            public void log(@NotNull String s) {
                log.d(s);
            }
        });
        appId = context.getString(R.string.agora_app_id);
        RtcManager.instance().init(context, getAppId(), (RtcManager sdk) -> {
            if (sdk == null) return;
            sdk.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING);
            sdk.setClientRole(Constants.CLIENT_ROLE_BROADCASTER);
            sdk.setParameters("che.audio.specify.codec", "OPUSFB");
            sdk.setAudioProfile(Constants.AUDIO_PROFILE_DEFAULT, Constants.AUDIO_SCENARIO_CHATROOM_ENTERTAINMENT);
            sdk.setVideoEncoderConfiguration(new VideoEncoderConfiguration(
                    VideoEncoderConfiguration.VD_360x360,
                    VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,
                    VideoEncoderConfiguration.STANDARD_BITRATE,
                    VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT
            ));
        });
        RtmManager.instance().init(context, getAppId(), null);
        String plainCredentials = "84a4f38355944bdb8f35726391214af3:165b4e4cfdea47cd8b24c0da4661cc3d";
        String base64Credentials = new String(Base64.encode(plainCredentials.getBytes(), Base64.NO_WRAP));
        RetrofitManager.instance().addHeader("Authorization", "Basic " + base64Credentials);
    }

    @Nullable
    public static String getAppId() {
        return appId;
    }

    public static String getDisplayName() {
        return displayName;
    }

    public static String getMeetingNo() {
        return meetingNo;
    }

    public static String getMemberId() {
        return memberId;
    }

    public static void setMemberId(String mmemberId) {
        memberId = mmemberId;
    }

    public static String getHostName() {
        return hostName;
    }

    public static void setHostName(String mhostName) {
        hostName = mhostName;
    }

    public static String getPassword() {
        return password;
    }

    public static void JoinMeeting(Context context, String mHostName, String mdisplayName, String mmemberId, String mmeetingNo, String mpassword) {
        displayName = "用户_" + mdisplayName;
        memberId = mmemberId;
        hostName = mHostName;
        meetingNo = mmeetingNo;
        password = mpassword;
        Intent mainIntent = new Intent(context, MainActivity.class);
        context.startActivity(mainIntent);

    }

}
