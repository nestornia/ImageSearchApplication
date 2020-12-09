package com.example.imagesearchapplication.ui.gallery

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.imagesearchapplication.data.repository.UnsplashRepository

class GalleryViewModel @ViewModelInject constructor(
    private val repository: UnsplashRepository
) : ViewModel() {
    
}