package br.com.jeffersonmarques.restapi

import java.io.InputStream

class StreamUtils {
    fun inputStream(clazz: Class<Any>, fileName: String): InputStream {
        return clazz.classLoader.getResourceAsStream(fileName)
    }
}