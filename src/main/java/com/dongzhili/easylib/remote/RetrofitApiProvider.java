package com.dongzhili.easylib.remote;

import androidx.annotation.NonNull;

import com.dongzhili.easylib.utils.FileUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class RetrofitApiProvider {

    private HttpUrl baseApi;
    private Retrofit sRetrofit;

    public RetrofitApiProvider(@NonNull RetrofitConfig config) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(config.getHttpConnectTimeout(), TimeUnit.SECONDS)
                .writeTimeout(config.getHttpWriteTimeout(), TimeUnit.SECONDS)
                .readTimeout(config.getHttpReadTimeout(), TimeUnit.SECONDS)
                .cache(new Cache(config.getHttpCacheDir(), config.getResponseCacheSize()));
//                .addInterceptor(new DynamicInterceptor());
        for (Interceptor interceptor : config.interceptors) {
            httpClientBuilder.addInterceptor(interceptor);
        }
        if (AppUtils.isDebug()) {
            for (Interceptor interceptor : config.debugInterceptors) {
                httpClientBuilder.addInterceptor(interceptor);
            }
        }
        final OkHttpClient httpClient = httpClientBuilder.build();
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(config.getHttpUrl())
                .client(httpClient)
                .addConverterFactory(CustomGsonConverterFactory.create(config.getGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        sRetrofit = retrofitBuilder.build();
        baseApi = sRetrofit.baseUrl();
    }

    public <T> T provideApi(Class<T> apiClass) {
        return sRetrofit.create(apiClass);
    }

    public static RetrofitApiProvider.RetrofitConfig newRetrofitConfig(String baseUrl, String channel, int subVersionCode) {
        RetrofitApiProvider.RetrofitConfig config = new RetrofitApiProvider.RetrofitConfig();
        config.setHttpUrl(baseUrl);
        config.setHttpCacheDir(FileUtil.getHttpCacheDir());
        config.clearDebugInterceptors();
//        config.addDebugInterceptor(new GuGuLoggingInterceptor(true, true));
//        config.addInterceptor(new UnifiedParameterInterceptor(channel, subVersionCode));
//        config.addInterceptor(new ChumanCacheInterceptor());
//        config.addInterceptor(new PlayerInterceptor());
        return config;
    }

    public static final class RetrofitConfig {

        private long responseCacheSize = 10 * 1024 * 1024;
        private long httpConnectTimeout = 10;
        private long httpReadTimeout = 10;
        private long httpWriteTimeout = 10;

        private Gson gson;
        private File httpCacheDir;
        private String httpUrl;
        private final List<Interceptor> interceptors = new ArrayList<>();
        private final List<Interceptor> debugInterceptors = new ArrayList<>();

        public RetrofitConfig() {
//            addDebugInterceptor(new LoggingInterceptor());
        }

        public long getResponseCacheSize() {
            return responseCacheSize;
        }

        public void setResponseCacheSize(long responseCacheSize) {
            this.responseCacheSize = responseCacheSize;
        }

        public long getHttpConnectTimeout() {
            return httpConnectTimeout;
        }

        public void setHttpConnectTimeout(long httpConnectTimeout) {
            this.httpConnectTimeout = httpConnectTimeout;
        }

        public long getHttpReadTimeout() {
            return httpReadTimeout;
        }

        public void setHttpReadTimeout(long httpReadTimeout) {
            this.httpReadTimeout = httpReadTimeout;
        }

        public long getHttpWriteTimeout() {
            return httpWriteTimeout;
        }

        public void setHttpWriteTimeout(long httpWriteTimeout) {
            this.httpWriteTimeout = httpWriteTimeout;
        }

        public Gson getGson() {
            if (gson == null) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(AutoResponseWrapper.class,
                        new AutoResponseWrapper.AutoResponseWrapperJsonDeserializer());
                gson = gsonBuilder.create();
            }
            return gson;
        }

        public void setGson(Gson gson) {
            this.gson = gson;
        }

        public File getHttpCacheDir() {
            if (httpCacheDir == null) {
                httpCacheDir = new File(AppUtils.getApplication().getCacheDir(), "retrofit");
                boolean mkdirs = httpCacheDir.mkdirs();
                if (!mkdirs) {
                    httpCacheDir = AppUtils.getApplication().getCacheDir();
                }
            }
            return httpCacheDir;
        }

        public void setHttpCacheDir(File httpCacheDir) {
            this.httpCacheDir = httpCacheDir;
        }

        public String getHttpUrl() {
            return httpUrl;
        }

        public void setHttpUrl(String httpUrl) {
            this.httpUrl = httpUrl;
        }

        public void addInterceptor(Interceptor interceptor) {
            interceptors.add(interceptor);
        }

        public void removeInterceptor(Interceptor interceptor) {
            interceptors.remove(interceptor);
        }

        public void clearInterceptors() {
            interceptors.clear();
        }

        public void addDebugInterceptor(Interceptor interceptor) {
            debugInterceptors.add(interceptor);
        }

        public void removeDebugInterceptor(Interceptor interceptor) {
            debugInterceptors.remove(interceptor);
        }

        public void clearDebugInterceptors() {
            debugInterceptors.clear();
        }
    }
}
