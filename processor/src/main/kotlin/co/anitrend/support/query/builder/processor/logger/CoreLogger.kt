package co.anitrend.support.query.builder.processor.logger

import co.anitrend.support.query.builder.processor.logger.contract.ILogger
import javax.annotation.processing.Messager
import javax.tools.Diagnostic.Kind.*

internal class CoreLogger(
    private val delegate: Messager
) : ILogger {

    private val separator = (0..120).joinToString("") { "-" }

    private fun formatMessage(message: String?) = "$message\r\n"

    override fun lineBreakWithSeparatorCharacter() =
        delegate.printMessage(OTHER, formatMessage(separator))

    override fun debug(message: String) =
        delegate.printMessage(NOTE, formatMessage(message))

    override fun warning(message: String) =
        delegate.printMessage(WARNING, formatMessage(message))

    override fun error(message: String?) {
        delegate.printMessage(ERROR, formatMessage(message))
        throw Throwable(message)
    }
}