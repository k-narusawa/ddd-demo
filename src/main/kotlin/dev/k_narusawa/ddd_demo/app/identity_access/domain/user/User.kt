package dev.k_narusawa.ddd_demo.app.identity_access.domain.user

import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "ddd_user")
class User private constructor(
  @EmbeddedId
  @AttributeOverride(name = "value", column = Column("user_id"))
  val userId: UserId,

  @Embedded
  @AttributeOverride(name = "value", column = Column("username"))
  private var username: Username,

  @Embedded
  @AttributeOverride(name = "value", column = Column("password"))
  private var password: Password
) {
  companion object {
    fun register(
      username: Username,
      password: Password
    ): User {
      return User(
        userId = UserId.new(),
        username = username,
        password = password
      )
    }
  }

  fun getUsername() = this.username

  fun matchPassword(
    rawPassword: String,
  ): Boolean {
    return this.password.matches(rawPassword)
  }

  fun changeUsername(
    newUsername: Username,
  ) {
    this.username = newUsername
  }

  fun changePassword(
    newPassword: Password,
  ) {
    this.password = newPassword
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is User) return false

    if (userId != other.userId) return false

    return true
  }

  override fun hashCode(): Int {
    return userId.hashCode()
  }
}
