@file:Suppress("UNCHECKED_CAST")

package com.redgyro.auth

import org.springframework.security.oauth2.provider.OAuth2Authentication
import java.security.Principal

fun Principal.getUserId(): String {
    val auth = this as OAuth2Authentication
    val userAuthentication = auth.userAuthentication
    val details = userAuthentication.details as LinkedHashMap<String, String>
    return details["sub"]!!
}