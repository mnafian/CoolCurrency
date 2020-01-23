package prod.divrt.runner.utilities

import android.content.Context

/**
 * Created on : December/10/2018
 * Author     : mnafian
 */
object CoreConfig {
    private lateinit var configurator: CoreConfigurator

    fun setConfigurator(configurator: CoreConfigurator) {
        CoreConfig.configurator = configurator
    }

    val context: Context
        get() = configurator.context

}