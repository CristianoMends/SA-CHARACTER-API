package com.api.characters.service

import com.api.characters.entity.Character
import com.api.characters.repository.CharacterRepository
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.net.InetAddress
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Paths
@Service
class CharacterService(
    private val characterRepository:CharacterRepository
):ICharacterService{

    private val uploadDirectory = "uploads/images"

    @Transactional(rollbackFor = [IOException::class])
    override fun save(character: Character, image: MultipartFile): Character {
        val imagesDir = Paths.get(uploadDirectory)

        if (!Files.exists(imagesDir)) {
            Files.createDirectories(imagesDir)
        }

        val fileName = character.name?.replace("\\s".toRegex(), "_")?.toLowerCase() + ".jpg"
        val filePath = imagesDir.resolve(fileName)

        if (Files.exists(filePath)) {
            throw Exception("Image with the same name already exists!")
        }

        try {
            Files.copy(image.inputStream, filePath)
        } catch (e: IOException) {
            throw IOException("Failed to save image", e)
        }

        val imageLink = "http://localhost:8080/api/character/image/$fileName"
        character.image = imageLink

        try {
            return characterRepository.save(character)
        } catch (e: Exception) {
            throw RuntimeException("Failed to save character", e)
        }
    }
    fun loadImage(imageName: String): Resource {
        try {
            val imagePath = Paths.get(uploadDirectory).resolve(imageName)
            return UrlResource(imagePath.toUri())
        } catch (e: MalformedURLException) {

            throw RuntimeException("Error loading image: $imageName", e)
        }
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