package com.zlw.mymediacontrol.me;

import android.content.ComponentName;
import android.media.session.MediaSession;

/**
 * Created by zlw on 2017/3/4.
 */

public class MediaSessionManager {

    private MediaSession mSession;

    private ComponentName mMediaButtonReceiverComponent;

    private void setUpMediaSession() {
        mSession = new MediaSession(this, "remusic");
        mSession.setCallback(new MediaSession.Callback() {
            @Override
            public void onPause() {
                pause();
                mPausedByTransientLossOfFocus = false;
            }

            @Override
            public void onPlay() {
                play();
            }

            @Override
            public void onSeekTo(long pos) {
                seek(pos);
            }

            @Override
            public void onSkipToNext() {
                gotoNext(true);
            }

            @Override
            public void onSkipToPrevious() {
                prev(false);
            }

            @Override
            public void onStop() {
                pause();
                mPausedByTransientLossOfFocus = false;
                seek(0);
                releaseServiceUiAndStop();
            }
        });
        mSession.setFlags(MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS);
    }
}
