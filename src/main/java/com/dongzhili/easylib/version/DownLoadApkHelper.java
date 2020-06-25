package com.dongzhili.easylib.version;

import android.os.Environment;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

// 负责下载apk
public class DownLoadApkHelper {
    private boolean isPause = false;
    public DownloadFile apiStore;
    private Call<ResponseBody> call;
    private String url;
    private OnDownloadApkListener onDownloadApkListener;

    public DownLoadApkHelper(String downUrl, String baseUrl, String downloadLocalPath, String downloadLocalFileName, OnDownloadApkListener listener) {
        url = downUrl;
        onDownloadApkListener = listener;
        setHttp(baseUrl);
        setCallBack(downloadLocalPath, downloadLocalFileName);
    }

    private void setHttp(String baseUrl) {
        Retrofit.Builder retrofitBuilder =
                // 获取一个实例
                new Retrofit.Builder()
                        // 使用RxJava作为回调适配器
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        // 添加Gson转换器
                        .addConverterFactory(GsonConverterFactory.create())
                        // 设置baseUrl{以"/"结尾}
                        .baseUrl(baseUrl);
        // Retrofit文件下载进度显示的解决方法
        OkHttpClient.Builder builder = ProgressHelper.addProgress(null).connectTimeout(60, TimeUnit.SECONDS);

        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {

            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done) {
                onDownloadApkListener.process(bytesRead, contentLength);
            }
        });

        apiStore = retrofitBuilder
                .client(builder.build())
                .build().create(DownloadFile.class);
    }

    public void setCallBack(final String downloadLocalPath, final String downloadLocalFileName) {
        Log.w("log", "00000000000000000");
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//判断SD卡是否挂载
            File folder = new File(Environment.getExternalStorageDirectory(), downloadLocalPath);
            Log.w("log", folder.getAbsolutePath());
            if (!folder.exists()) {
                boolean mkdirs = folder.mkdirs();
                Log.w("log", "mkdirs" + mkdirs);
            }
            File file = new File(folder, downloadLocalFileName);
            Log.w("log", file.getAbsolutePath());
            long range = 0;
            if (file.exists()) {
                range = file.length();
            }
            Log.w("log", "range=" + Long.toString(range) + "-");

            long finalRange = range;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        long progress = finalRange;
                        call = apiStore.getFile("bytes=" + Long.toString(progress) + "-", url);
                        Response<ResponseBody> response = call.execute();
                        if (response.isSuccessful()) {
                            Log.w("log", "111111111111111");
//                            Log.w("log", call.request().url().toString());
                            InputStream is = response.body().byteStream();
                            FileOutputStream fos = new FileOutputStream(file, true);
                            BufferedInputStream bis = new BufferedInputStream(is);
                            byte[] buffer = new byte[1024];
                            int len;
                            while ((len = bis.read(buffer)) != -1) {
                                if (isPause)
                                    break;
                                fos.write(buffer, 0, len);
                                fos.flush();
                                progress += len;
//                                Log.w("log", progress + ":" + response.body().contentLength());
                                ProgressHelper.getmProgressListener().onProgress(progress, response.body().contentLength(), false);
//                            Thread.sleep(20);
                            }
                            Log.w("log", "222222222222222222222-------" + progress);
                            fos.close();
                            bis.close();
                            is.close();
                            if (!isPause) {
                                onDownloadApkListener.downloadSuccess();
                            } else {
                                Log.w("log", "被暂停了");
                            }
                        } else {
                            onDownloadApkListener.failure("更新失败");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
//                        file.delete();
                    }
                }
            }).start();
        } else {
            onDownloadApkListener.sdCardError("请检查你的SD卡");
        }
    }

    public void pause() {
        isPause = true;
        if (call != null) {
            call.cancel();
        }
    }

    public void download(String downloadLocalPath, String downloadLocalFileName) {
        isPause = false;
        setCallBack(downloadLocalPath, downloadLocalFileName);
    }


    public interface OnDownloadApkListener {
        void downloadSuccess();

        void failure(String info);

        void process(long currentLength, long maxLength);

        void sdCardError(String info);
    }

    public interface DownloadFile {
        /**
         * 下载文件
         */
        @Streaming
        @GET
        Call<ResponseBody> getFile(@Header("Range") String range, @Url String url);
    }

}
