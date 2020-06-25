package com.dongzhili.easylib.bean

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "tb_short_music")
@Parcelize
data class ShortMusic constructor(
        @PrimaryKey @SerializedName("id") var id: String = "",
        @ColumnInfo @SerializedName("song_name") var songName: String = "",
        @ColumnInfo @SerializedName("song_time") var songTime: Int = 0,
        @ColumnInfo @SerializedName("cover") var cover: String = "",
        @ColumnInfo @SerializedName("song_url") var songUrl: String = "",
        @ColumnInfo @SerializedName("nick_name") var nickName: String = "",
        @ColumnInfo @SerializedName("download_state") var downloadState: MusicDownloadState = MusicDownloadState.INIT,
        @ColumnInfo @SerializedName("category_id") var categoryId: String = ""
) : Parcelable