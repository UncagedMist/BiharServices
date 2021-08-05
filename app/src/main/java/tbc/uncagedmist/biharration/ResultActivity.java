package tbc.uncagedmist.biharration;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.monstertechno.adblocker.AdBlockerWebView;
import com.monstertechno.adblocker.util.AdBlocker;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;

public class ResultActivity extends AppCompatActivity {

    AdView adView;
    ProgressBar progressBar;

    String url;

    WebView webView;

    FloatingActionButton resultShare,resultBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        loadBanner();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));

        Intent intent = getIntent();

        url = intent.getStringExtra("url");

        progressBar = findViewById(R.id.progressBar);
        resultShare = findViewById(R.id.resultShare);
        resultBack = findViewById(R.id.resultBack);

        webView = findViewById(R.id.webView);

        new AdBlockerWebView.init(this).initializeWebView(webView);

        webView.setWebViewClient(new MyWebViewClient());

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webView.loadUrl(url);

        resultShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String message = "Never Miss A Thing About Ration Card. Install Bihar Ration Card App and Stay Updated! \n https://play.google.com/store/apps/details?id=tbc.uncagedmist.biharration";
                intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(intent, "Share Bihar Ration Card App Using"));
            }
        });

        resultBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResultActivity.this,MainActivity.class));
                finish();
            }
        });

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String useragent, String contentdisposition, String mimetype, long contentlength) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    {
                        downloadDialog(url,useragent,contentdisposition,mimetype);
                    }
                    else
                    {
                        ActivityCompat.requestPermissions(ResultActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                    }
                }
                else
                {
                    downloadDialog(url,useragent,contentdisposition,mimetype);
                }
            }
        });
    }

    public void downloadDialog(final String url, final String UserAgent, String contentDisposition, String mimeType) {
        final String filename = URLUtil.guessFileName(url,contentDisposition,mimeType);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Downloading...")
                .setMessage("Do you want to Download "+ ' '+" "+filename+" "+' ')
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                        String cookie = CookieManager.getInstance().getCookie(url);
                        request.addRequestHeader("Cookie",cookie);
                        request.addRequestHeader("User-Agent",UserAgent);
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        DownloadManager manager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,filename);
                        manager.enqueue(request);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
    }

    private void loadBanner() {
        adView = new AdView(
                this,
                getString(R.string.FB_BANNER),
                AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        AdListener adListener = new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Ad loaded callback
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        };

        // Request an ad
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());
    }

    private class MyWebViewClient extends WebViewClient {

        MyWebViewClient()   {}

        @SuppressWarnings("deprecation")
        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            return AdBlockerWebView.blockAds(view,url) ? AdBlocker.createEmptyResource() :
                    super.shouldInterceptRequest(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {

            String message = "SSL Certificate Error";
            switch (error.getPrimaryError()) {
                case SslError.SSL_UNTRUSTED:
                    message = "The certificate authority is not trusted.";
                    break;
                case SslError.SSL_EXPIRED:
                    message = "The certificate has expired.";
                    break;
                case SslError.SSL_IDMISMATCH:
                    message = "The certificate Hostname mismatch.";
                    break;
                case SslError.SSL_NOTYETVALID:
                    message = "The certificate is not yet valid.";
                    break;
            }

            message += "\n\nDo you want to continue anyway?";

            new FancyAlertDialog.Builder(ResultActivity.this)
                    .setTitle("SSL Certificate Error")
                    .setBackgroundColor(Color.parseColor("#303F9F"))  //Don't pass R.color.colorvalue
                    .setMessage(message)
                    .setNegativeBtnText("Cancel")
                    .setPositiveBtnBackground(Color.parseColor("#FF4081"))  //Don't pass R.color.colorvalue
                    .setPositiveBtnText("Continue")
                    .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  //Don't pass R.color.colorvalue
                    .setAnimation(Animation.POP)
                    .isCancellable(true)
                    .setIcon(R.drawable.ic_star_border_black_24dp, Icon.Visible)
                    .OnPositiveClicked(new FancyAlertDialogListener() {
                        @Override
                        public void OnClick() {
                            handler.proceed();
                        }
                    })
                    .OnNegativeClicked(new FancyAlertDialogListener() {
                        @Override
                        public void OnClick() {
                            handler.cancel();
                        }
                    })
                    .build();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        }
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}