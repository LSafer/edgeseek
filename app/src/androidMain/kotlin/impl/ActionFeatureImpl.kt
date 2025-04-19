package net.lsafer.edgeseek.app.impl

import co.touchlab.kermit.Logger
import net.lsafer.edgeseek.app.data.settings.ActionFeature

sealed class ActionFeatureImpl {
    companion object {
        private val logger = Logger.withTag(ActionFeatureImpl::class.qualifiedName!!)

        fun from(feature: ActionFeature): ActionFeatureImpl? {
            return when (feature) {
                ActionFeature.Nothing -> null
                ActionFeature.ExpandStatusBar -> ExpandStatusBar
            }
        }
    }

    abstract fun execute(implLocal: ImplLocal)

    data object ExpandStatusBar : ActionFeatureImpl() {
        override fun execute(implLocal: ImplLocal) {
            try {
                //noinspection JavaReflectionMemberAccess, WrongConstant
                Class.forName("android.app.StatusBarManager")
                    .getMethod("expandNotificationsPanel")
                    .invoke(implLocal.context.getSystemService("statusbar"))
            } catch (e: ReflectiveOperationException) {
                logger.e("Couldn't expand status bar", e)
            }
        }
    }
}
