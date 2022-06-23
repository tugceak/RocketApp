package com.example.rocketappdemo.ui.main.home.launch_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.rocketappdemo.ui.main.MainActivity
import com.example.rocketappdemo.R
import com.example.rocketappdemo.data.model.Output
import com.example.rocketappdemo.databinding.FragmentLaunchDetailBinding
import com.example.rocketappdemo.utils.GlideUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch


@InternalCoroutinesApi
@AndroidEntryPoint
class LaunchDetailFragment : Fragment() {
    private lateinit var binding: FragmentLaunchDetailBinding
    private val launchDetailViewModel: LaunchDetailViewModel by viewModels()
    private val args: LaunchDetailFragmentArgs by navArgs()
    private val navBar: BottomNavigationView? by lazy { activity?.findViewById(R.id.bottomNavigationView) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLaunchDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initBackClick()
        initObservers()
        loadData()
    }

    private fun initView() {
        navBar?.isVisible = false
    }

    private fun initBackClick() {
        binding.btnBack2.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initObservers() {
        launchDetailViewModel.launchDetailLivedata.observe(viewLifecycleOwner, {
            when (it.status) {
                Output.Status.SUCCESS -> {
                    it.data?.let { launchDetails ->
                        binding.run {
                            nameupc.text = launchDetails.name
                            flightNumbertw.text = launchDetails.flight_number.toString()
                            date.text = launchDetails.date_utc
                            launchDetails.links.patch.small?.let { url ->
                                GlideUtils.urlToImageView(
                                    requireContext(),
                                    url, imageView
                                )
                            }
                        }

                    }
                    (activity as MainActivity).hideLoading()
                }
                Output.Status.ERROR -> {
                    (activity as MainActivity).hideLoading()
                }
                Output.Status.LOADING -> {
                    (activity as MainActivity).showLoading()
                }
            }
        })

    }

    private fun loadData() {
        lifecycleScope.launch {
            launchDetailViewModel.getLaunchDetails(args.idLaunch)
        }

    }

}