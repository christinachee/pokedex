package com.example.pokedex.ui

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

    fun getPokemonList() {
        viewModelScope.launch(Dispatchers.IO) {
            val pokemonListResult = pokeApi.getPokemonList(0, 151)
            val pokemons = pokemonListResult.results

            repository.insertAll(pokemons.asPokemon())

        }
    }

    fun clearPokemonList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clear()
        }
    }

}