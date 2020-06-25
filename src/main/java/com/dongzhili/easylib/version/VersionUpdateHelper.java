package com.dongzhili.easylib.version;

import android.app.Activity;
import android.os.Build;
import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by 李振强 on 2017/8/28.
 */

public class VersionUpdateHelper {
    public static final int INSTALL_PACKAGES_REQUESTCODE = 9;
    OnVersionUpdateListener mListener;
    private MaterialDialog tipsDialog;
    private MaterialDialog downloadDialog;
    private DownLoadApkHelper mDownLoadApkHelper;


    /**
     * final String versionName = "1.0.0";
     * String path =  "/WuXiDriver/";
     * String file = "WuXiDriver" + versionName + ".apk";
     *
     * @param listener callback
     */

    public VersionUpdateHelper(final Activity mActivity, String title, String message, boolean isForce, final String provider,
                               final String downUrl, final String baseUrl, final String downloadLocalPath, final String downloadLocalFileName, final OnVersionUpdateListener listener) {
        mListener = listener;
        if (isForce) {
            download(mActivity, downUrl, baseUrl, downloadLocalPath, downloadLocalFileName, provider);
        } else {
            tipsDialog = new MaterialDialog.Builder(mActivity)
                    .title(title)
                    .content(message)
                    .positiveText("立刻下载")
                    .negativeText("下次再说")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull final MaterialDialog dialog, @NonNull DialogAction which) {
                            download(mActivity, downUrl, baseUrl, downloadLocalPath, downloadLocalFileName, provider);
                            if (dialog.isShowing())
                                dialog.dismiss();
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            listener.nextTime();
                            if (dialog.isShowing())
                                dialog.dismiss();
                        }
                    })
                    .show();
        }
    }

    public VersionUpdateHelper(final Activity mActivity, final String provider,
                               final String downUrl, final String baseUrl, final String downloadLocalPath, final String downloadLocalFileName, final OnVersionUpdateListener listener) {
        mListener = listener;
        download(mActivity, downUrl, baseUrl, downloadLocalPath, downloadLocalFileName, provider);
    }

    private void download(final Activity mActivity, String downUrl, String baseUrl, final String downloadLocalPath, final String downloadLocalFileName, final String provider) {
        downloadDialog = new MaterialDialog.Builder(mActivity)
                .title("正在下载")
                .progress(false, 100, false)
                .cancelable(false)
                .positiveText("暂停")
                .negativeText("继续")
                .autoDismiss(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mDownLoadApkHelper.pause();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mDownLoadApkHelper.download(downloadLocalPath, downloadLocalFileName);
                    }
                })
                .show();
        if (mDownLoadApkHelper == null) {
            mDownLoadApkHelper = new DownLoadApkHelper(downUrl, baseUrl, downloadLocalPath, downloadLocalFileName, new DownLoadApkHelper.OnDownloadApkListener() {
                @Override
                public void downloadSuccess() {
                    downloadDialog.getContentView().post(new Runnable() {
                        @Override
                        public void run() {
                            downloadDialog.setContent("下载完成，正在安装");
                            checkIsAndroidO(mActivity, provider, downloadLocalPath, downloadLocalFileName);
                        }
                    });

                }

                @Override
                public void failure(String s) {
                    downloadDialog.getContentView().post(new Runnable() {
                        @Override
                        public void run() {
                            mListener.downloadFailure(s);
                            if (downloadDialog != null && downloadDialog.isShowing())
                                downloadDialog.dismiss();
                        }
                    });
                }

                @Override
                public void process(long currentLength, long sumLength) {
                    downloadDialog.getContentView().post(new Runnable() {
                        @Override
                        public void run() {
                            if (downloadDialog != null && downloadDialog.isShowing()) {
                                float percent = (float) currentLength / (float) sumLength;
                                int progress = Math.round(percent * 100);
                                downloadDialog.setProgress(progress);
                            }
                        }
                    });
                }

                @Override
                public void sdCardError(String s) {
                    downloadDialog.getContentView().post(new Runnable() {
                        @Override
                        public void run() {
                            mListener.sdCardError(s);
                            if (downloadDialog != null && downloadDialog.isShowing()) {
                                downloadDialog.dismiss();
                            }
                        }
                    });
                }
            });
        }
    }

    public void checkIsAndroidO(Activity mActivity, String provider, String downloadLocalPath, String downloadLocalFileName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            installApk(mActivity, provider, downloadLocalPath, downloadLocalFileName);
//            boolean b = mActivity.getPackageManager().canRequestPackageInstalls();
//            if (b) {
////安装应用的逻辑(写自己的就可以)
//            } else {
//                //请求安装未知应用来源的权限
//                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, INSTALL_PACKAGES_REQUESTCODE);
//            }
        } else {
            installApk(mActivity, provider, downloadLocalPath, downloadLocalFileName);
        }
    }

    public void installApk(Activity mActivity, String provider, String downloadLocalPath, String downloadLocalFileName) {
//        downloadDialog = new MaterialDialog.Builder(mActivity)
//                .title("正在下载")
//                .progress(false, 100, false)
//                .cancelable(false)
//                .show();
//        downloadDialog.setContent("下载完成，正在安装");
        InstallHelper.installApk(mActivity, provider, downloadLocalPath, downloadLocalFileName, downloadDialog);
    }

    //    public void dismiss(){
//        if (tipsDialog !)
//        tipsDialog.dismiss();
//    }
    public interface OnVersionUpdateListener {
        void downloadFailure(String s);

        void sdCardError(String s);

        void nextTime();
    }
}
