package com.example.movieappdevexpert.data

class MoviesRepository(private val moviesService: MoviesService) {

    suspend fun fetchPopularMovies(): List<Movie> =
        moviesService
        .fetchPopularMoviesService("CL")
        .results
        .map { it.toDomainModel() }

    suspend fun findMovieById(id: Int): Movie =
        moviesService
        .fetchMovieByIdService(id)
        .toDomainModel()

}

private fun RemoteMovie.toDomainModel(): Movie =
    Movie(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        poster = "https://image.tmdb.org/t/p/w185$posterPath",
        backdrop = backdropPath?.let { "https://image.tmdb.org/t/p/w780/$it" }.toString(),
        originalTitle = originalTitle,
        originalLanguage = originalLanguage,
        popularity = popularity,
        voteAverage = voteAverage
    )
