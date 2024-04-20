package com.api.characters.controller

import com.api.characters.dto.CharacterDto
import com.api.characters.dto.CharacterView
import com.api.characters.entity.Character
import com.api.characters.service.CharacterService
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@RestController
@RequestMapping("/character")
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

    @PostMapping("/")
    fun createCharacter(
        @RequestParam("name") name: String,
        @RequestParam("otherNames") otherNames: String,
        @RequestParam("gender") gender: String,
        @RequestParam("description") description: String,
        @RequestParam("image") image: MultipartFile
    ): ResponseEntity<CharacterView> {

        val characterDto:CharacterDto = CharacterDto(name,otherNames,gender,description)
        val savedCharacter = characterService.save(characterDto.toEntity(), image)

        return ResponseEntity.status(HttpStatus.CREATED).body(CharacterView(savedCharacter))
    }

    @GetMapping("/image/{imageName}")
    fun getImage(@PathVariable imageName: String): ResponseEntity<Resource> {
        val resource: Resource = characterService.loadImage(imageName)

        return if (resource.exists() && resource.isReadable) {

            ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .body(resource)
        } else {
            ResponseEntity.notFound().build()
        }
    }



}
