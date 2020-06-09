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
package lsafer.edgeseek.service;

import android.accessibilityservice.AccessibilityService;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A service to receive application changes-callbacks.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 09-Jun-20
 */
public class CallbackService extends AccessibilityService {
	/**
	 * The listeners to be called by the current running callback-service when it detects a window-state-change.
	 */
	final private static List<OnWindowStateChangedListener> listeners = new ArrayList<>();
	/**
	 * Determine if the call-back service is alive or not.
	 */
	private static volatile boolean alive;

	/**
	 * Register the given listener to be called when the service capture a window state change.
	 *
	 * @param listener to be registered
	 * @throws NullPointerException     if the given 'listener' is null
	 * @throws IllegalArgumentException if the given 'listener' already registered
	 */
	public static void registerOnWindowStateChangedListener(OnWindowStateChangedListener listener) {
		Objects.requireNonNull(listener, "listener");
		if (CallbackService.listeners.contains(listener))
			throw new IllegalArgumentException("listener already registered");

		synchronized (CallbackService.listeners) {
			CallbackService.listeners.add(listener);
		}
	}

	/**
	 * Unregister the given listener.
	 *
	 * @param listener to be unregistered
	 * @throws NullPointerException     if the given 'listener' is null
	 * @throws IllegalArgumentException if the given listener isn't registered
	 */
	public static void unregisterOnWindowStateChangedListener(OnWindowStateChangedListener listener) {
		Objects.requireNonNull(listener, "listener");
		if (!CallbackService.listeners.contains(listener))
			throw new IllegalArgumentException("listener not registered");

		synchronized (CallbackService.listeners) {
			CallbackService.listeners.remove(listener);
		}
	}

	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
			if (event.getPackageName() != null && event.getClassName() != null) {
				ComponentName componentName = new ComponentName(
						event.getPackageName().toString(),
						event.getClassName().toString()
				);
				try {
					ActivityInfo info = getPackageManager().getActivityInfo(new ComponentName(
							event.getPackageName().toString(),
							event.getClassName().toString()
					), 0);

					synchronized (CallbackService.listeners) {
						CallbackService.listeners.forEach(l -> l.onWindowStateChanged(info));
					}
				} catch (PackageManager.NameNotFoundException e) {
					Log.e("CallbackService", "onAccessibilityEvent", e);
				}
			}
		}
	}

	@Override
	protected void onServiceConnected() {
		super.onServiceConnected();

		synchronized (CallbackService.class) {
			CallbackService.alive = true;
		}
	}

	/**
	 * Check if the callback service is alive or not.
	 *
	 * @return whether the callback service is alive or not
	 */
	public static boolean isAlive() {
		synchronized (CallbackService.class) {
			return CallbackService.alive;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		synchronized (CallbackService.listeners) {
			CallbackService.listeners.forEach(OnWindowStateChangedListener::onCallBackDestroyed);
		}
		synchronized (CallbackService.class) {
			CallbackService.alive = false;
		}
	}

	@Override
	public void onInterrupt() {

	}

	/**
	 * A listener that get called on window state changed.
	 */
	public interface OnWindowStateChangedListener {
		/**
		 * Get called when the window-state has been changed.
		 *
		 * @param activity the information about the current 'top' activity
		 */
		void onWindowStateChanged(ActivityInfo activity);

		/**
		 * Get called when the callback service get destroyed.
		 */
		default void onCallBackDestroyed() {
		}
	}
}
