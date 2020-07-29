package com.and.newton.common.utils

import org.apache.commons.codec.binary.Base64

object Base64DecoderImpl: Base64Decoder {
    override fun decode(bytes: ByteArray): ByteArray {
        return Base64.decodeBase64(bytes)
    }

    override fun decode(string: String): ByteArray {
        return Base64.decodeBase64(string)
    }
}