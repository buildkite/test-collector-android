package com.buildkite.test.collector.android.util.logger

/**
 * Provides logging functionality with configurable log level sensitivity.
 *
 * @property minLevel The minimum log level that will be logged.
 */
class Logger(
    private val tag: String = "BuildkiteLogger",
    private val minLevel: LogLevel = LogLevel.INFO
) {
    /**
     * Logs a message at [LogLevel.DEBUG].
     * - Messages are logged only if [LogLevel.DEBUG] is greater than or equal to [minLevel].
     */
    fun debug(message: () -> String) = log(LogLevel.DEBUG, message)

    /**
     * Logs a message at [LogLevel.INFO].
     * - Messages are logged only if [LogLevel.INFO] is greater than or equal to [minLevel].
     */
    fun info(message: () -> String) = log(LogLevel.INFO, message)

    /**
     * Logs a message at [LogLevel.ERROR].
     * - Messages are logged only if [LogLevel.ERROR] is greater than or equal to [minLevel].
     */
    fun error(message: () -> String) = log(LogLevel.ERROR, message)

    /**
     * Conditionally logs messages based on the [minLevel] set.
     * - Logs to standard output for [LogLevel.DEBUG] and [LogLevel.INFO], and to standard error for [LogLevel.ERROR].
     */
    private fun log(level: LogLevel, message: () -> String) {
        if (level >= minLevel) {
            val output = if (level == LogLevel.ERROR) System.err else System.out
            output.println("\n$tag-${level.name}: ${message()}")
        }
    }

    /**
     * Defines the log levels, ordered from least to most severe.
     */
    enum class LogLevel {
        DEBUG,
        INFO,
        ERROR
    }
}
