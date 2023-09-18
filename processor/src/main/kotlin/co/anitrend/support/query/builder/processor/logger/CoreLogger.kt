/*
 * Copyright 2023 AniTrend
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.anitrend.support.query.builder.processor.logger

import co.anitrend.support.query.builder.processor.logger.contract.ILogger
import javax.annotation.processing.Messager
import javax.tools.Diagnostic.Kind.ERROR
import javax.tools.Diagnostic.Kind.NOTE
import javax.tools.Diagnostic.Kind.OTHER
import javax.tools.Diagnostic.Kind.WARNING

internal class CoreLogger(
    private val delegate: Messager,
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
