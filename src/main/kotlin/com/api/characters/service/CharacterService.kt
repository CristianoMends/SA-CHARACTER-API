package com.api.characters.service

import com.api.characters.entity.Character
import com.api.characters.repository.CharacterRepository
import org.springframework.stereotype.Service

@Service
class CharacterService(
    private val characterRepository:CharacterRepository
):ICharacterService{
    override fun save(character: Character): Character {
        return characterRepository.save(character)
    }
    override fun findCharacterById(characterId: Long): Character {
        val character = characterRepository.findCharacterById(characterId)
        return character
    }
    override fun findAllCharacters(): List<Character> {
        val characters = characterRepository.findAllCharacters()
        return characters
    }
}