package tbc.uncagedmist.biharration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

import tbc.uncagedmist.biharration.Common.Common;

public class RationActivity extends AppCompatActivity {

    AdView adView;

    Button btnFormA,btnFormB,btnCheckStatus,btnRationAllot,btnDealer,btnHome,btnAadhaar,btnDownload,btnSearch;

    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_ration);

        loadBanner();
        loadFull();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));

        btnFormA = findViewById(R.id.btnFormA);
        btnFormB = findViewById(R.id.btnFormB);

        btnCheckStatus = findViewById(R.id.btnStatus);
        btnRationAllot = findViewById(R.id.btnChkAllot);
        btnDealer = findViewById(R.id.btnDealer);

        btnHome = findViewById(R.id.btnHome);
        btnAadhaar = findViewById(R.id.btnAadhharLink);
        btnDownload = findViewById(R.id.btnDownload);
        btnSearch = findViewById(R.id.btnSearch);

        onclickImplement();

    }

    private void loadFull() {
        interstitialAd = new InterstitialAd(
                this, "" +
                getString(R.string.FB_FULL)
        );

        // Create listeners for the Interstitial Ad
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e("TAG", "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e("TAG", "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e("TAG", "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d("TAG", "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d("TAG", "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d("TAG", "Interstitial ad impression logged!");
            }
        };

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
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

    private void onclickImplement() {
        btnFormA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interstitialAd.isAdLoaded()) {
                    interstitialAd.show();
                }
                else {
                    askForPermission();
                    copyAsset("pra_k.pdf");
                }
            }
        });

        btnFormB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interstitialAd.isAdLoaded()) {
                    interstitialAd.show();
                }
                else {
                    askForPermission();
                    copyAsset("pra_kh.pdf");
                }
            }
        });

        btnCheckStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interstitialAd.isAdLoaded()) {
                    interstitialAd.show();
                }
                else {
                    Intent intent = new Intent(RationActivity.this,ResultActivity.class);
                    intent.putExtra("url", Common.CHECK_STATUS_URL);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnRationAllot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interstitialAd.isAdLoaded()) {
                    interstitialAd.show();
                }
                else {
                    Intent intent = new Intent(RationActivity.this,ResultActivity.class);
                    intent.putExtra("url", Common.CHECK_ALLOT_URL);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnDealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interstitialAd.isAdLoaded()) {
                    interstitialAd.show();
                }
                else {
                    Intent intent = new Intent(RationActivity.this,ResultActivity.class);
                    intent.putExtra("url", Common.DEALER_URL);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interstitialAd.isAdLoaded()) {
                    interstitialAd.show();
                }
                else {
                    Intent intent = new Intent(RationActivity.this,ResultActivity.class);
                    intent.putExtra("url", Common.OFFICIAL_URL);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnAadhaar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interstitialAd.isAdLoaded()) {
                    interstitialAd.show();
                }
                else {
                    Intent intent = new Intent(RationActivity.this,ResultActivity.class);
                    intent.putExtra("url", Common.AADHAAR_URL);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interstitialAd.isAdLoaded()) {
                    interstitialAd.show();
                }
                else {
                    Intent intent = new Intent(RationActivity.this,ResultActivity.class);
                    intent.putExtra("url", Common.DOWNLOAD_URL);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interstitialAd.isAdLoaded()) {
                    interstitialAd.show();
                }
                else {
                    Intent intent = new Intent(RationActivity.this,ResultActivity.class);
                    intent.putExtra("url", Common.SEARCH_URL);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void askForPermission() {
        Dexter
                .withContext(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Toast.makeText(RationActivity.this, "Permission Granted...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(RationActivity.this, "Permission Denied!! You Can't Download Files.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    private void copyAsset(String fileName) {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/BiharServices";
        File dir = new File(dirPath);

        if (!dir.exists())  {
            dir.mkdirs();
        }

        AssetManager assetManager = getAssets();
        InputStream in = null;
        OutputStream out = null;

        try {
            in = assetManager.open(fileName);
            File outFile = new File(dirPath, fileName);
            out = new FileOutputStream(outFile);
            copyFile(in, out);
            Toast.makeText(this, "File Saved!! Now Check in BiharService Folder.", Toast.LENGTH_SHORT).show();
        }
        catch (IOException e)   {
            e.printStackTrace();
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException e)   {
                    e.printStackTrace();
                }
            }

            if (out != null) {
                try {
                    out.close();
                }
                catch (IOException e)   {
                    e.printStackTrace();
                }
            }
        }

    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;

        while((read = in.read(buffer)) != -1)   {
            out.write(buffer, 0 ,read);
        }
    }

    private void loadLocale()   {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang","");
        setLocale(language);
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        //shared prefs
        SharedPreferences.Editor editor = getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }
}