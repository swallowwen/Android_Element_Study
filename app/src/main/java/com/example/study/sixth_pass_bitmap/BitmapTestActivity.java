package com.example.study.sixth_pass_bitmap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.study.R;

public class BitmapTestActivity extends AppCompatActivity {
    private ImageAdapter adapter;
    private RecyclerView rlvImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        rlvImage = findViewById(R.id.rlv_image);
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 3);
        rlvImage.setLayoutManager(manager);
        adapter = new ImageAdapter(this, rlvImage, Images.imageThumbUrls);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        } else {
            rlvImage.setAdapter(adapter);
        }
//        iv.post(new Runnable() {
//            @Override
//            public void run() {
//                loadBitmap(iv, R.drawable.timg);
//            }
//        });
//        File bitmap = getDiskCacheDir(this, "bitmap");
//        if (!bitmap.exists()) {
//            bitmap.mkdirs();
//        }
//        try {
//            diskLruCache = DiskLruCache.open(bitmap, getAppVersion(this), 1, 10 * 1024 * 1024);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String imgUrl = "https://img-my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
//        String key = hashKeyForDisk(imgUrl);
//        try {
//            DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
//            if (snapshot != null) {
//                InputStream inputStream = snapshot.getInputStream(0);
//                Bitmap bitmap1 = BitmapFactory.decodeStream(inputStream);
//                iv.setImageBitmap(bitmap1);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    String imgUrl = "https://img-my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
//                    String key = hashKeyForDisk(imgUrl);
//
//                    DiskLruCache.Editor editor = diskLruCache.edit(key);
//                    if (editor != null) {
//                        OutputStream os = editor.newOutputStream(0);
//                        if (downloadUrlToStream(imgUrl, os)) {
//                            editor.commit();
//                        } else {
//                            editor.abort();
//                        }
//                    }
//                    diskLruCache.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapter.flushCache();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.cancelAllTasks();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            boolean isAllGranted = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                }
            }
            if (isAllGranted) {
                rlvImage.setAdapter(adapter);
            }
        }
    }

}
