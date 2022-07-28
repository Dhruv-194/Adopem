package com.dhruv.adopem.dataclassfiles

import com.google.firebase.database.Exclude

data class AdoptPetDataClass(
    var name :String?=null,
    var age :String?=null,
    var breed :String?=null,
    var ownername :String?=null,
    var hometown :String?=null,
    var petimageurl :String?=null,
    @get:Exclude
    @set:Exclude
    var key:String?=null,

)
