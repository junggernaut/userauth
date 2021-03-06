package com.gomz.gomzuserservice.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import java.util.function.Function


@Component
class JWTUtils(
    @Value("\${jwt.secret}") private val secretKey: String,
    @Value("\${jwt.expiration}") private val expiration: Long
) {

    companion object {
        const val AUDIENCE_MOBILE = "mobile"
    }

    fun getUsernameFromToken(token: String): String? {
        return getClaimFromToken(token, Function { obj: Claims -> obj.subject })
    }

    fun getIssuedAtDateFromToken(token: String): Date {
        return getClaimFromToken(token, Function { obj: Claims -> obj.issuedAt })
    }

    fun getExpirationDateFromToken(token: String): Date {
        return getClaimFromToken(token, Function { obj: Claims -> obj.expiration })
    }

    fun getAudienceFromToken(token: String): String? {
        return getClaimFromToken(token, Function { obj: Claims -> obj.audience })
    }

    fun <T> getClaimFromToken(token: String, claimsResolver: Function<Claims, T>): T {
        val claims: Claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    private fun getAllClaimsFromToken(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(secretKey.toByteArray()))
            .build()
            .parseClaimsJws(token)
            .body
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    private fun generateAudience(): String { return AUDIENCE_MOBILE }


    private fun ignoreTokenExpiration(token: String): Boolean {
        val audience = getAudienceFromToken(token)
        return AUDIENCE_MOBILE == audience
    }

    fun generateToken(userDetails: UserDetails): String {
        val claims = mutableMapOf<String, Any>()
        claims["authority"] = userDetails.authorities
        return doGenerateToken(claims, userDetails.username, generateAudience())
    }

    private fun doGenerateToken(claims: Map<String, Any>, subject: String, audience: String): String {
        val now = Date()
        val expirationDate = calculateExpirationDate(now)
        val key: Key = Keys.hmacShaKeyFor(secretKey.toByteArray())
        println("doGenerateToken $now")
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setAudience(audience)
            .setIssuedAt(now)
            .setExpiration(expirationDate)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = getUsernameFromToken(token)
        val created = getIssuedAtDateFromToken(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun calculateExpirationDate(createdDate: Date): Date? {
        return Date(createdDate.time + expiration * 1000)
    }
}


