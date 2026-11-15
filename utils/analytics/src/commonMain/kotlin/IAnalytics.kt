/*
 * Copyright (C) 2024-2025 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.utils.analytics

/**
 * Global instance of [IAnalytics]. Always a no-op: 本项目不集成任何第三方分析/广告 SDK.
 */
val Analytics: IAnalytics get() = AnalyticsHolder.getInstance()

/**
 * Defines common interface for analytics
 */
interface IAnalytics {
    fun recordEvent(
        event: AnalyticsEvent,
        properties: Map<String, Any?> = emptyMap(),
    )

    fun onAppStart() {}

    /**
     * 实现在每条事件上自己附加的属性数 (平台、版本、会话等). 它们和调用方传入的属性一起计入 GA4 的 [MAX_EVENT_PROPERTIES] 上限,
     * 超出时 GA4 只保留其中一部分 (保留哪些不确定), 所以调用方一条事件最多传 `MAX_EVENT_PROPERTIES - extraPropertyCount` 个属性.
     */
    val extraPropertyCount: Int get() = 0

    companion object {
        /**
         * GA4 单条事件的属性上限, 包含实现附加的 [extraPropertyCount].
         */
        const val MAX_EVENT_PROPERTIES = 25
    }
}

inline fun IAnalytics.recordEvent(
    event: AnalyticsEvent,
    buildProperties: MutableMap<String, Any?>.() -> Unit = {},
) {
    recordEvent(event, buildMap(buildProperties))
}

@JvmInline
value class AnalyticsEvent(val event: String) {
    companion object {
        val Screen = AnalyticsEvent("screen")
        val AppStart = Screen // compatibility
        val ScreenView = AnalyticsEvent("screen_view")
        val SessionStart = AnalyticsEvent("session_start")

        val AppServerTestSuccess = AnalyticsEvent("app_server_test_success")
        val AppServerTestError = AnalyticsEvent("app_server_test_error")
        val AppServerSelectError = AnalyticsEvent("app_server_select_error")

        val NetworkCheckFailed = AnalyticsEvent("network_check_failed")
        val LoginClick = AnalyticsEvent("login_click")
        val LoginBangumiSuccess = AnalyticsEvent("login_bangumi_success")
        val LoginSkipClick = AnalyticsEvent("login_skip_click")

        val EpisodeEnter = AnalyticsEvent("episode_enter")
        val EpisodeSwitch = AnalyticsEvent("episode_switch")
        val EpisodePlaying = AnalyticsEvent("episode_playing")
        val EpisodeExit = AnalyticsEvent("episode_exit")
        val MediaFetchResult = AnalyticsEvent("media_fetch_result")

        /**
         * 播放页加载流程中的一步, 以 `step` 属性区分. 同一播放页的事件共享 `play_session_id`, 按 `seq` 排序即可重放.
         * 见 `docs/contributing/code/analytics-play-flow.md`.
         */
        val PlayFlow = AnalyticsEvent("play_flow")

        val CacheCreate = AnalyticsEvent("cache_create")

        val RatingEnter = AnalyticsEvent("rating_enter")
        val RatingSubmit = AnalyticsEvent("rating_submit")

        val SearchStart = AnalyticsEvent("search_start")
        val SubjectEnter = AnalyticsEvent("subject_enter")
        val SubjectRecommendationClick = AnalyticsEvent("subject_recommendation_click")
    }
}

object AnalyticsHolder {
    private val instance: IAnalytics = NoopAnalytics

    internal fun getInstance(): IAnalytics = instance
}

private object NoopAnalytics : IAnalytics {
    override fun recordEvent(event: AnalyticsEvent, properties: Map<String, Any?>) {
    }

    override fun onAppStart() {
    }
}
