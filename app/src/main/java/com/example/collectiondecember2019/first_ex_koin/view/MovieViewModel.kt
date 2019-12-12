package com.example.collectiondecember2019.first_ex_koin.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collectiondecember2019.R
import com.example.collectiondecember2019.first_ex_koin.api.ServiceMovies
import com.example.collectiondecember2019.first_ex_koin.model.MovieCollection
import com.example.collectiondecember2019.first_ex_koin.util.Constants
import com.example.collectiondecember2019.first_ex_koin.util.Event
import kotlinx.coroutines.launch

/*Note: We don’t need to cancel the coroutine in the onCleared method of ViewModel because the ViewModel class automatically cancels all the inner coroutine child of viewModelScope.
Note: The viewmodel component instance created with viewmode{} automatically destroys from memory when activity or fragment is destroyed.
* */
class MovieViewModel(private val serviceUtil: ServiceMovies) : ViewModel() {
    private val _uiState = MutableLiveData<MovieDataState>()
    val uiState: MutableLiveData<MovieDataState> = _uiState

    init {
        retrieveMovies()
    }

    private fun retrieveMovies() {
        viewModelScope.launch {
            kotlin.runCatching {
                emitUiState(showProgress = true)
                serviceUtil.popularMovies(Constants.API_KEY)
            }.onSuccess {
                emitUiState(movies = Event(it))
            }.onFailure {
                Log.e("onFailure =", it.message.toString())
                emitUiState(error = Event(R.string.error))
            }
        }
    }

    private fun emitUiState(
        showProgress: Boolean = false,
        movies: Event<List<MovieCollection.Movie>>? = null,
        error: Event<Int>? = null
    ) {
        val dataState = MovieDataState(showProgress, movies, error)
        _uiState.value = dataState
    }
}

data class MovieDataState(
    val showProgress: Boolean,
    val movies: Event<List<MovieCollection.Movie>>?,
    val error: Event<Int>?
)