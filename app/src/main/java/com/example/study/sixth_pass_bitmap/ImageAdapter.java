package com.example.study.sixth_pass_bitmap;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.study.R;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    /**
     * 记录所有正在下载或等待下载的任务。
     */
    private Set<BitmapWorkerTask> taskCollection;
    /**
     * 图片缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。
     */
    private LruCache<String, Bitmap> mBitmapCache;
    /**
     * 图片硬盘缓存核心类。
     */
    private DiskLruCache diskLruCache;
    /**
     * 记录每个子项的高度。
     */
    private int mItemHeight = 0;
    private Context mContext;
    private RecyclerView mPhotoWall;
    private String[] urls;

    public ImageAdapter(Context context, RecyclerView photoWall, String[] urls) {
        mPhotoWall = photoWall;
        mContext = context;
        this.urls = urls;
        taskCollection = new HashSet<>();
        int size = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = size / 8;
        mBitmapCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        File img = getDiskCacheDir(context, "img");
        if (!img.exists()) {
            img.mkdirs();
        }
        try {
            diskLruCache = DiskLruCache.open(img, getAppVersion(context), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public ImageAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image, null);
        return new ImageViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ImageViewHolder holder, int i) {
//        if (holder.ivImage.getLayoutParams().height != mItemHeight) {
//            holder.ivImage.getLayoutParams().height = mItemHeight;
//        }
        String url = urls[i];
        holder.ivImage.setTag(url);
        loadBitmap(holder.ivImage, url);
    }

    @Override
    public int getItemCount() {
        return urls == null ? 0 : urls.length;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
        }
    }

    public void setmItemHeight(int height) {
        this.mItemHeight = height;
    }

    /**
     * 将Bitmap缓存到内存中
     *
     * @param key
     * @param bitmap
     */
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mBitmapCache.put(key, bitmap);
        }
    }

    /**
     * 从内存中取出Bitmap
     *
     * @param key
     * @return
     */
    public Bitmap getBitmapFromMemCache(String key) {
        return mBitmapCache.get(key);
    }

    /**
     * 加载图片
     *
     * @param target
     * @param resId
     */
    public void loadBitmap(ImageView target, int resId) {
        String key = String.valueOf(resId);
        Bitmap bitmap = getBitmapFromMemCache(key);
        if (bitmap != null) {
            target.setImageBitmap(bitmap);
        } else {
            target.setImageResource(R.mipmap.ic_launcher);
            BitmapWorker worker = new BitmapWorker(target);
            worker.execute(resId);
        }
    }

    /**
     * 加载Bitmap对象。此方法会在LruCache中检查所有屏幕中可见的ImageView的Bitmap对象，
     * 如果发现任何一个ImageView的Bitmap对象不在缓存中，就会开启异步线程去下载图片。
     */
    public void loadBitmap(ImageView target, String url) {
        Bitmap bitmapFromMemCache = getBitmapFromMemCache(url);
        if (bitmapFromMemCache == null) {
            BitmapWorkerTask task = new BitmapWorkerTask();
            taskCollection.add(task);
            task.execute(url);
        }
    }

    /**
     * 将缓存记录同步到journal文件中。
     */
    public void flushCache() {
        if (diskLruCache != null) {
            try {
                diskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 加载图片的AsyncTask
     */
    class BitmapWorker extends AsyncTask<Integer, Void, Bitmap> {
        private ImageView target;

        public BitmapWorker(ImageView target) {
            this.target = target;
        }

        @Override
        protected Bitmap doInBackground(Integer... integers) {
            Bitmap bitmap = decodeBitmapFromResource(mContext.getResources(), integers[0], target.getWidth(), target.getHeight());
            addBitmapToMemoryCache(String.valueOf(integers[0]), bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            target.setImageBitmap(bitmap);
        }
    }

    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        private String imgUrl;

        @Override
        protected Bitmap doInBackground(String... strings) {
            imgUrl = strings[0];
            FileDescriptor fd = null;
            FileInputStream fis = null;
            DiskLruCache.Snapshot snapshot = null;
            String s = hashKeyForDisk(imgUrl);
            try {
                snapshot = diskLruCache.get(s);
                if (snapshot == null) {
                    DiskLruCache.Editor edit = diskLruCache.edit(s);
                    if (edit != null) {
                        OutputStream os = edit.newOutputStream(0);
                        if (downloadUrlToStream(imgUrl, os)) {
                            edit.commit();
                        } else {
                            edit.abort();
                        }
                    }
                    snapshot = diskLruCache.get(s);
                }
                if (snapshot != null) {
                    fis = (FileInputStream) snapshot.getInputStream(0);
                    fd = fis.getFD();
                }
                Bitmap bitmap = null;
                if (fd != null) {
                    bitmap = BitmapFactory.decodeFileDescriptor(fd);
                }
                if (bitmap != null) {
                    addBitmapToMemoryCache(strings[0], bitmap);
                }
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fd != null && fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            // 根据Tag找到相应的ImageView控件，将下载好的图片显示出来。
            ImageView imageView = mPhotoWall.findViewWithTag(imgUrl);
            if (imageView != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
            taskCollection.remove(this);
        }
    }

    /**
     * 取消所有正在下载或等待下载的任务。
     */
    public void cancelAllTasks() {
        if (taskCollection != null) {
            for (BitmapWorkerTask task : taskCollection) {
                task.cancel(false);
            }
        }
    }

    /**
     * 从资源文件中加载图片
     *
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public Bitmap decodeBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(res, resId, options);
        return bitmap;
    }

    /**
     * 计算图片的缩放因子
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        int inSampleSize = 1;
        if (outHeight > reqHeight || outWidth > reqWidth) {
            int halfHeight = outHeight / 2;
            int halfWidth = outWidth / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * 获取磁盘缓存文件夹
     *
     * @param context
     * @param dirName
     * @return
     */
    public File getDiskCacheDir(Context context, String dirName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + dirName);
    }

    /**
     * 获取App版本号
     *
     * @param context
     * @return
     */
    public int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 从网上下载文件
     *
     * @param url
     * @param os
     * @return
     */
    public boolean downloadUrlToStream(String url, OutputStream os) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            URL url1 = new URL(url);
            urlConnection = (HttpURLConnection) url1.openConnection();
            bis = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            bos = new BufferedOutputStream(os, 8 * 1024);
            int b;
            while ((b = bis.read()) != -1) {
                bos.write(b);
            }
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
                try {
                    if (bos != null) {
                        bos.close();
                    }
                    if (bis != null) {
                        bis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 对字符串进行MD5编码
     *
     * @param key
     * @return
     */
    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = byteToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    public String byteToHexString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                builder.append("0");
            }
            builder.append(hex);
        }
        return builder.toString();
    }
}
