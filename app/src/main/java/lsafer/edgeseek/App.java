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
package lsafer.edgeseek;

import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import cufyx.perference.MapDataStore;
import lsafer.edgeseek.data.AppData;
import lsafer.edgeseek.service.MainService;
import lsafer.edgeseek.util.Util;

/**
 * The application class of this application.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 18-May-20
 */
final public class App extends Application implements MapDataStore.OnDataChangeListener {
	/**
	 * The listeners.
	 */
	final private static Collection<OnConfigurationChangeListener> listeners = new HashSet<>();
	/**
	 * The data of this application.
	 * <br>
	 * FINAL !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 */
	public static AppData data;

	/**
	 * Register the given listener to be called when a new configuration occurs.
	 *
	 * @param listener to be registered
	 * @throws NullPointerException     if the given 'listener' is null
	 * @throws IllegalArgumentException if the given 'listener' already registered
	 */
	public static void registerOnConfigurationChangeListener(OnConfigurationChangeListener listener) {
		Objects.requireNonNull(listener, "listener");
		if (App.listeners.contains(listener))
			throw new IllegalArgumentException("listener is already registered");

		App.listeners.add(listener);
	}

	/**
	 * Unregister the given listener from the listeners list.
	 *
	 * @param listener to be unregistered
	 * @throws NullPointerException     if the given 'listener' is null
	 * @throws IllegalArgumentException if the given 'listener' isn't registered
	 */
	public static void unregisterOnConfigurationChangeListener(OnConfigurationChangeListener listener) {
		Objects.requireNonNull(listener, "listener");
		if (!App.listeners.contains(listener))
			throw new IllegalArgumentException("listener is not registered");

		App.listeners.remove(listener);
	}

	@Override
	public void onCreate() {
		super.onCreate();

		//data
		App.data = new AppData(this, new File(this.getExternalFilesDir("data"), "main"));
		App.data.load();

		//listener
		App.data.store.registerOnDataChangeListener(this);
		App.data.edges.forEach(edge -> edge.store.registerOnDataChangeListener(this));

		this.setTheme(Util.theme(App.data.theme));
	}

	@Override
	public void onTerminate() {
		super.onTerminate();

		//remove listener
		App.data.store.unregisterOnDataChangeListener(this);
		App.data.edges.forEach(edge -> edge.store.registerOnDataChangeListener(this));
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		//notify listeners
		App.listeners.forEach(l -> l.onConfigurationChanged(newConfig));
	}

	@Override
	public void onDataChange(MapDataStore store, String key, Object oldValue, Object newValue) {
		//save data
		App.data.save();

		if (!Objects.equals(oldValue, newValue)) {
			//ignore useless calls!

			if (App.data.store == store) {
				//only app-data

				switch (key) {
					case AppData.THEME:
						//on-theme-changed
						this.setTheme(Util.theme(App.data.theme));
						break;
					case AppData.ACTIVATED:
						//update main-service that the activation status changed
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
							this.startForegroundService(new Intent(this, MainService.class));
						else this.startService(new Intent(this, MainService.class));
						break;
				}
			}
		}
	}

	/**
	 * A listener that listens to the changes in configuration.
	 */
	public interface OnConfigurationChangeListener {
		/**
		 * Called when a new configuration occurred.
		 *
		 * @param newConfig the new configuration
		 */
		void onConfigurationChanged(Configuration newConfig);
	}
}
