package com.example.imagesearchapplication.ui.gallery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.imagesearchapplication.R
import com.example.imagesearchapplication.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private val viewModel by viewModels<GalleryViewModel>()

    private var _binding : FragmentGalleryBinding? = null
    private val binding  get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGalleryBinding.bind(view)

        val adapter = UnsplashPhotoAdapter()

        binding.apply {
            rvGallery.setHasFixedSize(true)
            rvGallery.adapter = adapter.withLoadStateHeaderAndFooter(
                    header = UnsplashPhotoLoadStateAdapter {adapter.retry()},
                    footer = UnsplashPhotoLoadStateAdapter {adapter.retry()},
            )
        }

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}