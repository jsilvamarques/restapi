package br.com.jeffersonmarques.restapi.error.exception

import java.io.Serializable

class ExistingEmailException(
        val errorCode: String = "ERROR-08"
) : RuntimeException(), Serializable