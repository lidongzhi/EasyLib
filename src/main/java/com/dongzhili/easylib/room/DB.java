package com.dongzhili.easylib.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.dongzhili.easylib.SearchMusic;
import com.dongzhili.easylib.bean.CategoryMusic;
import com.dongzhili.easylib.bean.MusicCategory;
import com.dongzhili.easylib.bean.MusicDownloadStateTypeConverters;
import com.dongzhili.easylib.bean.RecommendMusicRef;
import com.dongzhili.easylib.bean.ShortMusic;
import com.dongzhili.easylib.dao.MusicCategoryDao;
import com.dongzhili.easylib.dao.ShortMusicDao;

@Database(entities = {User.class, MusicCategory.class, ShortMusic.class,
        RecommendMusicRef.class, CategoryMusic.class, SearchMusic.class}, version = 1,exportSchema = false)
@TypeConverters(MusicDownloadStateTypeConverters.class)
public abstract class DB extends RoomDatabase {

    private static DB INSTANCE;
    private static final Object sLock = new Object();
    public abstract UserDao userDao();
    public abstract MusicCategoryDao musicCategoryDao();
    public abstract ShortMusicDao shortMusicDao();

    public static DB getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE =
                        Room.databaseBuilder(context.getApplicationContext(), DB.class, "easycode_db")
                                .build();
            }
            return INSTANCE;
        }
    }
}
