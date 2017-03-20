package com.zlw.mymediacontrol.me;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.RemoteControlClient;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.zlw.mymediacontrol.Logger;
import com.zlw.mymediacontrol.MediaPlaybackService;

import java.util.List;

/**
 * Created by zlw on 2017/3/4.
 */

public class RemoteManager {
    private static volatile RemoteManager ins = null;

    public static RemoteManager getInstance(Context mContext, Activity mActivity) {
        if (ins == null) {
            synchronized (RemoteManager.class) {
                if (ins == null) {
                    ins = new RemoteManager(mContext, mActivity);
                }
            }
        }
        return ins;
    }

    public static RemoteManager getInstance() {
        return ins;
    }

    private Context mContext;
    private Activity mActivity;
    ComponentName myEventReceiver;
    AudioManager myAudioManager;
    RemoteControlClient myRemoteControlClient;

    //Callback
    private MediaBrowserCompat mMediaBrowser;


    private RemoteManager(Context mContext, Activity mActivity) {
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    public void register_18() {
        Logger.d("zlw", "register_18...");
        ComponentName myEventReceiver = new ComponentName(mActivity.getPackageName(), android.support.v4.media.session.MediaButtonReceiver.class.getName());
        myAudioManager = (AudioManager) mActivity.getSystemService(Context.AUDIO_SERVICE);

        // 需要向AudioManager注册
        myAudioManager.registerMediaButtonEventReceiver(myEventReceiver);

        // build the PendingIntent for the remote control client
        Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        mediaButtonIntent.setComponent(myEventReceiver);
        PendingIntent mediaPendingIntent = PendingIntent.getBroadcast(mContext, 0, mediaButtonIntent, 0);

        // create and register_18 the remote control client
        myRemoteControlClient = new RemoteControlClient(mediaPendingIntent);
        // 在AudioManager中注册RemoteControlClient
        myAudioManager.registerRemoteControlClient(myRemoteControlClient);


        //设置所支持的系统操作，下面的例子中支持了上一首，下一首，播放，暂停，播放/暂停, 评分（收藏）
        int flags = RemoteControlClient.FLAG_KEY_MEDIA_PREVIOUS
                | RemoteControlClient.FLAG_KEY_MEDIA_NEXT
                | RemoteControlClient.FLAG_KEY_MEDIA_PLAY
                | RemoteControlClient.FLAG_KEY_MEDIA_PAUSE
                | RemoteControlClient.FLAG_KEY_MEDIA_PLAY_PAUSE
                | RemoteControlClient.FLAG_KEY_MEDIA_RATING;
        myRemoteControlClient.setTransportControlFlags(flags);


        // 注册listener监听系统发出的进度更新请求
        myRemoteControlClient.setOnGetPlaybackPositionListener(new RemoteControlClient.OnGetPlaybackPositionListener() {
            @Override
            public long onGetPlaybackPosition() {
                return 13L;
            }
        });
    }

    public void send(String name) {
        Logger.d("zlw", "send name：" + name);
        // 要向系统控件发送上述信息，需要创建一个MetadataEditor
        RemoteControlClient.MetadataEditor ed = myRemoteControlClient.editMetadata(true);
        // 创建时传入的布尔值参数，指示本次传入的数据是否附加在之前传入的数据一起传给系统，一般来说，换歌了才需要将其设置为true
        // 向系统传入歌曲信息
        ed.putString(MediaMetadataRetriever.METADATA_KEY_TITLE, name);
        ed.putString(MediaMetadataRetriever.METADATA_KEY_ALBUM, "专辑名");
        ed.putString(MediaMetadataRetriever.METADATA_KEY_ARTIST, "歌手名");
        ed.putLong(MediaMetadataRetriever.METADATA_KEY_DURATION, 1234L);
        ed.putLong(MediaMetadataRetriever.METADATA_KEY_CD_TRACK_NUMBER, 12L);
//        ed.putLong(MediaMetadataRetriever.METADATA_KEY_NUM_TRACKS, 12L);
//        ed.putBitmap(MetadataEditor.BITMAP_KEY_ARTWORK, 歌曲封面);
//        ed.putObject(MediaMetadataEditor.RATING_KEY_BY_USER, 评分（参考Rating类，目前只支持RATING_HEART类型）);
        ed.apply();
        myRemoteControlClient.setPlaybackState(RemoteControlClient.PLAYSTATE_BUFFERING, 1, 1);//状态为加载中
        myRemoteControlClient.setPlaybackState(RemoteControlClient.PLAYSTATE_PLAYING, 1, 1);//状态为播放
//        myRemoteControlClient.setPlaybackState(RemoteControlClient.PLAYSTATE_PAUSED);//状态为暂停
    }

    public void updataState(boolean isplay) {
        if (isplay) {
            Logger.d("zlw", "播放");
            myRemoteControlClient.setPlaybackState(RemoteControlClient.PLAYSTATE_PLAYING);//状态为播放
        } else {
            Logger.d("zlw", "暂停");
            myRemoteControlClient.setPlaybackState(RemoteControlClient.PLAYSTATE_PAUSED);//状态为暂停
        }
    }


    public void initCallBack() {
        // Create MediaBrowserServiceCompat
        mMediaBrowser = new MediaBrowserCompat(mActivity,
                new ComponentName(mActivity, MediaPlaybackService.class),
                mConnectionCallbacks,
                null); // optional Bundle
    }

    private final MediaBrowserCompat.ConnectionCallback mConnectionCallbacks =
            new MediaBrowserCompat.ConnectionCallback() {
                @Override
                public void onConnected() {
                    Logger.i("zlw", "onConnected");
                    // Get the token for the MediaSession
                    MediaSessionCompat.Token token = mMediaBrowser.getSessionToken();

                    // Create a MediaControllerCompat
                    try {
                        MediaControllerCompat mediaController = new MediaControllerCompat(mActivity, // Context
                                token);
                        // Save the controller
                        MediaControllerCompat.setMediaController(mActivity, mediaController);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    buildTransportControls();

                }

                @Override
                public void onConnectionSuspended() {
                    // The Service has crashed. Disable transport controls until it automatically reconnects
                    Logger.i("zlw", "onConnectionSuspended");
                }

                @Override
                public void onConnectionFailed() {
                    // The Service has refused our connection
                    Logger.i("zlw", "onConnectionFailed");
                }
            };


    void buildTransportControls() {

        // Since this is a play/pause button, you'll need to test the current state
        // and choose the action accordingly

        int pbState = MediaControllerCompat.getMediaController(mActivity).getPlaybackState().getState();
        if (pbState == PlaybackState.STATE_PLAYING) {
            MediaControllerCompat.getMediaController(mActivity).getTransportControls().pause();
        } else {
            MediaControllerCompat.getMediaController(mActivity).getTransportControls().play();
        }


        MediaControllerCompat mediaController = MediaControllerCompat.getMediaController(mActivity);

        // Display the initial state
//        MediaMetadataCompat metadata = mediaController.getMetadata();
//        PlaybackStateCompat pbState = mediaController.getPlaybackState();

//        Bundle bundle = new Bundle();
//        bundle.putString(METADATA_KEY_TITLE, "123");
//        mMediaSession.sendSessionEvent(METADATA_KEY_TITLE, bundle);
//        String title = metadata.getDescription().getTitle().toString();
//        Logger.d("zlw", "title:" + title);

        // Register a Callback to stay in sync
        mediaController.registerCallback(controllerCallback);
    }


    private MediaControllerCompat.Callback controllerCallback = new MediaControllerCompat.Callback() {
        @Override
        public void onSessionDestroyed() {
            super.onSessionDestroyed();
            Logger.i("zlw", "onSessionDestroyed");
        }

        @Override
        public void onSessionEvent(String event, Bundle extras) {
            super.onSessionEvent(event, extras);
            Logger.i("zlw", "onSessionEvent");
        }

        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            super.onPlaybackStateChanged(state);
            Logger.i("zlw", "onPlaybackStateChanged");
            Logger.i("zlw", "state:" + state.getState());
            String time = state.getExtras().getString("time");
            Logger.i("zlw", "time:" + time);
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            super.onMetadataChanged(metadata);
            Logger.i("zlw", "onMetadataChanged");
        }

        @Override
        public void onQueueChanged(List<MediaSessionCompat.QueueItem> queue) {
            super.onQueueChanged(queue);
            Logger.i("zlw", "onQueueChanged");
        }

        @Override
        public void onQueueTitleChanged(CharSequence title) {
            super.onQueueTitleChanged(title);
            Logger.i("zlw", "onQueueTitleChanged");
        }

        @Override
        public void onExtrasChanged(Bundle extras) {
            super.onExtrasChanged(extras);
            Logger.i("zlw", "onExtrasChanged");
        }

        @Override
        public void onAudioInfoChanged(MediaControllerCompat.PlaybackInfo info) {
            super.onAudioInfoChanged(info);
            Logger.i("zlw", "onAudioInfoChanged");
        }

        @Override
        public void binderDied() {
            super.binderDied();
            Logger.i("zlw", "binderDied");
        }


    };

}
