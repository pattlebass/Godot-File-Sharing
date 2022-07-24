package com.pattlebass.godotfilesharing;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.UsedByGodot;

import java.io.File;

public class GodotFileSharing extends org.godotengine.godot.plugin.GodotPlugin {
    private static final String TAG = "godot";
    private Activity activity;
    private Context context;

    private ContentResolver contentResolver;

    public GodotFileSharing(Godot godot) {
        super(godot);
        activity = godot.getActivity();
        context = godot.getContext();
        contentResolver = context.getContentResolver();
    }

    @NonNull
    @Override
    public String getPluginName() {
        return "GodotFileSharing";
    }

    @UsedByGodot
    public void shareFile(String path, String title, String subject, String text, String mimeType) {
        Log.d(TAG, "shareFile called on path: " + path);

        File f = new File(path);

        Uri uri;
        try {
            uri = FileProvider.getUriForFile(activity, activity.getPackageName(), f);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "The selected file can't be shared: " + path);
            return;
        }

        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        if (mimeType.isEmpty()) {
            String type = contentResolver.getType(uri);
            shareIntent.setType(type);
            Log.d(TAG, "Detected MIME type: " + type);
        } else {
            shareIntent.setType(mimeType);
        }
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        activity.startActivity(Intent.createChooser(shareIntent, title));
    }
}
