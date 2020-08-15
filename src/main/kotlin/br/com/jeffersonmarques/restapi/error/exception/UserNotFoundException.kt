package br.com.jeffersonmarques.restapi.error.exception

import java.io.Serializable

class UserNotFoundException(
        val errorCode: String = "ERROR-07"
) : RuntimeException(), Serializable