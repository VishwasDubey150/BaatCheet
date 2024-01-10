package com.example.baat_cheet.data

data class UserData(
    var userId:String?="",
    var name:String?="",
    var number:String?="",
    var imageurl:String?="",
)
{
    fun toMap()= mapOf(
        "userId" to userId,
        "name" to name,
        "number" to number,
        "imageUrl" to imageurl
    )
}