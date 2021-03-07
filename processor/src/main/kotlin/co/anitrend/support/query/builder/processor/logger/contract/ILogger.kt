package co.anitrend.support.query.builder.processor.logger.contract

internal interface ILogger {
    fun lineBreakWithSeperatorCharacter()
    fun debug(message: String)
    fun warning(message: String)
    fun error(message: String?)
}