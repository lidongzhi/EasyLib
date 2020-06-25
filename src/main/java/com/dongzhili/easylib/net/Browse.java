package com.dongzhili.easylib.net;

import com.dongzhili.easylib.base.BaseBean;
import com.dongzhili.easylib.bean.VersionBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * 获取数据
 */
public interface Browse {

    @GET
    Observable<BaseBean<VersionBean>> checkVersion(@Url String url, @Query("time") long time, @Query("type") int type);



//    /**
//     * 获取全公司拆分后线路
//     */
//    @FormUrlEncoded
//    @POST("phone/control/manage/task/line/all/list")
//    Observable<BaseBean<List<Line>>> getAllLine(@Field("userId") String userId,
//                                                @Field("keyCode") String keyCode,
//                                                @Field("taskLineId") String taskLineId,
//                                                @Field("content") String content);


}

