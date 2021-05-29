package fr.test200.findme

data class Itinerary (
    val id: Int = 1,
    val name: String,
    val duration: Float,
    var length: Float,
    var active: Boolean,
    val description: String
)