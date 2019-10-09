/*
 * Copyright (c) 2019, LSafer, All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * -You can edit this file (except the header).
 * -If you have change anything in this file. You
 *  shall mention that this file has been edited.
 *  By adding a new header (at the bottom of this header)
 *  with the word "Editor" on top of it.
 */
package lsafer.edge_seek.io;

import android.content.Context;

import androidx.annotation.ArrayRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import lsafer.edge_seek.R;
import lsafer.edge_seek.util.Common;
import lsafer.edge_seek.view.global.EdgeOverlay;
import lsafer.io.File;
import lsafer.io.JSONFileMap;
import lsafer.util.JSObject;
import lsafer.util.impl.AbstractIOJSObject;

/**
 * @author LSaferSE
 * @version 1 alpha (08-Oct-19)
 * @since 08-Oct-19
 */
@SuppressWarnings({"JavaDoc", "unused", "WeakerAccess"})
@JSObject.Configurations(restricted = false)
public class Edge extends AbstractIOJSObject<File, Object, Object> implements JSONFileMap<Object, Object> {
	@Node(value = R.array.alpha, mode = 1, float_meta = {0.0f, 1.0f})
	public Float alpha = 0.0f;

	@Node(value = R.array.rotate)
	public Boolean rotate = false;

	@Node(value = R.array.position, mode = 1, int_meta = {0, 3, 1})
	public Integer position = 0;

	@Node(value = R.array.xposition, mode = 1, int_meta = {0, 3, 1})
	public Integer xposition = -1;

	@Node(value = R.array.width, int_meta = {10, 100, 10})
	public Integer width = 20;

	@Node(value= R.array.sensitivity, float_meta = {0.001f, 2.0f, 0.001f})
	public Float sensitivity = 0.03f;

	@Node(value = R.array.color, mode = 1, string_meta = {"#??????"})
	public String color = "#ff00ff00";

	@Node(value = R.array.task, int_meta = {0, 0, 1, R.array.tasks})
	public Integer task = 0;

	@Node(value = R.array.vibration, int_meta = {0, 300, 20})
	public Integer vibration = 100;

	@Node(value = R.array.maximum)
	public Integer maximum = 255;

	@Node(value= R.array.minimum)
	public Integer minimum = 0;

	@Transient(true)
	public transient EdgeOverlay overlay;

	public void split() {
		this.delete();
		this.xposition = this.position == 0 || this.position == 2 ? 1 : 0;
		this.remote(f -> f.sibling(Common.positionName(this.position) + " " + Common.positionName(this.xposition)));
		this.save();
		this.xposition = this.position == 0 || this.position == 2 ? 3 : 2;
		this.remote(f -> f.sibling(Common.positionName(this.position) + " " + Common.positionName(this.xposition)));
		this.save();
	}

	public void merge() {
		App.edges.forEach((k, v)-> {
			if (v instanceof Edge && ((Edge) v).position.equals(this.position))
				((Edge) v).delete();
		});
		this.xposition = -1;
		this.remote(r -> r.sibling(Common.positionName(this.position)));
		this.save();
	}

	public EdgeOverlay overlay(Context context) {
		return this.overlay == null ? this.overlay = new EdgeOverlay(context, this) : this.overlay;
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface Node {
		/**
		 * The description resources
		 *
		 * @return
		 */
		@ArrayRes int value();
		int[] int_meta() default {};
		String[] string_meta() default {};
		int mode() default 0;
		float[] float_meta() default {};
	}
}
