package com.dongzhili.easylib.net;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.dongzhili.easylib.base.BaseView;
import com.dongzhili.easylib.net.cookie.ClearableCookieJar;
import com.dongzhili.easylib.net.cookie.PersistentCookieJar;
import com.dongzhili.easylib.net.cookie.SetCookieCache;
import com.dongzhili.easylib.net.cookie.SharedPrefsCookiePersistor;
import com.dongzhili.easylib.utils.ConnectionUtil;

import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

public class BasePresenter<V extends BaseView> {
//    public static final String BASE_URL = "http://10.0.2.2:8080/TakeoutService/";     //模拟器地址
//    public static final String BASE_URL = "http://192.168.3.144:8080/TakeoutService/";     //Demo地址
//    public static final String BASE_URL = "http://192.168.0.50:8081/yd_driver_app/"; // 内网
public static final String BASE_URL = "http://yddriver.szebus.net/"; // 外网
    public static final String updateUrl = BASE_URL + "app/version/work";
    protected RequestBodyHelper requestBodyHelper = RequestBodyHelper.getInstance();
    private static final int DEFAULT_TIMEOUT = 30;
    //TODO:token问题
    public static String token = "";
    private static String coustomerId = "";
    protected SharedPreferences mSp;

    //    protected PhysicalExamBeforeWorkService mRetrofitService;
    protected RequestBodyHelper mRequestBodyHelper = RequestBodyHelper.getInstance();
    private OkHttpClient client = new OkHttpClient();
    protected Retrofit mRetrofit;

    public V mvpView;
    protected Context mContext;

    protected BasePresenter(V mvpView, Context context) {
        this.mvpView = mvpView;
        this.mContext = context;
        mSp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        // 使用RxJava作为回调适配器
        // 使用Gson作为数据转换器
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 使用RxJava作为回调适配器
                    .addConverterFactory(ResponseConvertFactory.create()) // 使用Gson作为数据转换器
                    .client(getClient())
                    .build();
        }

//        if (mRetrofitService == null)
//            mRetrofitService = mRetrofit.create(PhysicalExamBeforeWorkService.class);
//            mRetrofitService = mRetrofit.create(RetrofitService.class);
    }

    protected <T> ObservableTransformer<T, T> applySchedulers() {
        return observable -> (Observable<T>) observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
    }

    private OkHttpClient getClient() {
        //setup cache
        File httpCacheDirectory = new File(mContext.getCacheDir(), "okhttpCache");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        //cookie
        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(
                mContext));
        try {
//            SSLContext sslContext = SSLContext.getInstance("SSL");
//            sslContext.init(null, new TrustManager[]{getTrustManager()}, null);
//            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            SSLSocketFactory sslSocketFactory = sc.getSocketFactory();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                client = new OkHttpClient.Builder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request.Builder builder = chain.request()
                                        .newBuilder();
                                String currentTimeMillis = String.valueOf(System.currentTimeMillis());
                                String versionName = "1.0";
                                String platform = "android";
                                builder.addHeader("time", currentTimeMillis);
                                builder.addHeader("platform", platform);
                                builder.addHeader("version", versionName);

                                if (!TextUtils.isEmpty(token)) {
                                    builder.addHeader("customerId", coustomerId);
                                    builder.addHeader("token", token);
                                }
//                                else {
//                                    builder.removeHeader("token");
//                                }
                                Request request = builder.build();

                                Log.v("netData", "request:" + request.toString());
                                return chain.proceed(request);
                            }
                        })
                        .addNetworkInterceptor(NetCacheInterceptor)
                        .addInterceptor(OfflineCacheInterceptor)
                        .cache(cache)
                        //设置cookie 内存持久化
                        .cookieJar(cookieJar)
                        .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .hostnameVerifier(DO_NOT_VERIFY)
                        .sslSocketFactory(sslSocketFactory)//证书认证 使用HTTPS协议
                        .build();
            } else {
                client = new OkHttpClient.Builder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request.Builder builder = chain.request()
                                        .newBuilder();
                                String currentTimeMillis = String.valueOf(System.currentTimeMillis());
                                String versionName = "1.0";
                                String platform = "android";
                                builder.addHeader("time", currentTimeMillis);
                                builder.addHeader("platform", platform);
                                builder.addHeader("version", versionName);

                                if (!TextUtils.isEmpty(token)) {
                                    builder.addHeader("customerId", coustomerId);
                                    builder.addHeader("token", token);
                                }
//                                else {
//                                    builder.removeHeader("token");
//                                }
                                Request request = builder.build();

                                Log.v("netData", "request:" + request.toString());
                                return chain.proceed(request);
                            }
                        })
                        .addNetworkInterceptor(NetCacheInterceptor)
                        .addInterceptor(OfflineCacheInterceptor)
                        .cache(cache)
                        //设置cookie 内存持久化
                        .cookieJar(cookieJar)
                        .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .hostnameVerifier(DO_NOT_VERIFY)
                        .sslSocketFactory(sslSocketFactory)//证书认证 使用HTTPS协议
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

    /**
     * 有网时候的缓存
     */
    final Interceptor NetCacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            int onlineCacheTime = 60;//在线的时候的缓存过期时间，如果想要不缓存，直接时间设置为0
            return response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + onlineCacheTime)
                    .removeHeader("Pragma")
                    .build();
        }
    };
    /**
     * 没有网时候的缓存
     */
    final Interceptor OfflineCacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!ConnectionUtil.isNetworkAvailable(mContext)) {
                int offlineCacheTime = 60 * 60 * 8;//离线的时候的缓存的过期时间
                request = request.newBuilder()
//                        .cacheControl(new CacheControl
//                                .Builder()
//                                .maxStale(60,TimeUnit.SECONDS)
//                                .onlyIfCached()
//                                .build()
//                        ) 两种方式结果是一样的，写法不同
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + offlineCacheTime)
                        .build();
            }
            return chain.proceed(request);
        }
    };

    private X509TrustManager getTrustManager() {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            return (X509TrustManager) trustManagers[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    private TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }

        public void checkClientTrusted(X509Certificate[] chain,
                                       String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain,
                                       String authType) throws CertificateException {
        }
    }};


}
