package com.api.characters.controller

import com.api.characters.dto.CharacterDto
import com.api.characters.dto.CharacterView
import com.api.characters.service.CharacterService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

@RestController
@RequestMapping("/api/characters")
class CharacterController(
    val characterService: CharacterService
){
    @GetMapping("/{characterId}")
    fun getCharacterById(@PathVariable characterId: Long): ResponseEntity<CharacterView> {
        val character = characterService.findCharacterById(characterId)
        return ResponseEntity.status(HttpStatus.OK).body(CharacterView(character))
    }

    @GetMapping("/all")
    fun getCharacterById(): ResponseEntity<List<CharacterView>> {
        val characters = characterService.findAllCharacters()
        val charactersViews = mutableListOf<CharacterView>()

        for (c in characters){
            charactersViews.add(CharacterView(c))
        }
        return ResponseEntity.status(HttpStatus.OK).body(charactersViews)
    }

    @PostMapping("/save")
    fun createCharacter(
        @RequestParam("name") name: String,
        @RequestParam("otherNames") otherNames: String,
        @RequestParam("gender") gender: String,
        @RequestParam("description") description: String,
        @RequestParam("image") image: MultipartFile
    ): ResponseEntity<CharacterView> {
        val imagesDir = Paths.get("src/main/resources/static/images")

        if (!Files.exists(imagesDir)) {
            try {
                Files.createDirectories(imagesDir)
            } catch (e: IOException) {
                e.printStackTrace()
                println("Falha ao criar diretório de upload.")
            }
        }

        val characterName = name.replace("\\s".toRegex(), "_")
        val filePath = imagesDir.resolve("$characterName.jpg")

        try {
            Files.copy(image.inputStream, filePath)
        } catch (e: IOException) {
            e.printStackTrace()
            throw Exception("Fail to salve image.")
        }

        val imagePath = "images/$characterName.jpg"

        val characterDto:CharacterDto = CharacterDto(name,otherNames,gender,description,imagePath)
        val savedCharacter = characterService.save(characterDto.toEntity())

        return ResponseEntity.status(HttpStatus.CREATED).body(CharacterView(savedCharacter))
    }


}
