package com.example.submission2.data
import com.example.submission2.data.source.remote.response.TvResultsItem
import java.io.Serializable

data class TvShow(
    val id: Int,
    val title: String,
    val backdropPath: String?,
    val posterPath: String?,
    val overview: String?,
    val firstAirDate: String?,
    val voteAverage: Double?,
) : Serializable {
    constructor(tvResponse: TvResultsItem) : this(
        tvResponse.id,
        tvResponse.name,
        tvResponse.backdropPath,
        tvResponse.posterPath,
        tvResponse.overview,
        tvResponse.firstAirDate,
        tvResponse.voteAverage
    )
}
