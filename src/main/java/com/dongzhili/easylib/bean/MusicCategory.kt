package com.dongzhili.easylib.bean;

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tb_music_category")
data class MusicCategory(
        @PrimaryKey @SerializedName("id") var id: Int,
        @ColumnInfo @SerializedName("name") var name: String,
        @ColumnInfo @SerializedName("cover") var cover: String
)