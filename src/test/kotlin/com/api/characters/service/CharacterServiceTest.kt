package com.api.characters.service

import com.api.characters.entity.Character
import com.api.characters.repository.CharacterRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream

class CharacterServiceTest {

    lateinit var characterService: CharacterService
    @Mock
    lateinit var characterRepository: CharacterRepository
    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        characterService = CharacterService(characterRepository)
    }

    @Test
    fun testSave() {
    }

    // Adicione mais métodos de teste conforme necessário
}
