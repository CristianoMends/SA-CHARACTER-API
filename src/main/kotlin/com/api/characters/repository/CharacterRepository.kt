package com.api.characters.repository

import com.api.characters.entity.Character
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CharacterRepository:JpaRepository<Character,Long> {
    @Query(value = "select * from character where character_id = ?1", nativeQuery = true)
    fun findCharacterById(characterId:Long):Character

    @Query(value = "select * from Character", nativeQuery = true)
    fun findAllCharacters():List<Character>


}