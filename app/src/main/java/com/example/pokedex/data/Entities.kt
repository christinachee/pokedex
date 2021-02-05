package com.example.pokedex.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource


@Entity(tableName = "pokemons")
data class Pokemon(
    val name: String,
    val category: String,
    @PrimaryKey
    val id: Int
)

fun List<NamedApiResource>.asPokemon(): List<Pokemon> {
    return map {
        Pokemon(
            name = it.name,
            category = it.category,
            id = it.id
        )
    }
}