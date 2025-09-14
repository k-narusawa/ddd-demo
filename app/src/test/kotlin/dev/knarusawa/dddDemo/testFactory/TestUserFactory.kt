package dev.knarusawa.dddDemo.testFactory

import dev.knarusawa.dddDemo.app.identityAccess.domain.user.FamilyName
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.GivenName
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.Password
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.User
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.UserRepository
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.Username
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
      givenName: GivenName? = null,
      familyName: FamilyName? = null,
    ): User {
      val user =
        User.signup(
          username = username ?: Username.of("test@example.com"),
          password = password ?: Password.of("Password0"),
          givenName = givenName ?: GivenName.of("姓"),
          familyName = familyName ?: FamilyName.of("姓"),
        )
      userRepository.save(user = user)

      return user
    }
  }
