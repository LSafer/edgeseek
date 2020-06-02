/*
 *	Copyright 2020 LSafer
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
package lsafer.edgeseek.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import lsafer.edgeseek.App;
import lsafer.edgeseek.service.MainService;

/**
 * Receives Auto-Boot flag.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 01-Jun-20
 */
public class BootCompleteBroadcastReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

			if (App.data.auto_boot) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
					context.startForegroundService(new Intent(context, MainService.class));
				else context.startService(new Intent(context, MainService.class));
			}
		}
	}
}
