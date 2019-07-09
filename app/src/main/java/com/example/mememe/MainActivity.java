package com.example.mememe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private MemeViewController mMemeViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);

        View rootView = findViewById(R.id.view_root);
        mMemeViewController = new MemeViewController(rootView);

        rootView.findViewById(R.id.meme_share_button).setOnClickListener(new View.OnClickListener() {
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
                        System.out.println("finished: " + outputFile.toString());
                    }
                });
    }
}
