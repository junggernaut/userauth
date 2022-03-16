package com.gomz.gomzuserservice.service

import com.gomz.gomzuserservice.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class JWTUserDetailsService(
    @Autowired
    val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username) ?: throw UsernameNotFoundException("User not found with username: $username")

//        return if ("user_id" == username) {
//            User(
//                "user_id", "$2a$10\$m/enYHaLsCwH2dKMUAtQp.ksGOA6lq7Fd2pnMb4L.yT4GyeAPRPyS",
//                ArrayList<E>()
//            )
//        } else {
//            throw UsernameNotFoundException("User not found with username: $username")
//        }
    }
}