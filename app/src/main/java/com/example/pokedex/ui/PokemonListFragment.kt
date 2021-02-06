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
import timber.log.Timber

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
    ): View {
        binding = PokemonListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.clear_btn -> {
                viewModel.clearPokemonList()
                true
            }
            R.id.load_btn -> {
                viewModel.getPokemonList()
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
        viewModel.pokemonList.observe(viewLifecycleOwner, {
            when(it.size) {
                0 -> {
                    binding.emptyStatus.visibility = View.VISIBLE
                    adapter.submitList(it)
                }
                else -> {
                    binding.emptyStatus.visibility = View.GONE
                    adapter.submitList(it)
                }
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, {
            when (it) {
                true -> {
                    binding.loadingAnimation.visibility = View.VISIBLE
                }
                false -> {
                    binding.loadingAnimation.visibility = View.GONE
                }
            }
        })
    }

}