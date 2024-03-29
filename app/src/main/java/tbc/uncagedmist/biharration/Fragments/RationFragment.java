package tbc.uncagedmist.biharration.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.InterstitialListener;
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
import tbc.uncagedmist.biharration.R;

public class RationFragment extends Fragment {

    View myFragment;

    Button btnFormA,btnFormB,btnCheckStatus,btnRationAllot,btnDealer,btnHome,btnAadhaar,btnDownload,btnSearch;

    Context context;

    FloatingActionButton fabBack;

    @Override
    public void onAttach(Activity activity) {
        context = activity;
        super.onAttach(activity);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        IronSource.init(
                (Activity) context,
                context.getString(R.string.IS_APP_KEY),
                IronSource.AD_UNIT.INTERSTITIAL
        );

        loadLocale();
        loadFullscreen();
        IronSource.loadInterstitial();

        // Inflate the layout for this fragment
        myFragment = inflater.inflate(R.layout.fragment_ration, container, false);

        btnFormA = myFragment.findViewById(R.id.btnFormA);
        btnFormB = myFragment.findViewById(R.id.btnFormB);

        btnCheckStatus = myFragment.findViewById(R.id.btnStatus);
        btnRationAllot = myFragment.findViewById(R.id.btnChkAllot);
        btnDealer = myFragment.findViewById(R.id.btnDealer);

        btnHome = myFragment.findViewById(R.id.btnHome);
        btnAadhaar = myFragment.findViewById(R.id.btnAadhharLink);
        btnDownload = myFragment.findViewById(R.id.btnDownload);
        btnSearch = myFragment.findViewById(R.id.btnSearch);
        fabBack = myFragment.findViewById(R.id.fabBack);

        onclickImplement();

        return myFragment;
    }

    private void onclickImplement() {
        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = ((AppCompatActivity)context)
                        .getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame,homeFragment).commit();
            }
        });

        btnFormA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IronSource.isInterstitialReady()) {
                    //show the interstitial
                    IronSource.showInterstitial("DefaultInterstitial");
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
                if (IronSource.isInterstitialReady()) {
                    //show the interstitial
                    IronSource.showInterstitial("DefaultInterstitial");
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
                if (IronSource.isInterstitialReady()) {
                    //show the interstitial
                    IronSource.showInterstitial("DefaultInterstitial");
                }
                else {
                    ResultFragment resultFragment = new ResultFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.CHECK_STATUS_URL;

                    transaction.replace(R.id.main_frame,resultFragment).commit();
                }
            }
        });

        btnRationAllot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IronSource.isInterstitialReady()) {
                    //show the interstitial
                    IronSource.showInterstitial("DefaultInterstitial");
                }
                else {
                    ResultFragment resultFragment = new ResultFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.CHECK_ALLOT_URL;

                    transaction.replace(R.id.main_frame,resultFragment).commit();
                }
            }
        });

        btnDealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IronSource.isInterstitialReady()) {
                    //show the interstitial
                    IronSource.showInterstitial("DefaultInterstitial");
                }
                else {
                    ResultFragment resultFragment = new ResultFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.DEALER_URL;

                    transaction.replace(R.id.main_frame,resultFragment).commit();
                }
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IronSource.isInterstitialReady()) {
                    //show the interstitial
                    IronSource.showInterstitial("DefaultInterstitial");
                }
                else {
                    ResultFragment resultFragment = new ResultFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.OFFICIAL_URL;

                    transaction.replace(R.id.main_frame,resultFragment).commit();
                }
            }
        });

        btnAadhaar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IronSource.isInterstitialReady()) {
                    //show the interstitial
                    IronSource.showInterstitial("DefaultInterstitial");
                }
                else {
                    ResultFragment resultFragment = new ResultFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.AADHAAR_URL;

                    transaction.replace(R.id.main_frame,resultFragment).commit();
                }
            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IronSource.isInterstitialReady()) {
                    //show the interstitial
                    IronSource.showInterstitial("DefaultInterstitial");
                }
                else {
                    ResultFragment resultFragment = new ResultFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.DOWNLOAD_URL;

                    transaction.replace(R.id.main_frame,resultFragment).commit();
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IronSource.isInterstitialReady()) {
                    //show the interstitial
                    IronSource.showInterstitial("DefaultInterstitial");
                }
                else {
                    ResultFragment resultFragment = new ResultFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.SEARCH_URL;

                    transaction.replace(R.id.main_frame,resultFragment).commit();
                }
            }
        });
    }

    private void askForPermission() {
        Dexter
                .withContext(context)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Toast.makeText(context, "Permission Granted...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(context, "Permission Denied!! You Can't Download Files.", Toast.LENGTH_SHORT).show();
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

        AssetManager assetManager = context.getAssets();
        InputStream in = null;
        OutputStream out = null;

        try {
            in = assetManager.open(fileName);
            File outFile = new File(dirPath, fileName);
            out = new FileOutputStream(outFile);
            copyFile(in, out);
            Toast.makeText(context, "File Saved!! Now Check in BiharService Folder.", Toast.LENGTH_SHORT).show();
        }
        catch (IOException e)   {
            e.printStackTrace();
            Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
        SharedPreferences prefs = context.getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("My_Lang","");
        setLocale(language);
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(
                config, getActivity().getBaseContext().getResources().getDisplayMetrics());

        //shared prefs
        SharedPreferences.Editor editor = context.getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();
    }

    private void loadFullscreen() {
        IronSource.setInterstitialListener(new InterstitialListener() {
            /**
             * Invoked when Interstitial Ad is ready to be shown after load function was called.
             */
            @Override
            public void onInterstitialAdReady() {
            }
            /**
             * invoked when there is no Interstitial Ad available after calling load function.
             */
            @Override
            public void onInterstitialAdLoadFailed(IronSourceError error) {
            }
            /**
             * Invoked when the Interstitial Ad Unit is opened
             */
            @Override
            public void onInterstitialAdOpened() {
            }
            /*
             * Invoked when the ad is closed and the user is about to return to the application.
             */
            @Override
            public void onInterstitialAdClosed() {
            }
            /**
             * Invoked when Interstitial ad failed to show.
             * @param error - An object which represents the reason of showInterstitial failure.
             */
            @Override
            public void onInterstitialAdShowFailed(IronSourceError error) {
            }
            /*
             * Invoked when the end user clicked on the interstitial ad, for supported networks only.
             */
            @Override
            public void onInterstitialAdClicked() {
            }
            /** Invoked right before the Interstitial screen is about to open.
             *  NOTE - This event is available only for some of the networks.
             *  You should NOT treat this event as an interstitial impression, but rather use InterstitialAdOpenedEvent
             */
            @Override
            public void onInterstitialAdShowSucceeded() {
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        IronSource.onResume((Activity) context);
    }

    @Override
    public void onPause() {
        super.onPause();
        IronSource.onPause((Activity) context);
    }
}