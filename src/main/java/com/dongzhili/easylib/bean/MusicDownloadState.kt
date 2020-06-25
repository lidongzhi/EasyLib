package com.dongzhili.easylib.bean

import androidx.room.TypeConverter

enum class MusicDownloadState {
    INIT,   //未下载
    DOWNLOADING,    //下载中
    SUCCESS,    //下载成功
    FAIL    //下载失败
}

class MusicDownloadStateTypeConverters {

    @TypeConverter
    fun fromMusicDownloadState(state: MusicDownloadState): Int {
        return state.ordinal
    }

    @TypeConverter
    fun toMusicDownloadState(value: Int): MusicDownloadState {
        return MusicDownloadState.values()[value]
    }
}