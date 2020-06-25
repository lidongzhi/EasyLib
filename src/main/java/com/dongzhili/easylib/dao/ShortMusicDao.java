package com.dongzhili.easylib.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.dongzhili.easylib.SearchMusic;
import com.dongzhili.easylib.bean.CategoryMusic;
import com.dongzhili.easylib.bean.RecommendMusicRef;
import com.dongzhili.easylib.bean.ShortMusic;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

@Dao
public interface ShortMusicDao {

//    @Query("select * from tb_short_music")
//    Observable<List<ShortMusic>> getAllShortMusic();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void saveAllShortMusic(List<ShortMusic> shortMusicList);


    ///////推荐音乐/////////////////
    @Query("SELECT * from tb_short_music INNER JOIN tb_recommend_music ON tb_short_music.id=tb_recommend_music.music_id ORDER BY `index` ASC")
    Observable<List<ShortMusic>> getAllRecommendMusic();

    @Query("DELETE FROM tb_recommend_music")
    public void clearRecommendMusicRefList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void saveRecommendMusicRefList(List<RecommendMusicRef> recommendList);

    @Transaction
    public default void saveRecommendMusicList(List<ShortMusic> musicList) {
//        keepDownLoadStateAndSaveShortMusicList(musicList)
        saveAllShortMusic(musicList);
        ArrayList<RecommendMusicRef> recommendList = new ArrayList<>();
        musicList.forEach(shortMusic -> recommendList.add(new RecommendMusicRef(0, shortMusic.getId())));
        saveRecommendMusicRefList(recommendList);
    }

    @Transaction
    public default void updateAllRecommendMusicList(List<ShortMusic> recommendList) {
        clearRecommendMusicRefList();
        saveRecommendMusicList(recommendList);
    }

    ///////分类音乐/////////////////
    @Query("SELECT * from tb_short_music INNER JOIN tb_category_music ON tb_short_music.id=tb_category_music.music_id WHERE category_id = :categoryId ORDER BY `index` ASC")
    Observable<List<ShortMusic>> getAllCategoryMusic(String categoryId);

    @Query("DELETE FROM tb_category_music")
    void clearCategoryMusicRefList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveCategoryMusicRefList(List<CategoryMusic> categoryMusicList);

    @Transaction
    default void saveCategoryMusicList(String categoryId,List<ShortMusic> categoryMusicList) {
//        keepDownLoadStateAndSaveShortMusicList(musicList)
        saveAllShortMusic(categoryMusicList);
        ArrayList<CategoryMusic> categoryList = new ArrayList<>();
        categoryMusicList.forEach(shortMusic -> categoryList.add(new CategoryMusic(0, categoryId,shortMusic.getId())));
        saveCategoryMusicRefList(categoryList);
    }

    @Transaction
    default void updateAllCategoryMusicList(String categoryId,List<ShortMusic> categoryMusicList) {
        clearCategoryMusicRefList();
        saveCategoryMusicList(categoryId,categoryMusicList);
    }

    ///////搜索音乐/////////////////
    @Query("SELECT * from tb_short_music INNER JOIN tb_search_music ON tb_short_music.id=tb_search_music.music_id  WHERE key_word = :searchKey ORDER BY `index` ASC")
    Observable<List<ShortMusic>> getAllSearchMusic(String searchKey);

    @Query("DELETE FROM tb_search_music")
    void clearSearchMusicRefList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveSearchMusicRefList(List<SearchMusic> categoryMusicList);

    @Transaction
    default void saveSearchMusicList(String keyword, List<ShortMusic> searchMusicList) {
//        keepDownLoadStateAndSaveShortMusicList(musicList)
        saveAllShortMusic(searchMusicList);
        ArrayList<SearchMusic> searchList = new ArrayList<>();
        searchMusicList.forEach(shortMusic -> searchList.add(new SearchMusic(0, keyword, shortMusic.getId())));
        saveSearchMusicRefList(searchList);
    }

    @Transaction
    default void updateAllSearchMusicList(String keyword, List<ShortMusic> searchMusicList) {
        clearSearchMusicRefList();
        saveSearchMusicList(keyword, searchMusicList);
    }
}
