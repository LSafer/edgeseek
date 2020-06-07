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

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Objects;

import cufyx.perference.MapDataStore;
import cufyx.perference.MapDataStore.OnDataChangeListener;
import lsafer.edgeseek.App.OnConfigurationChangeListener;
import lsafer.edgeseek.data.EdgeData;
import lsafer.edgeseek.tasks.OnLongClickExpandStatusBar;
import lsafer.edgeseek.tasks.OnTouchAudioControl;
import lsafer.edgeseek.tasks.OnTouchBrightnessControl;
import lsafer.edgeseek.util.Util;

import static android.view.WindowManager.LayoutParams;

/**
 * An instance that responsible for viewing and managing an edge.
 * <br<
 * an `attached` edge is always `built` and `alive`
 *
 * @author lsafer
 * @version 0.1.5
 * @since 27-May-20
 */
public class Edge implements OnDataChangeListener, OnConfigurationChangeListener {
	/**
	 * The data of this edge.
	 */
	final public EdgeData data;
	/**
	 * The layout params of this edge.
	 */
	final public WindowManager.LayoutParams params;
	/**
	 * The view to be displayed.
	 */
	final public View view;

	/**
	 * The context this edge is using.
	 */
	final private Context context;
	/**
	 * The window-manager for this edge to attach to.
	 */
	final private WindowManager manager;

	/**
	 * If this edge is landscape or not.
	 */
	public boolean landscape;
	/**
	 * The current position of this edge.
	 */
	public int position;

	/**
	 * True, if this edge is currently showing on the screen.
	 * <br>
	 * If this set to true, then {@link #built} and {@link #alive} should be true, too.
	 */
	protected boolean attached = false;
	/**
	 * True, if this edge have been built.
	 * <br>
	 * If this set to false, then {@link #attached} should be false, too.
	 */
	protected boolean built = false;
	/**
	 * Determines if this edge is alive or not.
	 * <br>
	 * If this set to false, then {@link #attached} should be false, too.
	 */
	protected boolean alive = false;

	/**
	 * Construct a new edge from the given data.
	 *
	 * @param context the context of the application
	 * @param manager for this edge to attach to
	 * @param data    to construct the edge from
	 * @throws NullPointerException if the given 'context' or 'manager' or 'data' is null
	 */
	public Edge(Context context, WindowManager manager, EdgeData data) {
		Objects.requireNonNull(context, "context");
		Objects.requireNonNull(manager, "manager");
		Objects.requireNonNull(data, "data");
		this.context = context;
		this.manager = manager;
		this.data = data;
		this.view = new View(context);
		this.params = new LayoutParams();
	}

	//events

	@Override
	public synchronized void onConfigurationChanged(Configuration newConfig) {
		this.assertAlive();

		if (!this.data.rotate) {
			//only if this edge is not allowed to rotate with the screen
			this.viewSolveDimens();
			//reattach to the layout-manager
			this.windowReattach();
		}
	}

	@Override
	public synchronized void onDataChange(MapDataStore data, String key, Object oldValue, Object newValue) {
		this.assertAlive();

		if (!Objects.equals(newValue, oldValue)) {
			//ignore useless calls

			switch (key) {
				case "activated":
					if (this.data.activated) {
						if (this.attached) {
							//if it is attached
							this.windowReattach();
						} else {
							//if it was not attached
							if (!this.built) {
								//if it haven't been attached before
								this.viewBuild();
							}

							this.windowAttach();
						}
					} else {
						//if it have been activated before
						this.windowDetach();
					}
					break;
				case "width":
				case "rotate":
					this.viewSolveDimens();
					if (this.attached)
						this.windowReattach();
					break;
				case "seek":
					this.viewSolveSeekTask();
					break;
				case "longClick":
					this.viewSolveLongClickTask();
					break;
				case "color":
					this.viewSolveStyle();
					break;
			}
		}
	}

	//public control

	/**
	 * Destroy this edge.
	 *
	 * @throws IllegalStateException if this edge is not alive
	 */
	public synchronized void destroy() {
		this.assertAlive();

		//detach
		if (this.attached)
			this.windowDetach();

		//remove listeners
		this.data.store.unregisterOnDataChangeListener(this);
		App.unregisterOnConfigurationChangeListener(this);

		this.alive = false;
	}

	/**
	 * Start/Revive this edge.
	 *
	 * @return this
	 * @throws IllegalStateException if this edge is alive
	 */
	public synchronized Edge start() {
		this.assertNotAlive();

		this.alive = true;

		this.data.store.registerOnDataChangeListener(this);
		App.registerOnConfigurationChangeListener(this);

		if (this.data.activated) {
			this.viewBuild();
			this.windowAttach();
		}

		return this;
	}

	//view control

	/**
	 * Builds this edge for the first time.
	 *
	 * @return this edge
	 * @throws IllegalStateException if already built, or if this edge is not alive
	 */
	protected synchronized Edge viewBuild() {
		this.assertAlive();
		this.assertNotBuilt();

		//noinspection deprecation
		this.params.type = Build.VERSION.SDK_INT >= 26 ?
						   LayoutParams.TYPE_APPLICATION_OVERLAY :
						   LayoutParams.TYPE_PHONE;
		this.params.flags = LayoutParams.FLAG_NOT_FOCUSABLE |
							LayoutParams.FLAG_LAYOUT_IN_SCREEN |
							LayoutParams.FLAG_LAYOUT_NO_LIMITS |
							LayoutParams.FLAG_NOT_TOUCH_MODAL |
							LayoutParams.FLAG_SHOW_WHEN_LOCKED;

		this.built = true;
		this.viewSolveDimens();
		this.viewSolveSeekTask();
		this.viewSolveLongClickTask();
		this.viewSolveStyle();
		return this;
	}

