package com.gomz.gomzuserservice.domain

import com.gomz.gomzuserservice.account.AccountRole
import org.hibernate.annotations.CreationTimestamp
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import java.util.stream.Collectors
import javax.persistence.*


@Entity
class Account(
    @Id
    @GeneratedValue
    var id: Long? = null,
    var walletId: String,
    var username: String,
    var password: String,
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    var roles: MutableSet<AccountRole>,
    @CreationTimestamp
    var createDt: LocalDateTime
){

    fun getAuthorities(): User {
        return User(this.walletId, this.password, this.roles.stream().map { role -> SimpleGrantedAuthority("ROLE_$role") }.collect(Collectors.toSet()))
    }


}