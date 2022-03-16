package com.gomz.gomzuserservice.repository

import com.gomz.gomzuserservice.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
}