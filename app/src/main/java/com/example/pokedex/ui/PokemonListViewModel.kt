package com.example.pokedex.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.data.PokemonRepository
import com.example.pokedex.data.asPokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val pokeApi = PokeApiClient()

    val pokemonList = repository.getAllPokemons()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun getPokemonList() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            val pokemonListResult = pokeApi.getPokemonList(0, 151)
            val pokemons = pokemonListResult.results
            delay(3000L)
            repository.insertAll(pokemons.asPokemon())
            _isLoading.postValue(false)
        }
    }

    fun clearPokemonList() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            repository.clear()
            delay(5000L)
            _isLoading.postValue(false)
        }
    }

}