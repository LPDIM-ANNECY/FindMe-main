package fr.test200.findme.dataClass

enum class RadiusType(val value: Int) {
    Small(0), Normal(1), Large(2), Big(3)
}

data class Place (
    val id: Int,
    val name: String,
    val latitude: Double,
    var longitude: Double,
    var difficulty: Int,
    val radius_type: Int,
    val active: Boolean?,
    val description: String?,
    val user_id: Int?,
    val visited: Boolean?,
    val category_id: Int?,
    val picture: String?
)