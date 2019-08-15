package io.legado.app.data.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.legado.app.data.entities.SearchBook
import io.legado.app.data.entities.SearchShow

@Dao
interface SearchBookDao {

    @Query("SELECT * FROM searchBooks")
    fun observeAll(): DataSource.Factory<Int, SearchBook>

    @Query("SELECT * FROM searchBooks where time >= :time")
    fun observeNew(time: Long): DataSource.Factory<Int, SearchBook>

    @Query("SELECT name, author, min(time) time, max(kind) kind, max(coverUrl) coverUrl, max(intro) intro, max(wordCount) wordCount, max(latestChapterTitle) latestChapterTitle, count(origin) originCount FROM searchBooks where time >= :time group by name, author")
    fun observeShow(time: Long): DataSource.Factory<Int, SearchShow>

    @Query("select * from searchBooks where bookUrl = :bookUrl")
    fun getSearchBook(bookUrl: String): SearchBook?

    @Query("select * from searchBooks where name = :name and author = :author order by originOrder limit 1")
    fun getFirstByNameAuthor(name: String, author: String): SearchBook?

    @Query("select * from searchBooks where name = :name and author = :author order by originOrder")
    fun getByNameAuthor(name: String, author: String): List<SearchBook>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg searchBook: SearchBook): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(searchBook: SearchBook): Long
}