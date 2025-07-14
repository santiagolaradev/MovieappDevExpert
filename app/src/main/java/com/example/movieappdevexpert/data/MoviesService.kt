package com.example.movieappdevexpert.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun fetchPopularMoviesService(
        @Query("region") region: String
    ): RemoteResult

    @GET("movie/{id}") //use usa @path ya que el argumento va entre llaves
    suspend fun fetchMovieByIdService(
        @Path("id") id: Int,
    ): RemoteMovie

}