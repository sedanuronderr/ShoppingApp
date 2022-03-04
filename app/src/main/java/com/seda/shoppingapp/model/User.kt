package com.seda.shoppingapp.model

import java.io.Serializable

 data class User (
    val id :String = "",
    val firstName:String = "",
    val lastName :String = "",
    val email : String = "",
    val image: String  = "",
    val mobile: String  = "",
    val gender:String  = "",
    val profileCompleted:Int  = 0

        )