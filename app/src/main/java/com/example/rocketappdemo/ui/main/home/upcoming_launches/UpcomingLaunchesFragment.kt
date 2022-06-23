package com.example.rocketappdemo.ui.main.home.upcoming_launches

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rocketappdemo.ui.main.MainActivity
import com.example.rocketappdemo.R
import com.example.rocketappdemo.data.model.Output
import com.example.rocketappdemo.databinding.FragmentUpcomingLaunchesBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch


@InternalCoroutinesApi
@AndroidEntryPoint
class UpcomingLaunchesFragment : Fragment(), UpcomingLaunchesAdapter.Listener {
    private lateinit var binding: FragmentUpcomingLaunchesBinding
    private val upcomingLaunchesViewModel: UpcomingLaunchesViewModel by viewModels()
    val adapter: UpcomingLaunchesAdapter by lazy { UpcomingLaunchesAdapter(arrayListOf(), this) }
    val navBar: BottomNavigationView? by lazy { activity?.findViewById(R.id.bottomNavigationView) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpcomingLaunchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservers()
        loadData()
    }

    private fun initView() {
        binding.upcomingRecycler.adapter = adapter
        binding.upcomingRecycler.layoutManager = LinearLayoutManager(requireContext())
        navBar?.isVisible = true
    }

    private fun initObservers() {
        upcomingLaunchesViewModel.upcLivedata.observe(viewLifecycleOwner, {
            when (it.status) {
                Output.Status.SUCCESS -> {
                    it.data?.let { upc ->

                        adapter.setUpcList(upc)
                        setLayout(false)
                    }
                    (activity as MainActivity).hideLoading()
                }
                Output.Status.ERROR -> {
                    setLayout(true)

                }
                Output.Status.LOADING -> {
                    (activity as MainActivity).showLoading()

                }
            }
        })
    }

    private fun loadData() {
        lifecycleScope.launch {
            upcomingLaunchesViewModel.getLaunchs()
        }

    }


    private fun setLayout(listIsEmpty: Boolean) {
        binding.run {
            upcLayout.isVisible = !listIsEmpty
            noupcLayout.isVisible = listIsEmpty
        }
    }

    override fun onItemClick(idLaunch: String) {
        val action = UpcomingLaunchesFragmentDirections.toLaunchDetail(idLaunch)
        findNavController().navigate(action)
    }

}