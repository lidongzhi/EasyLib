package com.dongzhili.easylib.dao

import androidx.room.*
import com.dongzhili.easylib.bean.MusicCategory
import com.dongzhili.easylib.utils.LogUtils
import io.reactivex.Observable

@Dao
interface MusicCategoryDao {

    @Query("delete from tb_music_category")
    fun clearMusicCategory()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllMusicCategory(musicCategoryList: List<MusicCategory>)

    @Query("select * from tb_music_category")
    fun getAllMusicCategory(): Observable<List<MusicCategory>>

    @Transaction
    fun updateAllMusicCategory(categoryList: List<MusicCategory>): Int {
        clearMusicCategory()
        LogUtils.w("dongzhili", "MusicCategoryDao updateAllMusicCategory size:${categoryList.size}")
        saveAllMusicCategory(categoryList)
        return categoryList.size
    }
}