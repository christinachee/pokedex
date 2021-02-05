package com.example.pokedex.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.data.PokemonRepository
import com.example.pokedex.data.asPokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val pokeApi = PokeApiClient()

    val pokemonList = repository.getAllPokemons()

    private fun getPokemonList() {
        viewModelScope.launch(Dispatchers.IO) {
            val pokemonListResult = pokeApi.getPokemonList(0, 151)
            val pokemons = pokemonListResult.results

            repository.insertAll(pokemons.asPokemon())

        }
    }

    init {
        getPokemonList()
    }

}