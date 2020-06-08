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
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Objects;

import cufyx.perference.MapDataStore;
import cufyx.perference.MapDataStore.OnDataChangeListener;
import lsafer.edgeseek.App.OnConfigurationChangeListener;
import lsafer.edgeseek.data.EdgeData;
import lsafer.edgeseek.data.SideData;
import lsafer.edgeseek.tasks.OnLongClickExpandStatusBar;
import lsafer.edgeseek.tasks.OnTouchAudioControl;
import lsafer.edgeseek.tasks.OnTouchBrightnessControl;
import lsafer.edgeseek.util.Position;
import lsafer.edgeseek.util.Util;

import static android.view.WindowManager.LayoutParams;

/**
 * An instance that responsible for viewing and managing an edge.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 27-May-20
 */
public class Edge implements OnDataChangeListener, OnConfigurationChangeListener {
	/**
	 * The data of this edge.
	 */
	final public EdgeData edgeData;
	/**
	 * The data of the side of this edge.
	 */
	final public SideData sideData;
	/**
	 * The context this edge is using.
	 */
	final private Context context;
	/**
	 * The display this edge is showing at.
	 */
	final private Display display;
	/**
	 * The window-manager for this edge to attach to.
	 */
	final private WindowManager manager;
	/**
	 * If this edge is landscape or not.
	 */
	public boolean landscape;
	/**
	 * The layout params of this edge.
	 */
	public WindowManager.LayoutParams params;
	/**
	 * The current position of this edge.
	 */
	public int position;
	/**
	 * The view to be displayed.
	 */
	public View view;
	/**
	 * Determines if this edge is alive or not.
	 * <br>
	 * If this set to false, then {@link #attached} should be false, too.
	 */
	protected boolean alive = false;
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
	 * Construct a new edge from the given data.
	 *
	 * @param context  the context of the application
	 * @param edgeData to construct the edge from
	 * @param sideData the data of the side this edge is at
	 * @throws NullPointerException if the given 'context' or 'sideData' or 'edgeData' is null
	 */
	public Edge(Context context, SideData sideData, EdgeData edgeData) {
		Objects.requireNonNull(context, "context");
		Objects.requireNonNull(sideData, "sideData");
		Objects.requireNonNull(edgeData, "edgeData");
		this.context = context;
		this.edgeData = edgeData;
		this.sideData = sideData;
		this.manager = context.getSystemService(WindowManager.class);
		this.display = this.manager.getDefaultDisplay();
	}

	//events

	@Override
	public synchronized void onConfigurationChanged(Configuration newConfig) {
		if (this.attached) {
			//if this edge is not attached, then ignore the call

			if (!this.edgeData.rotate) {
				//only if the edge should stay the same position
				this.viewSolveDimens();
				//if its attached and have been started, then it is attached
				this.windowReattach();
			}
		}
	}

	@Override
	public synchronized void onDataChange(MapDataStore store, String key, Object oldValue, Object newValue) {
		if (this.alive) {
			if (!Objects.equals(newValue, oldValue)) {
				//ignore useless calls

				switch (key) {
					case SideData.FACTOR:
						//if the split-factor of the side of this changed,
						//the activation-status of this will change, too
					case EdgeData.ACTIVATED:
						//when the user toggles the activation status of this edge
						if (this.isActivated()) {
							//if it was deactivated and the user activated it
							if (!this.attached) {
								if (!this.built)
									this.viewBuild();

								this.windowAttach();
							}
						} else if (this.attached) {
							//if it was activated and the user deactivated it,
							//only do when attach, since the data may change while
							//this edge is not attached
							this.windowDetach();
						}
						break;
					case EdgeData.WIDTH:
						//when the width changes, the layout-params should change
						//and to show the effect of the change in the layout-params
						//we should update the window manager
					case EdgeData.ROTATE:
						//when the rotation changes, the current position should
						//be changed too, depending on the current display position
						this.viewSolveDimens();
						//only if it have been attached, since if its not,
						//then no need to update it
						if (this.attached)
							this.windowReattach();
						break;
					case EdgeData.SEEK:
						//update the seek-task is just changing the
						this.viewSolveSeekTask();
						break;
					case EdgeData.LONG_CLICK:
						this.viewSolveLongClickTask();
						break;
					case EdgeData.COLOR:
						this.viewSolveStyle();
						if (this.attached)
							this.windowReattach();
						break;
				}
			}
		}
	}

