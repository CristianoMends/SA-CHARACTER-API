package com.api.characters.dto

import com.api.characters.entity.Character
import com.api.characters.enums.Gender
import java.awt.Image
import java.io.File


data class CharacterView(
    val id: Long?,
    val name:String?,
    val OtherNames:String?,
    val gender:String?,
    val description:String?,
    val image: String?//for urls
) {
    constructor(character: Character):this(
        id = character.id,
        name = character.name,
        OtherNames = character.otherNames,
        gender = character.gender,
        description = character.description,
        image = character.image
    )
}


