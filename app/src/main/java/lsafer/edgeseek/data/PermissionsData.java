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
package lsafer.edgeseek.data;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PowerManager;
import android.provider.Settings;
import cufyx.perference.MapDataStore;
import lsafer.edgeseek.service.CallbackService;

import java.util.*;

/**
 * A map to simplify the permissions request and permissions get.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 07-Jun-2020
 */
final public class PermissionsData extends AbstractMap {
	/**
	 * A data store to use this map with the package {@code android.preference}.
	 */
	final public MapDataStore store = new MapDataStore(this);

	/**
	 * The context to get the permissions-status of.
	 */
	final private Context context;
	/**
	 * The entry set of this map.
	 */
	final private Set<Entry> entrySet;

	/**
	 * Construct a new permissions' data.
	 *
	 * @param context the context
	 * @throws NullPointerException if the given 'context' is null.
	 */
	public PermissionsData(Context context) {
		Objects.requireNonNull(context, "context");
		this.context = context;
		this.entrySet = Collections.unmodifiableSet(new HashSet(Arrays.asList(
				//android.permission.SYSTEM_ALERT_WINDOW
				new Entry() {
					@Override
					public Object getKey() {
						return Manifest.permission.SYSTEM_ALERT_WINDOW;
					}

					@Override
					public Object getValue() {
						return Settings.canDrawOverlays(PermissionsData.this.context);
					}

					@Override
					public Object setValue(Object value) {
						Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
						intent.setData(Uri.parse("package:" + context.getPackageName()));
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);

						return this.getValue();
					}
				},
				//android.permission.WRITE_SETTINGS
				new Entry() {
					@Override
					public Object getKey() {
						return Manifest.permission.WRITE_SETTINGS;
					}

					@Override
					public Object getValue() {
						return Settings.System.canWrite(PermissionsData.this.context);
					}

					@Override
					public Object setValue(Object value) {
						Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
						intent.setData(Uri.parse("package:" + context.getPackageName()));
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);

						return this.getValue();
					}
				},
				//android.permission.BIND_ACCESSIBILITY_SERVICE
				new Entry() {
					@Override
					public Object getKey() {
						return Manifest.permission.BIND_ACCESSIBILITY_SERVICE;
					}

					@Override
					public Object getValue() {
						return CallbackService.isAlive();
					}

					@Override
					public Object setValue(Object value) {
						Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
//						//need better solution
//						intent.setData(Uri.parse("package:" + context.getPackageName()));
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);

						return this.getValue();
					}
				},
				//android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
				new Entry() {
					@Override
					public Object getKey() {
						return Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS;
					}

					@Override
					public Object getValue() {
						return context.getSystemService(PowerManager.class)
								.isIgnoringBatteryOptimizations(context.getPackageName());
					}

					@Override
					public Object setValue(Object value) {
						Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
						intent.setData(Uri.parse("package:" + context.getPackageName()));
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);

						return this.getValue();
					}
				}
		)));
	}

	@Override
	public Object put(Object key, Object value) {
		for (Entry entry : this.entrySet)
			if (entry.getKey().equals(key))
				return entry.setValue(value);
		return null;
	}

	@Override
	public Set<Entry> entrySet() {
		return this.entrySet;
	}
}