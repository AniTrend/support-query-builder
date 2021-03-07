package co.anitrend.support.query.builder.buildSrc.module

internal object Modules {

    interface Module {
        val id: String

        /**
         * @return Formatted id of module as a path string
         */
        fun path(): String = ":$id"
    }

    enum class App(override val id: String) : Module {
        Main("sample")
    }

    enum class Processor(override val id: String) : Module {
        Core("processor")
    }

    enum class Common(override val id: String) : Module {
        Annotation("annotations"),
        Core("core")
    }
}


