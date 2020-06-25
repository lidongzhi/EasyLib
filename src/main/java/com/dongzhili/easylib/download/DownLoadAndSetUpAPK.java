package com.dongzhili.easylib.download;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.dongzhili.easylib.net.BasePresenter;
import com.dongzhili.easylib.net.RetrofitService;
import com.dongzhili.easylib.utils.DialogUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author MXQ
 * create at 2017/3/10 17:22
 * email: 1299242483@qq.com
 */
public class DownLoadAndSetUpAPK {
    private Activity mActivity;
    private DialogUtil progressDialog;
    public RetrofitService.UpdateVersion apiStore;
    private Call<ResponseBody> call;
    private String url;
    private boolean isLoadAll = false;
    private boolean isLoadSuccess = true;
    private LoadFailure loadFailure = null;
    // 用于判断正在下载安装包时,司机刷卡事件将被过滤
    public static boolean isDownloading = false;
    public void DownLoadAndSetUpAPK(Activity activity, String downUrl, LoadFailure loadFailure){
        mActivity = activity;
        url = downUrl;
        this.loadFailure = loadFailure;
        showDialog();
        setHttp();
        progressHandler();
        setCallBack();
    }

    public void DownLoadAndSetUpAPK(Activity activity, String downUrl){
        mActivity = activity;
        url = downUrl;
        showDialog();
        setHttp();
        progressHandler();
        setCallBack();
    }

    private void showDialog(){
        isDownloading = true;
        progressDialog = new DialogUtil(mActivity, "正在下载，请稍后...", "", DialogUtil.PROGRESS);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialogListener();
    }

    private void setHttp(){
        Retrofit.Builder retrofitBuilder =
                // 获取一个实例
                new Retrofit.Builder()
                        // 使用RxJava作为回调适配器
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        // 添加Gson转换器
                        .addConverterFactory(GsonConverterFactory.create())
                        // 设置baseUrl{以"/"结尾}
                        .baseUrl(BasePresenter.BASE_URL);
        // Retrofit文件下载进度显示的解决方法
        OkHttpClient.Builder builder = ProgressHelper.addProgress(null);

        apiStore = retrofitBuilder
                .client(builder.build())
                .build().create(RetrofitService.UpdateVersion.class);
    }

    private void progressHandler(){
        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done) {
                Log.e("是否在主线程中运行", String.valueOf(Looper.getMainLooper() == Looper.myLooper()));
                Log.e("onProgress", String.format("%d%% done\n", (100 * bytesRead) / contentLength));
                Log.e("done", "--->" + String.valueOf(done));
                progressDialog.ProgressPlan(contentLength / 1024, bytesRead / 1024);
                if (done) {
                    isLoadAll = true;
//                    progressDialog.dismiss();
                    progressDialog.setTitle("正在安装，请稍后！");
                }
            }
        });
    }

    private void setCallBack(){
        call = apiStore.getFile(url);
        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                new AsyncTask<Void, Long, Void>() {
                    File file;
                    @Override
                    protected Void doInBackground(Void... voids) {
                        try {
                            InputStream is = response.body().byteStream();
                            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//判断SD卡是否挂载
                                File foder = new File(Environment.getExternalStorageDirectory(), Constants.Path.SECONDPATH + "/");
                                file = new File(foder, Constants.Path.APKNAME);
                                if (!foder.exists()) {
                                    foder.mkdirs();
                                }
                                FileOutputStream fos = new FileOutputStream(file);
                                BufferedInputStream bis = new BufferedInputStream(is);
                                byte[] buffer = new byte[1024];
                                int len;
                                while ((len = bis.read(buffer)) != -1) {
                                    fos.write(buffer, 0, len);
                                    fos.flush();
                                }
                                fos.close();
                                bis.close();
                                is.close();

                            } else {
                                if (loadFailure != null) {
                                    loadFailure.onLoadFailureListener("请检查你的SD卡");
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            isDownloading = false;
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        installApk(file.getAbsolutePath());
                    }
                }.execute();
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                isLoadSuccess = false;
                progressDialog.dismiss();
                if (loadFailure != null){
                    loadFailure.onLoadFailureListener("更新失败");
                }

            }
        });
    }

    //发送到设备安装新的apk
    private final String FLAG_INSTALL = "android.intent.action.EBUSDRIVER_UPDATE_HELPER";
    public void installApk(String filepath){
//        Logger.i("安装新版本");
        Intent intent = new Intent(FLAG_INSTALL);
        intent.putExtra("filepath", filepath);
        mActivity.getApplication().sendBroadcast(intent);
    }

    private void progressDialogListener(){
        progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (!isLoadAll && isLoadSuccess){
                    showTipDialog();
                }
            }
        });
    }

    private void showTipDialog(){
        final DialogUtil tipDialog = new DialogUtil(mActivity, "提示", "确定要放弃更新？", DialogUtil.HAVEBUTTON);
        tipDialog.show();
        tipDialog.setCancelable(false);
        tipDialog.ButtonQuery(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipDialog.dismiss();
                call.cancel();
            }
        });

        tipDialog.ButtonCancel(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipDialog.dismiss();
                progressDialog.show();
            }
        });

    }

    public void setOnLoadFailureListener(LoadFailure loadFailureListener){
        this.loadFailure = loadFailureListener;
    }

    public interface LoadFailure{
        void onLoadFailureListener(String msg);
    }


}
