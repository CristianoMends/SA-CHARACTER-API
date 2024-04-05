package com.api.characters.service

import com.api.characters.entity.Character
import com.api.characters.repository.CharacterRepository
import org.springframework.web.multipart.MultipartFile

interface ICharacterService {
    fun save(character: Character,image: MultipartFile):Character
    fun findCharacterById(characterId:Long):Character
    fun findAllCharacters():List<Character>?

}