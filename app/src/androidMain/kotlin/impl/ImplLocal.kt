package net.lsafer.edgeseek.app.impl

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import net.lsafer.edgeseek.app.Local

class ImplLocal {
    lateinit var local: Local
    lateinit var context: Context

    lateinit var defaultScope: CoroutineScope

    lateinit var toast: CustomToastFacade
    lateinit var dimmer: CustomDimmerFacade
}
