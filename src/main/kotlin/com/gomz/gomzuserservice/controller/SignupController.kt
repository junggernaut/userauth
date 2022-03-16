package com.gomz.gomzuserservice.controller

import com.gomz.gomzuserservice.domain.Account
import com.gomz.gomzuserservice.service.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
//@RequestMapping(value =["/api/user"])
class SignupController(
    @Autowired
    val accountService: AccountService
) {
    @GetMapping("/api/user/{userId}")
    fun accountSignup (@PathVariable userId: String):String {
        println(userId)
        return "hello"
    }
}