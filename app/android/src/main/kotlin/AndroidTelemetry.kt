/*
 * Copyright (C) 2024-2026 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.android

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.Koin

/**
 * 本项目不集成任何第三方遥测/广告 SDK (Firebase / Sentry / Posthog), 因此遥测初始化为 no-op.
 * 保留该函数是为了兼容上游调用点 (TV/手机 Application), 不做任何上报.
 */
@Suppress("UNUSED_PARAMETER")
fun launchTelemetryInitialization(scope: CoroutineScope, context: Context, koin: Koin): Job = scope.launch {
    // no-op: 本项目移除 Firebase/Sentry 遥测.
}