package com.example.merqurinotes.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Query("SELECT * FROM category_table")
    suspend fun getAllCategory(): List<Category>

    @Query("SELECT * FROM category_table WHERE id = :categoryId LIMIT 1")
    suspend fun getUserById(categoryId: Int): Category?
}

@Dao
interface ContentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContent(content: Content)

    @Query("SELECT * FROM content_table")
    suspend fun getAllContent(): List<Content>

    @Query("DELETE FROM content_table")
    suspend fun deleteAllContent()
}
