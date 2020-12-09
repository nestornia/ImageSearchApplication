package com.example.imagesearchapplication.data.api

import com.example.imagesearchapplication.model.UnsplashPhoto

data class UnsplashResponse(
    val results: List<UnsplashPhoto>
)