package com.will_code_for_food.crucentralcoast.view.resources;

import android.os.Build;
import android.os.Bundle;
import android.provider.Browser;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

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

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        webView = (WebView) fragmentView.findViewById(R.id.article_webview);
        article = ResourcesActivity.selectedResource;

        if (Build.VERSION.SDK_INT >= Android.HARDWARE_ACCELERATION_SUPPORTED_SDK_LEVEL) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        if (article != null) {
            Log.i("ViewArticleFragment", "attempting to load " + article.getUrl());
            webView.loadUrl(article.getUrl());
        } else {
            Log.e("ViewArticleFragment", "article was null");
        }

        return fragmentView;
    }
}
