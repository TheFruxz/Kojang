package dev.fruxz.kojang

import dev.fruxz.kojang.domain.MojangProfile
import dev.fruxz.ascend.extension.tryOrNull
import dev.fruxz.ascend.json.globalJson
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import java.util.*

/**
 * This class helps to get information about players/users from Mojang.
 * This utilizes the [Mojang API from Electroid](https://github.com/Electroid/mojang-api).
 * @author Fruxz
 * @since 1.0-RC
 */
@Suppress("MemberVisibilityCanBePrivate")
public object Kojang {

    @PublishedApi
    internal const val MOJANG_API: String = "https://api.ashcon.app/mojang/v2/user/%s"

    public suspend fun getMojangUser(
        player: String,
        maxAttempts: Int = 3,
        json: Json = globalJson,
        httpClient: HttpClient = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(globalJson)
            }
        }
    ): JsonElement? {

        var attempt = 1
        var response = httpClient.request(MOJANG_API.format(player))
        var formatted = tryOrNull { json.parseToJsonElement(response.body()) }

        while ((attempt <= maxAttempts) && (!response.status.isSuccess() || formatted == null)) {
            attempt++
            response = httpClient.request(MOJANG_API.format(player))
            formatted = tryOrNull { json.parseToJsonElement(response.body()) }
        }

        return formatted
    }

    public suspend fun getMojangUser(
        player: UUID,
        maxAttempts: Int = 3,
        json: Json = globalJson,
        httpClient: HttpClient = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(globalJson)
            }
        }
    ): JsonElement? = getMojangUser(player.toString(), maxAttempts, json, httpClient)

    public suspend fun getMojangUserProfile(
        player: String,
        maxAttempts: Int = 3,
        json: Json = globalJson,
        httpClient: HttpClient = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(globalJson)
            }
        }
    ): MojangProfile? = getMojangUser(player, maxAttempts, json, httpClient)?.let { json.decodeFromJsonElement<MojangProfile>(it) }

    public suspend fun getMojangUserProfile(
        player: UUID,
        maxAttempts: Int = 3,
        json: Json = globalJson,
        httpClient: HttpClient = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(globalJson)
            }
        }
    ): MojangProfile? = getMojangUser(player, maxAttempts, json, httpClient)?.let { json.decodeFromJsonElement<MojangProfile>(it) }

}