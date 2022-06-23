package com.example.rocketappdemo.ui.main.home.rockets

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rocketappdemo.ui.main.MainActivity
import com.example.rocketappdemo.R
import com.example.rocketappdemo.data.model.Output
import com.example.rocketappdemo.ui.main.home.favorite_rockets.FavRocketsViewModel
import com.example.rocketappdemo.data.room.FavoritedRockets
import com.example.rocketappdemo.databinding.FragmentRocketsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@InternalCoroutinesApi
@AndroidEntryPoint
class RocketsFragment : Fragment(), RocketsAdapter.Listener {

    private var favList = emptyList<FavoritedRockets>()
    private lateinit var favrocketViewModel: FavRocketsViewModel
    private val rocketsViewModel: RocketsViewModel by viewModels()
    private lateinit var binding: FragmentRocketsBinding
    val adapter: RocketsAdapter by lazy { RocketsAdapter(arrayListOf(), this) }
    val navBar: BottomNavigationView? by lazy { activity?.findViewById(R.id.bottomNavigationView) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRocketsBinding.inflate(inflater, container, false)
        favrocketViewModel = ViewModelProvider(this).get(FavRocketsViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservers()
        loadData()
    }

    private fun initView() {
        binding.rocketRecycler.adapter = adapter
        binding.rocketRecycler.layoutManager = LinearLayoutManager(requireContext())
        navBar?.isVisible = true
    }

    private fun initObservers() {
        favrocketViewModel.readAllData.observe(viewLifecycleOwner, Observer { rocket ->
            favList = rocket

        })
        rocketsViewModel.rocketsLivedata.observe(viewLifecycleOwner, {
            when (it.status) {
                Output.Status.SUCCESS -> {
                    it.data?.let { rockets ->
                        rockets.forEach { item ->

                            item.isFavorite = favList.any { favItem ->
                                item.id == favItem.id
                            }
                        }
                        adapter.setRocketList(rockets)
                        setLayout(listIsEmpty = false)
                        (activity as MainActivity).hideLoading()
                    }

                }
                Output.Status.ERROR -> {
                    setLayout(true)
                    (activity as MainActivity).hideLoading()
                }
                Output.Status.LOADING -> {
                    Log.v("Tag", "Veriler yükleniyor")
                    (activity as MainActivity).showLoading()
                }
            }
        })

        favrocketViewModel.favoriteStatus.observe(viewLifecycleOwner, {
            adapter.setItemFavoriteStatus(it.first, it.second)
        })
    }

    private fun loadData() {
        lifecycleScope.launch {
            rocketsViewModel.getRockets()
        }

    }

    override fun insertRoom(rocketItem: FavoritedRockets) {
        favrocketViewModel.addtoFav(rocketItem)
    }

    override fun deleteFromRoom(rocketId: String) {
        favrocketViewModel.deleteFromRoom(rocketId)
    }

    private fun setLayout(listIsEmpty: Boolean) {
        binding.run {
            rocketLayout.isVisible = !listIsEmpty
            noRocketLayout.isVisible = listIsEmpty
        }
    }

    override fun onItemClick(id: String) {
        val action = RocketsFragmentDirections.toRocketDetailFragment(id)
        findNavController().navigate(action)
        binding.tryAgainButton.setOnClickListener { loadData() }
    }


}
