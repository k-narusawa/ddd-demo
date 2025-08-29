package dev.knarusawa.dddDemo.app.task.domain.member

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, MemberId> {
  fun save(member: Member)

  fun findByMemberId(memberId: MemberId): Member?
}
