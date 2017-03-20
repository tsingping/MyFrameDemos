package com.zlw.mymediacontrol;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MediaPlaybackService extends MediaBrowserServiceCompat {

    // The action of the incoming Intent indicating that it contains a command
    // to be executed (see {@link #onStartCommand})
    public static final String ACTION_CMD = "com.example.android.uamp.ACTION_CMD";
    // The key in the extras of the incoming Intent indicating the command that
    // should be executed (see {@link #onStartCommand})
    public static final String CMD_NAME = "CMD_NAME";
    // A value of a CMD_NAME key in the extras of the incoming Intent that
    // indicates that the music playback should be paused (see {@link #onStartCommand})
    public static final String CMD_PAUSE = "CMD_PAUSE";
    // A value of a CMD_NAME key that indicates that the music playback should switch
    // to local playback from cast playback.
    public static final String CMD_STOP_CASTING = "CMD_STOP_CASTING";

    public static final String MY_MEDIA_ROOT_ID = "__ROOT__";
    public static final String MEDIA_ID_EMPTY_ROOT = "__EMPTY_ROOT__";
    public static final String MEDIA_ID_MUSICS_BY_GENRE = "__BY_GENRE__";
    public static final String MEDIA_ID_MUSICS_BY_SEARCH = "__BY_SEARCH__";


    private PackageValidator packageValidator;
    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;

    private String parentMediaId;

    @Override
    public void onCreate() {
        super.onCreate();
        packageValidator = new PackageValidator(this);

        // Create a MediaSessionCompat
        mMediaSession = new MediaSessionCompat(this, "mediaSession");
        // Enable callbacks from MediaButtons and TransportControls
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mMediaSession.setPlaybackState(mStateBuilder.build());
        // MySessionCallback() has methods that handle callbacks from a media controller
        mMediaSession.setCallback(new MySessionCallback());


        // For Android 5.0 (API version 21) or greater
        // To enable restarting an inactive session in the background,
        // You must create a pending intent and setMediaButtonReceiver.
        Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        mediaButtonIntent.setClass(getApplicationContext(), MediaPlaybackService.class);
        PendingIntent mbrIntent = PendingIntent.getService(getApplicationContext(), 0, mediaButtonIntent, 0);
        mMediaSession.setMediaButtonReceiver(mbrIntent);
        // Set the session's token so that client activities can communicate with it.
        setSessionToken(mMediaSession.getSessionToken());


    }

    @Override
    public int onStartCommand(Intent startIntent, int flags, int startId) {
        if (startIntent != null) {
            String action = startIntent.getAction();
            String command = startIntent.getStringExtra(CMD_NAME);
            if (ACTION_CMD.equals(action)) {
                if (CMD_PAUSE.equals(command)) {
                    // TODO: 2016/12/25 pause
                }
            } else {
                // Try to handle the intent as a media button event wrapped by MediaButtonReceiver


                MediaButtonReceiver.handleIntent(mMediaSession, startIntent);
            }
        }
        // Reset the delay handler to enqueue a message to stop the service if
        // nothing is playing.
        return START_STICKY;
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        Logger.i("zlw", "onGetRoot");
        // (Optional) Control the level of access for the specified package name.
        // You'll need to write your own Loggeric to do this.
        if (packageValidator.isCallerAllowed(this, clientPackageName, clientUid)) {
            // Returns a root ID, so clients can use onLoadChildren() to retrieve the content hierarchy
            return new BrowserRoot(MY_MEDIA_ROOT_ID, null);
        } else {
            // Returns an empty root, so clients can connect, but no content browsing possible
            return new BrowserRoot(null, null);
        }
    }


    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {

        //  Browsing not allowed
        if (parentMediaId == null) {
            result.sendResult(null);
            return;
        }

        // Assume for example that the music cataLogger is already loaded/cached.
        List<MediaBrowserCompat.MediaItem> mediaItems = new ArrayList<>();

        // Check if this is the root menu:
        if (MY_MEDIA_ROOT_ID.equals(parentMediaId)) {

            // build the MediaItem objects for the top level,
            // and put them in the mediaItems list
        } else {

            // examine the passed parentMediaId to see which submenu we're at,
            // and put the children of that menu in the mediaItems list
        }
        result.sendResult(mediaItems);
    }

    private class MySessionCallback extends MediaSessionCompat.Callback {

        public MySessionCallback() {
            super();
            Logger.i("zlw", "MySessionCallback");
        }

        @Override
        public void onCommand(String command, Bundle extras, ResultReceiver cb) {
            super.onCommand(command, extras, cb);
            Logger.i("zlw", "onCommand");
            Logger.i("zlw", "command:" + command);
            Logger.i("zlw", "cb:" + command);
            Logger.i("zlw", "extras:" + extras.describeContents());
            Logger.i("zlw", "====================================");

        }

        @Override
        public boolean onMediaButtonEvent(Intent mediaButtonEvent) {
            Logger.i("zlw", "onMediaButtonEvent");
            return super.onMediaButtonEvent(mediaButtonEvent);
        }

        @Override
        public void onPrepare() {
            super.onPrepare();
            Logger.i("zlw", "onPrepare");

        }

        @Override
        public void onPrepareFromMediaId(String mediaId, Bundle extras) {
            super.onPrepareFromMediaId(mediaId, extras);
            Logger.i("zlw", "onPrepareFromMediaId");
        }

        @Override
        public void onPrepareFromSearch(String query, Bundle extras) {
            super.onPrepareFromSearch(query, extras);
            Logger.i("zlw", "onPrepareFromSearch");
        }

        @Override
        public void onPrepareFromUri(Uri uri, Bundle extras) {
            super.onPrepareFromUri(uri, extras);
            Logger.i("zlw", "onPrepareFromUri");
        }

        @Override
        public void onPlay() {
            super.onPlay();
            Logger.i("zlw", "onPlay");
            Toast.makeText(getApplicationContext(), "播放", Toast.LENGTH_SHORT).show();

            //noinspection ResourceType
            PlaybackStateCompat.Builder stateBuilder = new PlaybackStateCompat.Builder()
                    .setActions(getAvailableActions());
            stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, 0, 1.0f);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Bundle extra = new Bundle();
            extra.putString("time", "" + sdf.format(new Date()));
            stateBuilder.setExtras(extra);
            mMediaSession.setPlaybackState(stateBuilder.build());
        }

        @Override
        public void onPlayFromMediaId(String mediaId, Bundle extras) {
            super.onPlayFromMediaId(mediaId, extras);
            Logger.i("zlw", "onPlayFromMediaId");
        }

        @Override
        public void onPlayFromSearch(String query, Bundle extras) {
            super.onPlayFromSearch(query, extras);
            Logger.i("zlw", "onPlayFromSearch");
        }

        @Override
        public void onPlayFromUri(Uri uri, Bundle extras) {
            super.onPlayFromUri(uri, extras);
            Logger.i("zlw", "onPlayFromUri");
        }

        @Override
        public void onSkipToQueueItem(long id) {
            super.onSkipToQueueItem(id);
            Logger.i("zlw", "onSkipToQueueItem");
        }

        @Override
        public void onPause() {
            super.onPause();
            Logger.i("zlw", "onPause");
            Toast.makeText(getApplicationContext(), "暂停", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onSkipToNext() {
            super.onSkipToNext();
            Logger.i("zlw", "onSkipToNext");
            Toast.makeText(getApplicationContext(), "下一首", Toast.LENGTH_SHORT).show();

            //TODO:nextplay
        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
            Logger.i("zlw", "onSkipToPrevious");
            Toast.makeText(getApplicationContext(), "上一首", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onFastForward() {
            super.onFastForward();
            Logger.i("zlw", "onFastForward");
        }

        @Override
        public void onRewind() {
            super.onRewind();
            Logger.i("zlw", "onRewind");
        }

        @Override
        public void onStop() {
            super.onStop();
            Logger.i("zlw", "onStop");
        }

        @Override
        public void onSeekTo(long pos) {
            super.onSeekTo(pos);
            Logger.i("zlw", "onSeekTo:" + pos);
        }


        @Override
        public void onSetRating(RatingCompat rating) {
            super.onSetRating(rating);
            Logger.i("zlw", "onSetRating");
        }

        @Override
        public void onCustomAction(String action, Bundle extras) {
            super.onCustomAction(action, extras);
            Logger.i("zlw", "onCustomAction");
        }

    }

    private long getAvailableActions() {
        long actions =
                PlaybackStateCompat.ACTION_PAUSE |
                        PlaybackStateCompat.ACTION_PLAY |
                        //----
                        PlaybackStateCompat.ACTION_PLAY_PAUSE |
                        PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID |
                        PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH |
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT;
//        if (mPlayback.isPlaying()) {
//            actions |= PlaybackStateCompat.ACTION_PAUSE;
//        } else {
//            actions |= PlaybackStateCompat.ACTION_PLAY;
//        }
        return actions;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
