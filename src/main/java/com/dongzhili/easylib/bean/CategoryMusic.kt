package com.dongzhili.easylib.bean

import androidx.room.*
import com.dongzhili.easylib.bean.ShortMusic

@Entity(
        tableName = "tb_category_music",
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
data class CategoryMusic(
        @PrimaryKey(autoGenerate = true)
        val index: Int = 0,
        @ColumnInfo(name = "category_id")
        val categoryId: String = "",
        @ColumnInfo(name = "music_id")
        val musicId: String
)
