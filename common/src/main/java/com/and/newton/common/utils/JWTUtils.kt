package com.and.newton.common.utils

import com.google.gson.annotations.SerializedName
import java.nio.charset.Charset

object JWTUtils {
    private const val tokenDelimiter = '.'

    fun < P : JWTAuthPayload> decode(
        jwtTokenString: String,
        jsonDecoder: JsonDecoder<P>,
        decoder: Base64Decoder,
        charset: Charset = Charsets.UTF_8
    ): JWTToken< P>? {
        val parts = jwtTokenString.split(tokenDelimiter)
        return if (parts.size >= 2) {

            val payloadJson = decoder.decode(parts[1].toByteArray(charset)).toString(charset)

            val payload: P = jsonDecoder.payloadFrom(payloadJson)

            JWTToken(payload)
        } else {
            null
        }
    }

}



interface JsonDecoder< P : JWTAuthPayload> {
    fun payloadFrom(json: String): P
}


interface Base64Decoder {
    /**
     * Base64 encodes the provided bytes.
     * @param bytes The ByteArray to be decoded.
     * @return The decoded bytes.
     */
    fun decode(bytes: ByteArray): ByteArray

    /**
     * Base64 encodes the provided String.
     * @param string The String to be decoded.
     * @return The decoded String as a ByteArray.
     */
    fun decode(string: String): ByteArray
}

/**
 * JWTToken representation with header and payload. Used for String token decoding.
 */
open class JWTToken< P : JWTAuthPayload>(
    val payload: P
)



/**
 * JWT authentication token payload.
 */
open class JWTAuthPayload(
    /** the issuer of the token (team id found in developer member center) */
    @field:SerializedName("http://schemas.microsoft.com/ws/2008/06/identity/claims/role")
    val role: String,
    /** token issued at timestamp in seconds since Epoch (UTC) */
    @field:SerializedName("exp")
    val expriryTime: Long
)