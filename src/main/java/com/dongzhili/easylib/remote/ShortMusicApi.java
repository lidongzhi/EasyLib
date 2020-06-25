package com.dongzhili.easylib.remote;

import com.google.gson.JsonElement;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ShortMusicApi {

    /**
     * 获取音乐分类列表 get_music_category_list
     */
    @GET("?m=Api&c=Video&a=get_music_category_list")
    Observable<ResponseWrapper<JsonElement>> listMusicCategory();


    @GET("?m=Api&c=Video&a=get_music_recommend_list")
    Observable<ResponseWrapper<JsonElement>> listRecommendMusic(
            @Query("page") Integer page,
            @Query("pagesize") Integer pagesize
    );

    @GET("?m=Api&c=Video&a=get_video_music_list")
    Observable<ResponseWrapper<JsonElement>> listMusicByCategoryId(
            @Query("category") String categoryId,
            @Query("page") Integer page,
            @Query("pagesize") Integer pagesize
    );

    @GET("?m=Api&c=Video&a=get_music_search_list")
    Observable<ResponseWrapper<JsonElement>> listSuggestionMusic(
            @Query("key_word") String keyWord,
            @Query("page") Integer page,
            @Query("pagesize") Integer pagesize
    );
}
