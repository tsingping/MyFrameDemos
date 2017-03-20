// Generated code from Butter Knife. Do not modify!
package com.example.jingbin.webviewstudy;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WebViewActivity_ViewBinding<T extends WebViewActivity> implements Unbinder {
  protected T target;

  @UiThread
  public WebViewActivity_ViewBinding(T target, View source) {
    this.target = target;

    target.mProgressBar = Utils.findRequiredViewAsType(source, R.id.pb_progress, "field 'mProgressBar'", ProgressBar.class);
    target.webView = Utils.findRequiredViewAsType(source, R.id.webview_detail, "field 'webView'", WebView.class);
    target.videoFullView = Utils.findRequiredViewAsType(source, R.id.video_fullView, "field 'videoFullView'", FrameLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mProgressBar = null;
    target.webView = null;
    target.videoFullView = null;

    this.target = null;
  }
}
