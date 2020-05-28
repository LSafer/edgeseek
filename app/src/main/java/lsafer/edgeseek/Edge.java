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

import lsafer.edgeseek.data.EdgeData;
import lsafer.edgeseek.tasks.AudioControl;
import lsafer.edgeseek.tasks.BrightnessControl;
import lsafer.edgeseek.util.Util;

import static android.view.WindowManager.LayoutParams;

/**
 * An instance that responsible for viewing and managing an edge.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 27-May-20
 */
public class Edge {
	/**
	 * The data of this edge.
	 */
	final public EdgeData data;
	/**
	 * The layout params of this edge.
	 */
	final public WindowManager.LayoutParams params = new WindowManager.LayoutParams();
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

		this.view = new View(context) {
			@Override
			protected void onConfigurationChanged(Configuration newConfig) {
				super.onConfigurationChanged(newConfig);
				Edge.this.reattach();
			}
		};

		this.params.type = Build.VERSION.SDK_INT >= 26 ?
						   WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
						   WindowManager.LayoutParams.TYPE_PHONE;
		this.params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
							WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
							WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
							WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
	}

	/**
	 * Display this edge to the screen.
	 *
	 * @return this
	 * @throws IllegalStateException if this edge is showing already or if the edge not built yet
	 */
	public Edge attach() {
		if (this.attached)
			throw new IllegalStateException("edge already in the screen");
		if (!this.built)
			throw new IllegalStateException("edge has not been built");

		if (this.data.activated) {
			this.manager.addView(this.view, this.params);
			this.attached = true;
		}

		return this;
	}

	/**
	 * Build the view of this edge.
	 *
	 * @return this
	 */
	public Edge build() {
		//positioning
		int d = this.manager.getDefaultDisplay().getRotation();
		this.position = Util.position(this.data.position, false, this.manager.getDefaultDisplay().getRotation());
		this.landscape = this.position == 0 || this.position == 2;

		//listeners
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

		//dimensions
		this.params.gravity = Util.gravity(this.position);
		this.params.width = this.landscape ? LayoutParams.MATCH_PARENT : this.data.width;
		this.params.height = this.landscape ? this.data.width : LayoutParams.MATCH_PARENT;

		//appearance
		this.view.setBackgroundColor(this.data.color);
		this.view.setAlpha(Color.alpha(this.data.color));
		this.params.alpha = Color.alpha(this.data.color);

		this.built = true;
		return this;
	}

	/**
	 * Remove this edge from the screen.
	 *
	 * @return this
	 * @throws IllegalStateException if this edge is not showing
	 */
	public Edge detach() {
		if (!this.attached)
			throw new IllegalStateException("edge is not on the screen in the first place");

		this.manager.removeView(this.view);
		this.attached = false;
		return this;
	}

	/**
	 * Checks if this edge is currently shown in the display or not.
	 *
	 * @return the showing status of this edge
	 */
	public boolean isAttached() {
		return this.attached;
	}

	/**
	 * Check if this edge has been built.
	 *
	 * @return true, if this edge has been built
	 */
	public boolean isBuilt() {
		return this.built;
	}

	/**
	 * Update the view live. (call only when the edge is shown)
	 *
	 * @return this
	 * @throws IllegalStateException if the edge is currently not shown
	 */
	public Edge reattach() {
		if (!this.attached)
			throw new IllegalStateException("edge is not attached in the first place");

		this.manager.removeView(this.view);

		if (this.data.activated) {
			this.build();
			this.manager.addView(this.view, this.params);
			this.attached = true;
		} else {
			this.attached = false;
		}

		return this;
	}
}
