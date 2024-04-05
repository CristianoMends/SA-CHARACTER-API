package com.api.characters.service

import com.api.characters.entity.Character
import com.api.characters.repository.CharacterRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

@Service
class CharacterService(
    private val characterRepository:CharacterRepository
):ICharacterService{
    override fun save(character: Character, image:MultipartFile): Character {

        val imagesDir = Paths.get("uploads/images")

        if (!Files.exists(imagesDir)) {
            try {
                Files.createDirectories(imagesDir)
            } catch (e: IOException) {
                e.printStackTrace()
                throw Exception("Failed to create upload directory!")
            }
        }

        val characterName = character.name?.replace("\\s".toRegex(), "_")
        val filePath = imagesDir.resolve("$characterName.jpg")

        try {
            Files.copy(image.inputStream, filePath)
        } catch (e: IOException) {
            e.printStackTrace()
            throw Exception("failed to save image!")
        }

        val imagePath = "/uploads/images/$characterName.jpg"
        character.image = imagePath

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