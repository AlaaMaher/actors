package com.jetpackcompose.actors.repository

import com.jetpackcompose.actors.data.CharacterAPI
import com.jetpackcompose.actors.model.Character

class CharacterRepo(private val characterAPI: CharacterAPI) {

    suspend fun getCharacters(): List<Character> {
        return characterAPI.getCharacters()
    }
}