package net.lsafer.edgeseek.component

import android.content.Context
import android.util.Log
import android.widget.Toast

sealed class LongClickObserver {
    abstract fun onLongClick(context: Context)
}

object ExpandStatusBar : LongClickObserver() {
    override fun onLongClick(context: Context) {
        try {
            //noinspection JavaReflectionMemberAccess, WrongConstant
            Class.forName("android.app.StatusBarManager")
                .getMethod("expandNotificationsPanel")
                .invoke(context.getSystemService("statusbar"))
        } catch (e: ReflectiveOperationException) {
            Log.e("ExpandStatusBar", e.message, e)
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }
}
