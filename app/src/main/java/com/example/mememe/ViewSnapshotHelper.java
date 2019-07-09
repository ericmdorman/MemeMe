package com.example.mememe;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.view.View;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ViewSnapshotHelper {

    public static void snapshotToFile(
            final View viewToSnapshot,
            final File destinationFile,
            final Runnable completionCallback) {
        // First, create a bitmap the exact size of the view
        final Bitmap bitmap = Bitmap.createBitmap(
                viewToSnapshot.getMeasuredWidth(),
                viewToSnapshot.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);

        // Now create a canvas backed by that bitmap.
        Canvas canvas = new Canvas(bitmap);

        // Now draw the view to the canvas (which will draw it to the bitmap, since the canvas is
        // backed by the bitmap).
        viewToSnapshot.draw(canvas);

        // Now write the bitmap to a file, but do it on a background thread to avoid blocking the
        // UI thread.
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    bitmap.compress(
                            Bitmap.CompressFormat.JPEG,
                            95,
                            new BufferedOutputStream(new FileOutputStream(destinationFile)));

                    // Now that the bitmap has been written to the file, notify the UI thread that
                    // the file is ready.
                    viewToSnapshot.post(completionCallback);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
