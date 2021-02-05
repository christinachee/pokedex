package com.example.pokedex.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.PokemonListAdapter
import com.example.pokedex.R
import com.example.pokedex.databinding.PokemonListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonListFragment: Fragment() {

    private lateinit var binding: PokemonListFragmentBinding
    private val viewModel: PokemonListViewModel by viewModels()
    private lateinit var adapter: PokemonListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PokemonListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.clear_btn -> {
                Toast.makeText(context, "Clear btn", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.load_btn -> {
                Toast.makeText(context, "load btn", Toast.LENGTH_SHORT).show()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = PokemonListAdapter()
        binding.pokemonRv.layoutManager = LinearLayoutManager(requireContext())
        binding.pokemonRv.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.pokemonList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

}