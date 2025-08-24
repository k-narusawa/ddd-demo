package dev.k_narusawa.ddd_demo.testFactory

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.Password
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.User
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserRepository
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.Username
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TestUserFactory
    @Autowired
    constructor(
        private val userRepository: UserRepository,
    ) {
        fun createUser(
            username: Username? = null,
            password: Password? = null,
            personalName: String? = null,
        ): User {
            val user =
                User.signup(
                    username = username ?: Username.of("test@example.com"),
                    password = password ?: Password.of("Password0"),
                    personalName = personalName ?: "テスト氏名",
                )
            userRepository.save(user = user)

            return user
        }
    }
