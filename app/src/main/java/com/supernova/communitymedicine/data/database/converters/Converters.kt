package com.supernova.communitymedicine.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: String?): List<String>? {
        if (value == null) return null
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun toStringList(list: List<String>?): String? {
        if (list == null) return null
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromLongList(value: String?): List<Long>? {
        if (value == null) return null
        val type = object : TypeToken<List<Long>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun toLongList(list: List<Long>?): String? {
        if (list == null) return null
        return gson.toJson(list)
    }


}
