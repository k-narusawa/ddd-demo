package dev.k_narusawa.ddd_demo.app.task.application.usecase.createTeam

import dev.k_narusawa.ddd_demo.executionListener.DatabaseCleanupListener
import org.junit.jupiter.api.DisplayName
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners

@SpringBootTest
@DisplayName("ユースケース_チーム")
@TestExecutionListeners(listeners = [DatabaseCleanupListener::class])
class TeamInteractorTest
