package dev.knarusawa.dddDemo.app.project.application.usecase.createProject

import dev.knarusawa.dddDemo.executionListener.DatabaseCleanupListener
import org.junit.jupiter.api.DisplayName
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners

@SpringBootTest
@DisplayName("ユースケース_プロジェクト")
@TestExecutionListeners(listeners = [DatabaseCleanupListener::class])
class ProjectInteractorTest
