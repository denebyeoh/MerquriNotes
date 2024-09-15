package com.example.merqurinotes.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val category: String= "",
)

@Entity(tableName = "content_table")
data class Content(
    @PrimaryKey(autoGenerate = true)
    val contentId: Int = 0,
    val category: String= "",
    val content: String = "",
    var unixTime: Long = 0
)
