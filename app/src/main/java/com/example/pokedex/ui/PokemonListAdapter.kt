package com.example.pokedex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.databinding.PokemonListItemBinding
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource

class PokemonListAdapter : ListAdapter<NamedApiResource,PokemonViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = PokemonListItemBinding.inflate(LayoutInflater.from(parent.context))
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.bind(pokemon)
    }

    companion object DiffCallback: DiffUtil.ItemCallback<NamedApiResource>() {
        override fun areItemsTheSame(oldItem: NamedApiResource, newItem: NamedApiResource): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: NamedApiResource, newItem: NamedApiResource): Boolean {
            return oldItem.id == newItem.id
        }
    }

}

class PokemonViewHolder(private val pokemonListItemBinding: PokemonListItemBinding):
    RecyclerView.ViewHolder(pokemonListItemBinding.root) {

    private lateinit var pokemon: NamedApiResource

    fun bind(item: NamedApiResource) {
        this.pokemon = item
        pokemonListItemBinding.pokemon = item
        pokemonListItemBinding.executePendingBindings()
    }

}