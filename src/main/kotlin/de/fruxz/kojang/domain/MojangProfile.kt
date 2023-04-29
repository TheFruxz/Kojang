package de.fruxz.kojang.domain


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MojangProfile(
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("textures")
    val textures: Textures,
    @SerialName("username")
    val username: String,
    @SerialName("username_history")
    val usernameHistory: List<UsernameHistory>,
    @SerialName("uuid")
    val uuid: String
) {
    @Serializable
    data class Textures(
        @SerialName("cape")
        val cape: Cape,
        @SerialName("custom")
        val custom: Boolean,
        @SerialName("raw")
        val raw: Raw,
        @SerialName("skin")
        val skin: Skin,
        @SerialName("slim")
        val slim: Boolean
    ) {
        @Serializable
        data class Cape(
            @SerialName("data")
            val `data`: String,
            @SerialName("url")
            val url: String
        )

        @Serializable
        data class Raw(
            @SerialName("signature")
            val signature: String,
            @SerialName("value")
            val value: String
        )

        @Serializable
        data class Skin(
            @SerialName("data")
            val `data`: String,
            @SerialName("url")
            val url: String
        )
    }

    @Serializable
    data class UsernameHistory(
        @SerialName("username")
        val username: String
    )
}