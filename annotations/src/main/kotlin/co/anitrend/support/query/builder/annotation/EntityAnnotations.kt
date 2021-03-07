package co.anitrend.support.query.builder.annotation

/**
 * Marks a class as an entity schema. This will create a file with the room
 * annotations on table name, columns and embeds
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class EntitySchema