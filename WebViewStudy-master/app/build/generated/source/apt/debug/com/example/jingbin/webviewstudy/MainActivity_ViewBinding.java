// Generated code from Butter Knife. Do not modify!
package com.example.jingbin.webviewstudy;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding<T extends MainActivity> implements Unbinder {
  protected T target;

  @UiThread
  public MainActivity_ViewBinding(T target, View source) {
    this.target = target;

    target.btBaidu = Utils.findRequiredViewAsType(source, R.id.bt_baidu, "field 'btBaidu'", Button.class);
    target.btCall = Utils.findRequiredViewAsType(source, R.id.bt_call, "field 'btCall'", Button.class);
    target.btUploadPhoto = Utils.findRequiredViewAsType(source, R.id.bt_upload_photo, "field 'btUploadPhoto'", Button.class);
    target.btMovie = Utils.findRequiredViewAsType(source, R.id.bt_movie, "field 'btMovie'", Button.class);
    target.activityMain = Utils.findRequiredViewAsType(source, R.id.activity_main, "field 'activityMain'", LinearLayout.class);
    target.btMovieFull = Utils.findRequiredViewAsType(source, R.id.bt_movie_full, "field 'btMovieFull'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.btBaidu = null;
    target.btCall = null;
    target.btUploadPhoto = null;
    target.btMovie = null;
    target.activityMain = null;
    target.btMovieFull = null;

    this.target = null;
  }
}
