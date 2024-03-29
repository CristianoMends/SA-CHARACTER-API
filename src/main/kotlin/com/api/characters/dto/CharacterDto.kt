package com.api.characters.dto

import com.api.characters.entity.Character
import com.api.characters.enums.Gender
import jakarta.validation.constraints.NotEmpty
import org.apache.tomcat.util.http.fileupload.FileUpload
import java.io.File

data class CharacterDto(
    @field:NotEmpty(message = "invalid input") val name:String?,
    @field:NotEmpty(message = "invalid input") val otherNames:String?,
    @field:NotEmpty(message = "invalid input") val gender:String?,
    @field:NotEmpty(message = "invalid input") val description:String?,
    @field:NotEmpty(message = "invalid input") val image:String?
){
    fun toEntity(): Character {
        return Character(
            name = this.name,
            otherNames = this.otherNames,
            gender = this.gender,
            description = this.description,
            image = this.image
        )
    }
}