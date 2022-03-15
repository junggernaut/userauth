package com.gomz.gomzuserservice.repository

import com.gomz.gomzuserservice.domain.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.User

interface AccountRepository: JpaRepository<Account, Long> {
    fun findByWalletId(walletId: String): Account?
}