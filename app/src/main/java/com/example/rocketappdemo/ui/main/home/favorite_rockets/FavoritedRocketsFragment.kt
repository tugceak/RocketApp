package com.example.rocketappdemo.ui.main.home.favorite_rockets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rocketappdemo.R
import com.example.rocketappdemo.data.room.FavoritedRockets
import com.example.rocketappdemo.databinding.FragmentFavoritedRocketsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@AndroidEntryPoint
class FavoritedRocketsFragment : Fragment(), FavoritedRocketsAdapter.Listener {

    private lateinit var binding: FragmentFavoritedRocketsBinding
    val adapter: FavoritedRocketsAdapter by lazy { FavoritedRocketsAdapter(this) }
    private lateinit var favrocketViewModel: FavRocketsViewModel
    val navBar: BottomNavigationView? by lazy { activity?.findViewById(R.id.bottomNavigationView) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritedRocketsBinding.inflate(inflater, container, false)
        favrocketViewModel = ViewModelProvider(this).get(FavRocketsViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservers()
    }

    private fun initView() {
        binding.facoritedRecycler.adapter = adapter
        binding.facoritedRecycler.layoutManager = LinearLayoutManager(requireContext())
        navBar?.isVisible = true
    }

    private fun initObservers() {
        favrocketViewModel.readAllData.observe(viewLifecycleOwner, Observer { rocket ->
            adapter.setData(rocket)
            val favListStatus = rocket.isEmpty()
            setLayout(favListStatus)
        })
        favrocketViewModel.favoriteStatus.observe(viewLifecycleOwner, {
            adapter.setItemFavoriteStatus(it.first, it.second)
        })
    }

    override fun insertRoom(rocketItem: FavoritedRockets) {
        favrocketViewModel.addtoFav(rocketItem)
    }

    override fun deleteFromRoom(id: String) {
        favrocketViewModel.deleteFromRoom(id)
    }

    private fun setLayout(status: Boolean) {
        if (status) {
            binding.FavoritesFullLayout.visibility = View.GONE
            binding.noFavoriteLayout.visibility = View.VISIBLE
        } else {
            binding.noFavoriteLayout.visibility = View.GONE
            binding.FavoritesFullLayout.visibility = View.VISIBLE
        }
    }

    override fun onItemClick(id: String) {
        val action = FavoritedRocketsFragmentDirections.favToDetail(id)
        findNavController().navigate(action)
    }
}