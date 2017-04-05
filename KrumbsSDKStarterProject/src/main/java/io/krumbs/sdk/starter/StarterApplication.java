/*
 * Copyright (c) 2016 Krumbs Inc.
 * All rights reserved.
 *
 */
package io.krumbs.sdk.starter;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import java.net.URL;

import io.krumbs.sdk.KrumbsSDK;
import io.krumbs.sdk.KrumbsUser;
import io.krumbs.sdk.data.model.Media;
import io.krumbs.sdk.krumbscapture.KMediaUploadListener;
import io.krumbs.sdk.starter.Activitys.DishName;
import io.krumbs.sdk.starter.Activitys.MainActivity;
import io.krumbs.sdk.starter.Activitys.Staticimage;
import io.krumbs.sdk.starter.Models.User;
import io.krumbs.sdk.starter.Preferences.LoginPreferences;


public class StarterApplication extends Application {
    public static final String KRUMBS_SDK_APPLICATION_ID = "io.krumbs.sdk.APPLICATION_ID";
    public static final String KRUMBS_SDK_CLIENT_KEY = "io.krumbs.sdk.CLIENT_KEY";
    private LoginPreferences loginPreferences;
    User user;
    TabLayout tabLayout;
    ViewPager viewPager;
    Context context = null;
    @Override
    public void onCreate() {
        super.onCreate();
        loginPreferences = new LoginPreferences(StarterApplication.this);
        user = loginPreferences.getUser();

        String appID = getMetadata(getApplicationContext(), KRUMBS_SDK_APPLICATION_ID);
        String clientKey = getMetadata(getApplicationContext(), KRUMBS_SDK_CLIENT_KEY);
        if (appID != null && clientKey != null) {
            // SDK usage step 1 - initialize the SDK with your application id and client key
            // Make sure the application id and client key are correctly initialized in the Manifest
            KrumbsSDK.initialize(getApplicationContext(), appID, clientKey);

// Implement the interface KMediaUploadListener.
// After a Capture completes, the media (photo and audio) is uploaded to the cloud
// KMediaUploadListener will be used to listen for various state of media upload from the SDK.

            KMediaUploadListener kMediaUploadListener = new KMediaUploadListener() {
                // onMediaUpload listens to various status of media upload to the cloud.
                @Override
                public void onMediaUpload(String id, KMediaUploadListener.MediaUploadStatus mediaUploadStatus,
                                          Media.MediaType mediaType, URL mediaUrl) {

                    if (mediaUploadStatus != null) {
                        Intent intent1 = new Intent(StarterApplication.this, Staticimage.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                        Log.i("KRUMBS Status", mediaUploadStatus.toString());
                        if (mediaUploadStatus == KMediaUploadListener.MediaUploadStatus.UPLOAD_SUCCESS) {
                            Intent intent = new Intent(StarterApplication.this, DishName.class);
                            intent.putExtra("imageUrl", mediaUrl.toString());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            if (mediaType != null && mediaUrl != null) {
                                Log.i("KRUMBS Media, Type: ", mediaType + ": ID:" + id + ", URL:" + mediaUrl);
                                Toast.makeText(StarterApplication.this, mediaUploadStatus.toString(), Toast.LENGTH_LONG).show();

                            }
                        }
                    }
                }
            };
            // pass the KMediaUploadListener object to the sdk
            KrumbsSDK.setKMediaUploadListener(this, kMediaUploadListener);


            KrumbsSDK.registerUser(new KrumbsUser.KrumbsUserBuilder()
                    .email(user.getEmail())
                    .firstName(user.getUsername())
                    .lastName(" public ").build());



        }
    }

    public String getMetadata(Context context, String name) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                return appInfo.metaData.getString(name);
            }
        } catch (PackageManager.NameNotFoundException e) {
// if we can’t find it in the manifest, just return null
        }
        return null;
    }
}
