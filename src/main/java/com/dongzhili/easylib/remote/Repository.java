package com.dongzhili.easylib.remote;

public abstract class Repository {

//    /**
//     * 读取缓存数据
//     *
//     * @param cacheKey  缓存的KEY（不同数据对应不同的缓存KEY）
//     * @param typeToken 实体类型
//     * @param expires   缓存有效期（单位：毫秒）
//     * @return 如果存在缓存，返回缓存数据；否则读取网络数据并缓存
//     */
//    protected <R> ObservableTransformer<R, R> cache(final String cacheKey, final TypeToken<R> typeToken, final long expires) {
//        final String fullCacheKey = getClass().getName() + "_" + cacheKey;
//        return new ObservableTransformer<R, R>() {
//            @Override
//            public ObservableSource<R> apply(Observable<R> observable) {
//                // 读取缓存
//                CacheInfo cacheInfo = CacheDao.findByKey(fullCacheKey);
//                // 检测缓存是否有效
//                if (cacheInfo != null &&
//                        !TextUtils.isEmpty(cacheInfo.json)
//                        && cacheInfo.createTime + expires > System.currentTimeMillis()) {
//                    R result = null;
//                    try {
//                        result = JsonUtils.fromJson(cacheInfo.json, typeToken);
//                    } catch (Exception e) {
//                        LogUtils.e(e);
//                    }
//                    if (result != null) {
//                        return Observable.just(result);
//                    }
//                }
//
//                // 发起原始请求，并写入缓存
//                return observable
//                        .observeOn(Schedulers.io())
//                        .map(new Function<R, R>() {
//                            @Override
//                            public R apply(R data) throws Exception {
//                                String json = JsonUtils.toJson(data);
//                                CacheDao.saveOrUpdate(fullCacheKey, json);
//                                return data;
//                            }
//                        });
//            }
//        };
//    }
//
//    /**
//     * 读取缓存数据
//     *
//     * @param cacheKey  缓存的KEY（不同数据对应不同的缓存KEY）
//     * @param typeToken 实体类型
//     * @param expires   缓存有效期（单位：毫秒）
//     * @return 如果存在缓存，返回缓存数据；否则读取网络数据并缓存
//     */
//    protected <R> ObservableTransformer<R, R> onErrorReturnCache(final String cacheKey, final TypeToken<R> typeToken, final long expires) {
//        final String fullCacheKey = getClass().getName() + "_" + cacheKey;
//        return new ObservableTransformer<R, R>() {
//            @Override
//            public ObservableSource<R> apply(Observable<R> observable) {
//                // 发起原始请求，并写入缓存
//                return observable
//                        .observeOn(Schedulers.io())
//                        .map(new Function<R, R>() {
//                            @Override
//                            public R apply(R data) throws Exception {
//                                String json = JsonUtil.toJson(data);
//                                CacheDao.saveOrUpdate(fullCacheKey, json);
//                                return data;
//                            }
//                        })
//                        .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends R>>() {
//                            @Override
//                            public ObservableSource<? extends R> apply(Throwable throwable) throws Exception {
//                                // 读取缓存
//                                CacheInfo cacheInfo = CacheDao.findByKey(fullCacheKey);
//                                // 检测缓存是否有效
//                                if (cacheInfo != null &&
//                                        !TextUtils.isEmpty(cacheInfo.json)
//                                        && cacheInfo.createTime + expires > System.currentTimeMillis()) {
//                                    R result = null;
//                                    try {
//                                        result = JsonUtil.fromJson(cacheInfo.json, typeToken);
//                                    } catch (Exception e) {
//                                        LogUtils.e(e);
//                                    }
//                                    if (result != null) {
//                                        return Observable.just(result);
//                                    }
//                                }
//                                return Observable.error(throwable);
//                            }
//                        });
//            }
//        };
//    }
}

