package com.example.imagesearchapplication.ui.gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.SearchEvent
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.imagesearchapplication.R
import com.example.imagesearchapplication.data.model.UnsplashPhoto
import com.example.imagesearchapplication.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery), UnsplashPhotoAdapter.OnPhotoClickListener {

    private val viewModel by viewModels<GalleryViewModel>()

    private var _binding : FragmentGalleryBinding? = null
    private val binding  get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGalleryBinding.bind(view)

        val adapter = UnsplashPhotoAdapter(this)

        binding.apply {
            rvGallery.setHasFixedSize(true)
            rvGallery.itemAnimator = null
            rvGallery.adapter = adapter.withLoadStateHeaderAndFooter(
                    header = UnsplashPhotoLoadStateAdapter {adapter.retry()},
                    footer = UnsplashPhotoLoadStateAdapter {adapter.retry()},
            )
            btnRetry.setOnClickListener {
                adapter.retry()
            }
        }

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                rvGallery.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                tvErrorLoading.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading &&
                        loadState.append.endOfPaginationReached &&
                        adapter.itemCount < 1) {
                    rvGallery.isVisible = false
                    tvEmptyResults.isVisible = true

                } else {
                    tvEmptyResults.isVisible = false

                }
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onPhotoClick(photo: UnsplashPhoto) {
        val action = GalleryFragmentDirections.actionGalleryFragment2ToDetailsFragment(photo)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_gallery, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    binding.rvGallery.scrollToPosition(0)
                    viewModel.searchPhotos(it)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}