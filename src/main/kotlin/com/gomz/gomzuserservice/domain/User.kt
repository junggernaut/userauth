package com.gomz.gomzuserservice.domain

import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class User(
    @Id
    @GeneratedValue
    val id: Long? = null,
    val username: String,
    val password: String,
): UserDetails {
}