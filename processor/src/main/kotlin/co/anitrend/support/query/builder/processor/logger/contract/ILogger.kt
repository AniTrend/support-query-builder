package co.anitrend.support.query.builder.processor.logger.contract

internal interface ILogger {
    fun lineBreakWithSeparatorCharacter()
    fun debug(message: String)
    fun warning(message: String)
    fun error(message: String?)
}