package com.example.mememe;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

public class MainActivity extends AppCompatActivity {

    private static final String MEDIA_TYPE_JPEG = "image/jpeg";

    private MemeViewController mMemeViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);

        View rootView = findViewById(R.id.view_root);
        mMemeViewController = new MemeViewController(rootView);

        View shareButton = rootView.findViewById(R.id.meme_share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShareButtonClick();
            }
        });
    }

    private void onShareButtonClick() {
        final File outputFile = new File(getFilesDir(), "output.jpg");
        mMemeViewController.prepareForSnapshot();
        ViewSnapshotHelper.snapshotToFile(
                mMemeViewController.getContainerView(),
                outputFile,
                new Runnable() {
                    @Override
                    public void run() {
                        mMemeViewController.cleanupAfterSnapshot();
                        shareToInstagram(outputFile);
                    }
                });
    }

    private void shareToInstagram(File outputFile) {
        // Need to turn the file uri into a content uri
        Uri stickerAssetUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, outputFile);

        // Instantiate implicit intent with ADD_TO_STORY action,
        // sticker asset, background colors, and attribution link
        Intent intent = new Intent("com.instagram.share.ADD_TO_STORY");
        intent.setType(MEDIA_TYPE_JPEG);
        intent.putExtra("interactive_asset_uri", stickerAssetUri);
        intent.putExtra("top_background_color", "#33FF33");
        intent.putExtra("bottom_background_color", "#FF00FF");

        // Instantiate activity and verify it will resolve implicit intent
        Activity activity = this;
        activity.grantUriPermission(
                "com.instagram.android", stickerAssetUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (activity.getPackageManager().resolveActivity(intent, 0) != null) {
            activity.startActivityForResult(intent, 0);
        }
    }
}
