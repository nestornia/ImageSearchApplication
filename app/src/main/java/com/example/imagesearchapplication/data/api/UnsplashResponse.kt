package com.example.imagesearchapplication.data.api

import com.example.imagesearchapplication.data.model.UnsplashPhoto

data class UnsplashResponse(
    val results: List<UnsplashPhoto>
)