	/**
	 * Stick this edge to its position in the display.
	 *
	 * @return this
	 * @throws IllegalStateException if this edge is not alive
	 */
	protected synchronized Edge viewSolveDimens() {
		this.assertAlive();

		//positioning
		int d = this.manager.getDefaultDisplay().getRotation();
		this.position = Util.position(this.data.position, this.data.rotate, this.manager.getDefaultDisplay().getRotation());
		this.landscape = this.position == 0 || this.position == 2;

		//dimensions
		this.params.gravity = Util.gravity(this.position);
		this.params.width = this.landscape ? LayoutParams.MATCH_PARENT : this.data.width;
		this.params.height = this.landscape ? this.data.width : LayoutParams.MATCH_PARENT;

		return this;
	}

	/**
	 * Configure the seek-task of this edge.
	 *
	 * @return this
	 * @throws IllegalStateException if this edge is not alive
	 */
	protected synchronized Edge viewSolveSeekTask() {
		this.assertAlive();

		switch (this.data.seek) {
			case "brightness":
				this.view.setOnTouchListener(new OnTouchBrightnessControl(this.context, this));
				break;
			case "music":
			case "ring":
			case "alarm":
			case "system":
				this.view.setOnTouchListener(new OnTouchAudioControl(this.context, this));
				break;
			default:
				this.view.setOnTouchListener((v, e) -> false);
				break;
		}

		return this;
	}

	/**
	 * Configure the long-click-task of this edge.
	 *
	 * @return this
	 * @throws IllegalStateException if this edge is not alive
	 */
	protected synchronized Edge viewSolveLongClickTask() {
		this.assertAlive();

		switch (this.data.longClick) {
			case "expand_status_bar":
				this.view.setOnLongClickListener(new OnLongClickExpandStatusBar(this.context, this));
				break;
			default:
				this.view.setOnLongClickListener(v -> false);
				break;
		}

		return this;
	}

	/**
	 * Style the view of this edge.
	 *
	 * @return this
	 * @throws IllegalStateException if this edge is not alive
	 */
	protected synchronized Edge viewSolveStyle() {
		this.assertAlive();

		this.view.setBackgroundColor(this.data.color);
		this.view.setAlpha(Color.alpha(this.data.color));
		this.params.alpha = Color.alpha(this.data.color);

		return this;
	}

	//window control

	/**
	 * Display this edge to the screen.
	 *
	 * @return this
	 * @throws IllegalStateException if this edge is showing already or if the edge not built yet, or if this edge is not alive
	 */
	protected synchronized Edge windowAttach() {
		this.assertAlive();
		this.assertBuilt();
		this.assertNotAttached();

		try {
			this.manager.addView(this.view, this.params);
		} catch (Exception e) {
			Toast.makeText(this.context, R.string._lak_perm_SYSTEM_ALERT_WINDOW, Toast.LENGTH_LONG).show();
		}

		this.attached = true;
		return this;
	}

	/**
	 * Remove this edge from the screen.
	 *
	 * @return this
	 * @throws IllegalStateException if this edge is not showing, or if this edge is not alive
	 */
	protected synchronized Edge windowDetach() {
		this.assertAttached();

		try {
			this.manager.removeView(this.view);
		} catch (Exception e) {
			Toast.makeText(this.context, R.string._lak_perm_SYSTEM_ALERT_WINDOW, Toast.LENGTH_LONG).show();
		}

		this.attached = false;
		return this;
	}

	/**
	 * Update the view live. (call only when the edge is shown)
	 *
	 * @return this
	 * @throws IllegalStateException if the edge is currently not shown, or if this edge is not alive
	 */
	protected synchronized Edge windowReattach() {
		this.assertAttached();

		try {
			this.manager.updateViewLayout(this.view, this.params);
		} catch (Exception e) {
			//permission not granted
			Toast.makeText(this.context, R.string._lak_perm_SYSTEM_ALERT_WINDOW, Toast.LENGTH_LONG).show();
		}
		return this;
	}

	//assertions

	/**
	 * Assert that this edge is attached.
	 *
	 * @throws IllegalStateException if this edge is not attached
	 */
	private synchronized void assertAttached() {
		//if it is not alive or not built, then it is not attached
		this.assertAlive();
		this.assertBuilt();
		if (!this.attached)
			throw new IllegalStateException("non-attached edge");
	}

	/**
	 * Assert that this edge have been built.
	 *
	 * @throws IllegalStateException if this edge is not built
	 */
	private synchronized void assertBuilt() {
		if (!this.built)
			throw new IllegalStateException("edge not built");
	}

	/**
	 * Assert that this edge is not attached.
	 *
	 * @throws IllegalStateException if this edge is attached
	 */
	private synchronized void assertNotAttached() {
		if (this.attached)
			throw new IllegalStateException("attached edge");
	}

	/**
	 * Assert that this edge is not built.
	 *
	 * @throws IllegalStateException if this edge is built
	 */
	private synchronized void assertNotBuilt() {
		//if it is attached, then it is built
		this.assertNotAttached();
		if (this.built)
			throw new IllegalStateException("built edge");
	}

	/**
	 * Assert that this edge is alive.
	 *
	 * @throws IllegalStateException if this edge is not alive
	 */
	private synchronized void assertAlive() {
		if (!this.alive)
			throw new IllegalStateException("edge is not alive");
	}

	/**
	 * Assert that this edge is not alive.
	 *
	 * @throws IllegalStateException if this edge is alive
	 */
	private synchronized void assertNotAlive() {
		//if it is attached, then it is alive
		this.assertNotAttached();
		if (this.alive)
			throw new IllegalStateException("this edge is alive");
	}
}
