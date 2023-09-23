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

package co.anitrend.support.query.builder.core.ext

import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import co.anitrend.support.query.builder.core.contract.AbstractQueryBuilder

/**
 * Translates [co.anitrend.support.query.builder.core.contract.AbstractQueryBuilder] into a SQLiteQuery
 *
 * @return [androidx.sqlite.db.SupportSQLiteQuery]
 */
fun AbstractQueryBuilder.asSupportSQLiteQuery(): SupportSQLiteQuery {
    return SimpleSQLiteQuery(build(), buildParameters().toTypedArray())
}