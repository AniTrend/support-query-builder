package co.anitrend.support.query.builder.processor.logger

import co.anitrend.support.query.builder.processor.logger.contract.ILogger
import javax.annotation.processing.Messager
import javax.tools.Diagnostic.Kind.*

@Suppress("SpellCheckingInspection")
internal class CoreLogger(
    private val messager: Messager
) : ILogger {

    private val seperator = (0..120).joinToString("") { "-" }

    private fun formatMessage(message: String?) = "$message\r\n"

    override fun lineBreakWithSeperatorCharacter() =
        messager.printMessage(OTHER, formatMessage(seperator))

    override fun debug(message: String) =
        messager.printMessage(NOTE, formatMessage(message))

    override fun warning(message: String) =
        messager.printMessage(WARNING, formatMessage(message))

    override fun error(message: String?) {
        messager.printMessage(ERROR, formatMessage(message))
        throw Throwable(message)
    }
}