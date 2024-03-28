package com.api.characters.entity

import com.api.characters.enums.Gender
import jakarta.persistence.*

@Entity
@Table(name = "Character")
data class Character(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_id", nullable = false)
    var id: Long? = null,
    var name:String? = null,
    var OtherNames:String? = null,
    var gender:String? = Gender.UNKNOWN.toString(),
    var description:String? = null,
    var image: String? = null//for urls
)
