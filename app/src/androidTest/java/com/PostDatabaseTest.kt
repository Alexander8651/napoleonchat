package com

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.napoleon.AppDatabase
import com.napoleon.data.model.PostSqlite
import com.napoleon.domain.PostDao
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PostDatabaseTest {

    private lateinit var postDao:PostDao
    private lateinit var db: AppDatabase

    @Before
    fun createDB(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.

        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()

        postDao = db.PostDao()

    }

    @After
    @Throws(IOException::class)
    fun closeDB(){
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetPost(){
        val post = PostSqlite()
        postDao.insert(post)

        val thispost = postDao.getthisPost()
        Assert.assertEquals(post?.userId, 0)
    }

}