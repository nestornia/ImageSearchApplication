package com.example.imagesearchapplication.data.repository

import com.example.imagesearchapplication.data.api.UnsplashApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepository @Inject constructor(private val unsplashApi: UnsplashApi) {
}