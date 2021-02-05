package com.example.pokedex.data

import javax.inject.Inject

class PokemonRepository @Inject constructor(private val localDataSource: PokemonDao) {

    fun getAllPokemons() = localDataSource.getAllPokemons()

    suspend fun insertAll(pokemons: List<Pokemon>) = localDataSource.insertAll(pokemons)

    suspend fun clear() = localDataSource.clear()
}