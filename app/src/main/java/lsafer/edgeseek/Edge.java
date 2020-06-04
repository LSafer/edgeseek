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

import java.util.Objects;

import cufyx.perference.MapDataStore;
import cufyx.perference.MapDataStore.OnDataChangeListener;
import lsafer.edgeseek.App.OnConfigurationChangeListener;
import lsafer.edgeseek.data.EdgeData;
import lsafer.edgeseek.tasks.AudioControl;
import lsafer.edgeseek.tasks.BrightnessControl;
import lsafer.edgeseek.util.Util;

import static android.view.WindowManager.LayoutParams;

/**
 * An instance that responsible for viewing and managing an edge.
 * <br>
 * Life Cycle
 * <ul>
 *     <li>{@link #start()} -> {@link #update()}</li>
 *     <li>
 *         {@link #update()} ->
 *         <ul>
 *             <li>{@link #build()} -> {@link #attach()}</li>
 *             <li>{@link #build()} -> {@link #reattach()}</li>
 *             <li>{@link #rebuild()} -> {@link #attach()}</li>
 *             <li>{@link #rebuild()} -> {@link #reattach()}</li>
 *             <li>{@link #detach()}</li>
 *         </ul>
 *     </li>
 *     <li>{@link #build()} -> {@link #rebuild()}</li>
 * </ul>
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
	 */
	private boolean attached = false;
	/**
	 * True, if this edge have been built.
	 */
	private boolean built = false;
	/**
	 * When true, this edge shouldn't be used again, ever!.
	 */
	private boolean destroyed = false;
	/**
	 * True, if this edge is working.
	 */
	private boolean started = false;

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

		//------- initial build
		this.view = new View(context);
		this.params = new LayoutParams();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		this.update();
	}

	@Override
	public void onDataChange(MapDataStore data, Object key, Object oldValue, Object newValue) {
		this.update();
	}

	/**
	 * Destroy this edge.
	 *
	 * @throws IllegalStateException if this edge already destroyed
	 */
	public void destroy() {
		this.assertNotDestroyed();

		//detach
		if (this.attached)
			this.detach();

		//remove listeners
		this.data.store.unregisterOnDataChangeListener(this);
		App.unregisterOnConfigurationChangeListener(this);

		//don't use again
		this.destroyed = true;
	}

	/**
	 * Start this edge. Once an edge is started, It can only be destroyed or updated.
	 *
	 * @return this
	 * @throws IllegalStateException if this edge is destroyed, or already have been started
	 */
	public Edge start() {
		this.assertNotDestroyed();
		this.assertNotStarted();

		this.data.store.registerOnDataChangeListener(this);
		App.registerOnConfigurationChangeListener(this);

		this.started = true;
		this.update();
		return this;
	}

	/**
	 * Update this edge with the edge-data of it.
	 *
	 * @return this
	 * @throws IllegalStateException if this edge is destroyed, or not started
	 */
	public Edge update() {
		this.assertNotDestroyed();
		this.assertStarted();

		if (this.data.activated) {
			//building
			if (this.built)
				this.rebuild();
			else this.build();
			//attaching
			if (this.attached)
				this.reattach();
			else this.attach();
		} else if (this.attached) {
			this.detach();
		}

		return this;
	}

	/**
	 * Assert that this edge is attached.
	 *
	 * @throws IllegalStateException if this edge is not attached
	 */
	private void assertAttached() {
		if (!this.attached)
			throw new IllegalStateException("non-attached edge");
	}

	/**
	 * Assert that this edge have been built.
	 *
	 * @throws IllegalStateException if this edge is not built
	 */
	private void assertBuilt() {
		if (!this.built)
			throw new IllegalStateException("edge not built");
	}

	/**
	 * Assert that this edge is not attached.
	 *
	 * @throws IllegalStateException if this edge is attached
	 */
	private void assertNotAttached() {
		if (this.attached)
			throw new IllegalStateException("attached edge");
	}

	/**
	 * Assert that this edge is not built.
	 *
	 * @throws IllegalStateException if this edge is built
	 */
	private void assertNotBuilt() {
		if (this.built)
			throw new IllegalStateException("built edge");
	}

	/**
	 * Assert that this edge is not destroyed.
	 *
	 * @throws IllegalStateException if this edge is destroyed
	 */
	private void assertNotDestroyed() {
		if (this.destroyed)
			throw new IllegalStateException("destroyed edge");
	}

	/**
	 * Assert that the edge is not started yet.
	 *
	 * @throws IllegalStateException if this edge did start
	 */
	private void assertNotStarted() {
		if (this.started)
			throw new IllegalStateException("started edge");
	}

	/**
	 * Assert that this edge have been started.
	 *
	 * @throws IllegalStateException if this edge have not been started
	 */
	private void assertStarted() {
		if (!this.started)
			throw new IllegalStateException("edge have not been started");
	}

	/**
	 * Display this edge to the screen.
	 *
	 * @return this
	 * @throws IllegalStateException if this edge is showing already or if the edge not built yet, or if this edge is destroyed
	 */
	private Edge attach() {
		this.assertNotDestroyed();
		this.assertNotAttached();
		this.assertBuilt();

		this.manager.addView(this.view, this.params);

		this.attached = true;
		return this;
	}

	/**
	 * Builds this edge for the first time.
	 *
	 * @return this edge
	 * @throws IllegalStateException if already built, or if this edge is destroyed
	 */
	private Edge build() {
		this.assertNotDestroyed();
		this.assertNotBuilt();

		//noinspection deprecation
		this.params.type = Build.VERSION.SDK_INT >= 26 ?
						   LayoutParams.TYPE_APPLICATION_OVERLAY :
						   LayoutParams.TYPE_PHONE;
		//noinspection deprecation
		this.params.flags = LayoutParams.FLAG_DISMISS_KEYGUARD |
							LayoutParams.FLAG_NOT_FOCUSABLE |
							LayoutParams.FLAG_LAYOUT_IN_SCREEN |
							LayoutParams.FLAG_LAYOUT_NO_LIMITS |
							LayoutParams.FLAG_NOT_TOUCH_MODAL |
							LayoutParams.FLAG_SHOW_WHEN_LOCKED;

		this.built = true;
		this.rebuild();
		return this;
	}

	/**
	 * Remove this edge from the screen.
	 *
	 * @return this
	 * @throws IllegalStateException if this edge is not showing, or if this edge is destroyed
	 */
	private Edge detach() {
		this.assertNotDestroyed();
		this.assertAttached();

		this.manager.removeView(this.view);

		this.attached = false;
		return this;
	}

	/**
	 * Update the view live. (call only when the edge is shown)
	 *
	 * @return this
	 * @throws IllegalStateException if the edge is currently not shown, or if this edge is destroyed
	 */
	private Edge reattach() {
		this.assertNotDestroyed();
		this.assertAttached();

		this.manager.updateViewLayout(this.view, this.params);
		return this;
	}

	/**
	 * rebuild the view of this edge.
	 *
	 * @return this
	 * @throws IllegalStateException if this edge haven't been built yet, or if this edge is destroyed
	 */
	private Edge rebuild() {
		this.assertNotDestroyed();
		this.assertBuilt();

		//positioning
		int d = this.manager.getDefaultDisplay().getRotation();
		this.position = Util.position(this.data.position, this.data.rotate, this.manager.getDefaultDisplay().getRotation());
		this.landscape = this.position == 0 || this.position == 2;

		//dimensions
		this.params.gravity = Util.gravity(this.position);
		this.params.width = this.landscape ? LayoutParams.MATCH_PARENT : this.data.width;
		this.params.height = this.landscape ? this.data.width : LayoutParams.MATCH_PARENT;

		//task
		switch (this.data.seek) {
			case "brightness":
				this.view.setOnTouchListener(new BrightnessControl(this.context, this));
				break;
			case "music":
			case "ring":
			case "alarm":
			case "system":
				this.view.setOnTouchListener(new AudioControl(this.context, this));
				break;
		}

		//appearance
		this.view.setBackgroundColor(this.data.color);
		this.view.setAlpha(Color.alpha(this.data.color));
		this.params.alpha = Color.alpha(this.data.color);

		return this;
	}
}
