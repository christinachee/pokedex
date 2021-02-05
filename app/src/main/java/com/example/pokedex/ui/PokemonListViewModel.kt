package com.example.pokedex.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor() : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val pokeApi = PokeApiClient()

    private val _pokemonList = MutableLiveData<List<NamedApiResource>>()
    val pokemonList: LiveData<List<NamedApiResource>>
        get() = _pokemonList

    private fun getPokemonList() {
        viewModelScope.launch(Dispatchers.IO) {
            val pokemonListResult = pokeApi.getPokemonList(0, 151)

            Timber.i(pokemonListResult.results.toString())

            _pokemonList.postValue(pokemonListResult.results)
        }
    }

    init {
        getPokemonList()
    }

}