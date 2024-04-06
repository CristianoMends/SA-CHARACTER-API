package com.api.characters.service

import com.api.characters.entity.Character
import com.api.characters.repository.CharacterRepository
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
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

    private val serverIpAddress = InetAddress.getLocalHost().hostAddress
    private val uploadDirectory = "uploads/images"

    override fun save(character: Character, image:MultipartFile): Character {

        val imagesDir = Paths.get(uploadDirectory)

        if (!Files.exists(imagesDir)) {
            try {
                Files.createDirectories(imagesDir)
            } catch (e: IOException) {
                e.printStackTrace()
                throw Exception("Failed to create upload directory!")
            }
        }

        val fileName = character.name?.replace("\\s".toRegex(), "_")?.toLowerCase()
        val filePath = imagesDir.resolve("$fileName.jpg")

        try {
            Files.copy(image.inputStream, filePath)
        } catch (e: IOException) {
            e.printStackTrace()
            throw Exception("failed to save image!")
        }

        val imageLink = "http://localhost:8080/api/character/image/$fileName.jpg"
        character.image = imageLink

        return characterRepository.save(character)
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