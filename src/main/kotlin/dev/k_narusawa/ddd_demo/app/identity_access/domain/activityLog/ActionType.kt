package dev.k_narusawa.ddd_demo.app.identity_access.domain.activityLog

enum class ActionType {
    LOGIN_SUCCESS,
    LOGIN_FAILED,
    LOGOUT,
    CHANGE_USERNAME,
    CHANGE_PASSWORD,
}
