package com.dongzhili.easylib

import androidx.room.*
import com.dongzhili.easylib.bean.ShortMusic

@Entity(
    tableName = "tb_search_music",
    foreignKeys = [
        ForeignKey(
            entity = ShortMusic::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("music_id"),
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("music_id", unique = true)
    ]
)
data class SearchMusic(
    @PrimaryKey(autoGenerate = true)
    val index: Int = 0,
    @ColumnInfo(name = "key_word")
    val keyWord: String = "",
    @ColumnInfo(name = "music_id")
    val musicId: String
)
