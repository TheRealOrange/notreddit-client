package com.therealorange.notreddit.util

import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import kotlin.experimental.and
import kotlin.experimental.or

object UUIDv5 {
    private val gen: RandomString = RandomString()

    @Throws(UnsupportedEncodingException::class)
    fun generateType5UUID(namespace: String, name: String): UUID {
        val source = namespace + name
        val bytes = source.toByteArray(charset("UTF-8"))
        return type5UUIDFromBytes(bytes)
    }

    fun randomUUID(): UUID {
        return generateType5UUID(gen.nextString(), gen.nextString())
    }

    private fun type5UUIDFromBytes(name: ByteArray?): UUID {
        val md: MessageDigest
        md = try {
            MessageDigest.getInstance("SHA-1")
        } catch (nsae: NoSuchAlgorithmException) {
            throw InternalError("SHA-1 not supported", nsae)
        }
        val bytes: ByteArray = Arrays.copyOfRange(md.digest(name!!), 0, 16)
        bytes[6] = bytes[6] and 0x0f.toByte() /* clear version        */
        bytes[6] = bytes[6] or 0x50.toByte() /* set to version 5     */
        bytes[8] = bytes[8] and 0x3f.toByte() /* clear variant        */
        bytes[8] = bytes[8] or 0x80.toByte() /* set to IETF variant  */
        return constructType5UUID(bytes)
    }

    private fun constructType5UUID(data: ByteArray): UUID {
        var msb: Long = 0
        var lsb: Long = 0
        assert(data.size == 16) { "data must be 16 bytes in length" }
        for (i in 0..7) msb = msb shl 8 or ((data[i] and 0xff.toByte()).toLong())
        for (i in 8..15) lsb = lsb shl 8 or ((data[i] and 0xff.toByte()).toLong())
        return UUID(msb, lsb)
    }
}