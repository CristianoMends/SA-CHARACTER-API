package com.api.characters.repository

import com.api.characters.entity.Character
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CharacterRepository:JpaRepository<Character,Long> {

    fun getCharacterById(id:Long):Character?

}