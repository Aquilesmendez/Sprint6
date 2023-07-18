package com.example.sprint6

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HeroeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeroe(heroe: Heroe)

    @Query("SELECT * FROM heroes")
    fun getAllHeroes(): LiveData<List<Heroe>>
}
