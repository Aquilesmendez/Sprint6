package com.example.sprint6

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("response") val response: String,
    @SerializedName("results") val results: List<CharacterResponse>
)