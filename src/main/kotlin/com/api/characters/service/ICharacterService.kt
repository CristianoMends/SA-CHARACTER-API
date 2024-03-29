package com.api.characters.service

import com.api.characters.entity.Character
import com.api.characters.repository.CharacterRepository

interface ICharacterService {
    fun save(character: Character):Character
    fun findCharacterById(characterId:Long):Character
    fun findAllCharacters():List<Character>?

}