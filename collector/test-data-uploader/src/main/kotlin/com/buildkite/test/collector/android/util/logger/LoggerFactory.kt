package com.buildkite.test.collector.android.util.logger

object LoggerFactory {
    /**
     * Creates a Logger instance with the appropriate log level and tag.
     *
     * @param isDebugEnabled Indicates if debug logging should be enabled.
     * @param tag The tag to be used in log messages.
     * @return A new [Logger] instance configured with the appropriate log level and tag.
     */
    fun create(isDebugEnabled: Boolean, tag: String): Logger {
        return Logger(
            minLevel = if (isDebugEnabled) Logger.LogLevel.DEBUG else Logger.LogLevel.INFO,
            tag = tag
        )
    }
}
