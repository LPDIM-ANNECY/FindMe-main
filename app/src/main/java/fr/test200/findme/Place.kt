package fr.test200.findme

enum class RadiusType(val value: Int) {
    Small(0), Normal(1), Large(2), Big(3)
}

data class Place (
    val id: Int,
    val name: String,
    val latitude: Float,
    var longitude: Float,
    var difficulty: Int,
    val radius_type: Int,
    val active: Boolean?,
    val user_id: Int?
)