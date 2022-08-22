/*
 *	Copyright 2020-2022 LSafer
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *	    http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
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