	//public control OK

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
		this.edgeData.store.unregisterOnDataChangeListener(this);
		this.sideData.store.unregisterOnDataChangeListener(this);
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

		this.edgeData.store.registerOnDataChangeListener(this);
		this.sideData.store.registerOnDataChangeListener(this);
		App.registerOnConfigurationChangeListener(this);

		if (this.isActivated()) {
			if (!this.built) {
				this.viewBuild();
			} else {
				this.viewSolveDimens();
				this.viewSolveSeekTask();
				this.viewSolveLongClickTask();
				this.viewSolveStyle();
			}
			this.windowAttach();
		}

		return this;
	}

	//ask OK

	/**
	 * Determine if this edge is allowed to be displayed or not.
	 *
	 * @return whether this edge is allowed to be displayed or not
	 */
	protected synchronized boolean isActivated() {
		return this.edgeData.activated && this.edgeData.factor == this.sideData.factor;
	}

	//view control OK

	/**
	 * Builds this edge for the first time.
	 *
	 * @return this edge
	 * @throws IllegalStateException if already built, or if this edge is not alive
	 */
	protected synchronized Edge viewBuild() {
		this.assertAlive();
		this.assertNotBuilt();

		this.view = new View(this.context);
		this.params = new LayoutParams();
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
		this.position = this.edgeData.rotate ? this.edgeData.position : Position.getRotated(this.edgeData.position, this.display.getRotation());
		this.landscape = Position.isLandscape(this.position);

		float height = (this.landscape ? Util.getWidth(this.display) : Util.getHeight(this.display)) / Position.getFactor(this.position);

		//dimensions
		this.params.gravity = Position.getGravity(this.position);
		this.params.width = this.landscape ? (int) height : this.edgeData.width;
		this.params.height = this.landscape ? this.edgeData.width : (int) height;

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

		switch (this.edgeData.longClick) {
			case OnLongClickExpandStatusBar.TASK:
				this.view.setOnLongClickListener(new OnLongClickExpandStatusBar(this.context, this));
				break;
			default:
				this.view.setOnLongClickListener(v -> false);
				break;
		}

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

		switch (this.edgeData.seek) {
			case OnTouchBrightnessControl.TASK:
				this.view.setOnTouchListener(new OnTouchBrightnessControl(this.context, this));
				break;
			case OnTouchAudioControl.TASK_ALARM:
			case OnTouchAudioControl.TASK_MUSIC:
			case OnTouchAudioControl.TASK_RING:
			case OnTouchAudioControl.TASK_SYSTEM:
				this.view.setOnTouchListener(new OnTouchAudioControl(this.context, this));
				break;
			default:
				this.view.setOnTouchListener((v, e) -> false);
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

		this.view.setBackgroundColor(this.edgeData.color);
		this.view.setAlpha(Color.alpha(this.edgeData.color));
		this.params.alpha = Color.alpha(this.edgeData.color);

		return this;
	}

	//window control FINAL

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
			this.attached = true;
		} catch (Exception e) {
			Toast.makeText(this.context, R.string._lak_perm_SYSTEM_ALERT_WINDOW, Toast.LENGTH_LONG).show();
		}

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
			this.attached = false;
		} catch (Exception e) {
			Toast.makeText(this.context, R.string._lak_perm_SYSTEM_ALERT_WINDOW, Toast.LENGTH_LONG).show();
		}

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

	//assertions FINAL

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
}
