package com.example.sprint6

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    private val heroeDao: HeroeDao

    val allHeroes: LiveData<List<Heroe>>

    init {
        val database = AppDatabase.getDatabase(application)
        heroeDao = database.heroeDao()
        allHeroes = heroeDao.getAllHeroes()
    }

    fun insertHeroe(heroe: Heroe) {
        viewModelScope.launch {
            heroeDao.insertHeroe(heroe)
        }
    }
}
