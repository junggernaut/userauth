package com.gomz.gomzuserservice.controller

import com.gomz.gomzuserservice.domain.Account
import com.gomz.gomzuserservice.repository.AccountRepository
import com.gomz.gomzuserservice.service.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
@RequestMapping(value = ["/api/auth"])
class LoginController(
    @Autowired
    val accountService: AccountService
) {
    @PostMapping("/signup")
    fun accountSignup (@RequestBody account: Account) {
        println(account)
        accountService.saveAccount(account)
    }
}