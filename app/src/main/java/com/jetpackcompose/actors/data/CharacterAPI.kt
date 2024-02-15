package com.jetpackcompose.actors.data

import com.jetpackcompose.actors.model.Character
import retrofit2.http.GET

interface CharacterAPI {

    @GET("characters")
    suspend fun getCharacters():List<Character>
}