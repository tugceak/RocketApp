package com.example.rocketappdemo.ui.main.home.rocket_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.rocketappdemo.ui.main.MainActivity
import com.example.rocketappdemo.R
import com.example.rocketappdemo.data.model.Output
import com.example.rocketappdemo.databinding.FragmentRocketDetailBinding
import com.example.rocketappdemo.utils.GlideUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jama.carouselview.enums.IndicatorAnimationType
import com.jama.carouselview.enums.OffsetType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@InternalCoroutinesApi
@AndroidEntryPoint
class RocketDetailFragment : Fragment() {
    private lateinit var binding: FragmentRocketDetailBinding
    private val rocketDetailViewModel: RocketDetailViewModel by viewModels()
    private val args: RocketDetailFragmentArgs by navArgs()
    val navBar: BottomNavigationView? by lazy { activity?.findViewById(R.id.bottomNavigationView) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRocketDetailBinding.inflate(inflater, container, false)
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
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun initObservers() {
        rocketDetailViewModel.rocketDetailLivedata.observe(viewLifecycleOwner, {
            when (it.status) {
                Output.Status.SUCCESS -> {
                    it.data?.let { rocketdetails ->
                        binding.run {
                            textName.text = rocketdetails.name
                            textDescription.text = rocketdetails.description
                            initCarousel(rocketdetails.flickr_images)
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

    private fun initCarousel(images: List<String>) {
        binding.carouselView.apply {
            size = images.size
            resource = R.layout.carousel_home_item
            autoPlay = true
            indicatorAnimationType = IndicatorAnimationType.THIN_WORM
            carouselOffset = OffsetType.CENTER
            setCarouselViewListener { view, position ->
                val imageView = view.findViewById<ImageView>(R.id.imgCarousel)
                var imgPath: String? = images[position]
                if (imgPath.isNullOrEmpty())
                    imgPath = ""
                GlideUtils.urlToImageView(requireContext(), imgPath, imageView)
            }
            show()
        }
    }

    private fun loadData() {
        lifecycleScope.launch {
            rocketDetailViewModel.getRocketDetails(args.id)
        }
    }
}

