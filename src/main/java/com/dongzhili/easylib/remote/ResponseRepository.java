package com.dongzhili.easylib.remote;

import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public abstract class ResponseRepository<API> extends Repository {

    protected static long TIMEOUT_OUT_NEVER = Long.MAX_VALUE;

    protected API api;

    public ResponseRepository(API api) {
        this.api = api;
    }

    /**
     * 解包
     *
     * @return 取出ResponseWrapper中的data
     */
    protected static <T> ObservableTransformer<ResponseWrapper<T>, T> unwrap() {
        return new ObservableTransformer<ResponseWrapper<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<ResponseWrapper<T>> observable) {
                return observable
                        .flatMap(new Function<ResponseWrapper<T>, ObservableSource<T>>() {
                            @Override
                            public ObservableSource<T> apply(ResponseWrapper<T> wrapper) throws Exception {
                                // 解包之前先判断数据是否非法
                                checkResponse(wrapper);
                                return Observable.just(wrapper.getData());
                            }
                        });
            }
        };
    }

    /**
     * 解包
     *
     * @return 取出JsonElement中指定KEY的数据，并解析成实体类对象
     */
    protected static <T> ObservableTransformer<ResponseWrapper<JsonElement>, T> unwrap(final String wrapKey, final TypeToken<T> typeToken) {
        return new ObservableTransformer<ResponseWrapper<JsonElement>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<ResponseWrapper<JsonElement>> observable) {
                return observable
                        .flatMap(new Function<ResponseWrapper<JsonElement>, ObservableSource<T>>() {
                            @Override
                            public ObservableSource<T> apply(ResponseWrapper<JsonElement> wrapper) throws Exception {
                                checkResponse(wrapper);
                                JsonElement json = wrapper.getData();
                                if (json == null || !json.isJsonObject()) {
                                    throw new ApiException("-1", "数据解析错误！");
                                }

                                JsonElement wrapJson = json.getAsJsonObject().get(wrapKey);
                                if (wrapJson == null || wrapJson.isJsonNull()) {
                                    // 如果接口返回类似 {"status":"ok","message":{"key":"","message":"操作成功"},"data":{"list":null}}
                                    // 这种，会直接将 null -> emptyList(); 防止后端扑街。
                                    if (List.class.isAssignableFrom(typeToken.getRawType())) {
                                        return Observable.just((T) Collections.emptyList());
                                    }
                                    throw new ApiException("-1", "数据解析错误！");
                                }
                                return Observable.just(JsonUtil.fromJson(wrapJson, typeToken));
                            }
                        });
            }
        };
    }

    /**
     * 常规处理
     * 默认的线程调度、响应结果的合法性检查
     */
    protected static <R> ObservableTransformer<R, R> process() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<R, ObservableSource<R>>() {
                    @Override
                    public ObservableSource<R> apply(R data) throws Exception {
                        // 避免没有解包时，无法判断数据是否非法
                        if (data instanceof ResponseWrapper) {
                            checkResponse((ResponseWrapper) data);
                        }
                        return Observable.just(data);
                    }
                });
    }

    /**
     * 检查数据是否合法，不合法则抛出异常
     *
     * @throws ApiException 接口数据异常
     */
    private static void checkResponse(ResponseWrapper wrapper) throws ApiException {
        if (!wrapper.isSuccess()) {
            ApiException exception;
            try {
                final String errorCode = wrapper.getErrorMessage().getKey();
                final String errorMessage = wrapper.getErrorMessage().getMessage();
//                if (DataLayer.getGlobalErrorCodeHandler() != null) {
//                    DataLayer.getGlobalErrorCodeHandler().onGlobalErrorCodeHandle(errorCode, errorMessage);
//                }
                exception = new ApiException(errorCode, errorMessage);
            } catch (Exception e) {
                exception = new ApiException("-1", "数据解析错误！");
            }
            throw exception;
        }
    }
}

