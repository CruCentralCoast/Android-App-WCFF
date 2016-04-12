package com.will_code_for_food.crucentralcoast.view.resources;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Browser;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;
import com.will_code_for_food.crucentralcoast.values.Android;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;

/**
 * Created by MasonJStevenson on 2/15/2016.
 */
public class ViewArticleFragment extends CruFragment {

    Resource article;
    WebView webView;
    private ProgressBar progressBar;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        webView = (WebView) fragmentView.findViewById(R.id.article_webview);
        progressBar = (ProgressBar) fragmentView.findViewById(R.id.article_progress);
        article = ResourcesActivity.selectedResource;

        if (Build.VERSION.SDK_INT >= Android.CHROMIUM_SUPPORTED_SDK_LEVEL) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        webView.setWebViewClient(new Callback());
        webView.setWebChromeClient(new CustomChromeClient());

        if (article != null) {
            Log.i("ViewArticleFragment", "attempting to load " + article.getUrl());
            webView.loadUrl(article.getUrl());
        } else {
            Log.e("ViewArticleFragment", "article was null");
        }

        return fragmentView;
    }

    private class Callback extends WebViewClient {

        /**
         * Returning false here makes it so that https pages don't load in a separate window.
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

    }

    private class CustomChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int progress) {
            super.onProgressChanged(view, progress);

            if(progress < 100 && progressBar.getVisibility() == ProgressBar.GONE){
                progressBar.setVisibility(ProgressBar.VISIBLE);
            }

            progressBar.setProgress(progress);

            if(progress == 100) {
                progressBar.setVisibility(ProgressBar.GONE);
            }

        }
    }
}
