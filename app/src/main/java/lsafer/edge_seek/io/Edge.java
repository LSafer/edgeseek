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
import android.graphics.Color;
import android.media.AudioManager;

import lsafer.edge_seek.R;
import lsafer.edge_seek.constant.Constant;
import lsafer.edge_seek.util.Common;
import lsafer.edge_seek.view.EdgeOverlay;
import lsafer.edge_seek.view.abst.EditEntry;
import lsafer.edge_seek.view.edit_entry.EditBoolean;
import lsafer.edge_seek.view.edit_entry.EditNumber;
import lsafer.edge_seek.view.edit_entry.EditString;
import lsafer.io.File;
import lsafer.io.JSONFileMap;
import lsafer.util.JSObject;
import lsafer.util.impl.AbstractIOJSObject;

/**
 * Edge structure containing all the information and the needed utils to deal with an edge.
 *
 * @author LSaferSE
 * @version 1 alpha (08-Oct-19)
 * @since 08-Oct-19
 */
@JSObject.Configurations(restricted = false)
final public class Edge extends AbstractIOJSObject<File, Object, Object> implements JSONFileMap<Object, Object> {
	/**
	 * Transparency of the edge.
	 */
	@EditEntry.FieldConfig(res = R.array.alpha_d, section = Constant.SECTION_APPEARANCE, editor = EditNumber.class)
	@EditNumber.FieldConfig(max = 1.0f, fix = 10)
	public Float alpha = 0.0f;

	/**
	 * Is it allowed to rotate edges when the screen rotates.
	 */
	@EditEntry.FieldConfig(res = R.array.rotate_d, section = Constant.SECTION_ADVANCED, editor = EditBoolean.class)
	@EditBoolean.FieldConfig()
	public Boolean rotate = false;

	/**
	 * The position of the edge.
	 */
	@EditEntry.FieldConfig(res = R.array.position_d, section = Constant.SECTION_HIDDEN, editor = EditNumber.class)
	@EditNumber.FieldConfig(res = R.array.positions, max = 3)
	public Integer position = 0;

	/**
	 * The secondary position of this edge.
	 */
	@EditEntry.FieldConfig(res = R.array.xposition_d, section = Constant.SECTION_HIDDEN, editor = EditNumber.class)
	@EditNumber.FieldConfig(res = R.array.positions,  max= 4)
	public Integer xposition = 4;

	/**
	 * The width of this edge.
	 */
	@EditEntry.FieldConfig(res = R.array.width_d, section = Constant.SECTION_APPEARANCE, editor = EditNumber.class)
	@EditNumber.FieldConfig(max = 100, split = 5)
	public Integer width = 20;

	/**
	 * The sensitivity of this edge.
	 */
	@EditEntry.FieldConfig(res = R.array.sensitivity_d, editor = EditNumber.class)
	@EditNumber.FieldConfig(min = 0.01f, max = 10.0f, fix = 100)
	public Float sensitivity = 0.3f;

	/**
	 * The color of this edge.
	 */
	@EditEntry.FieldConfig(res = R.array.color_d, section = Constant.SECTION_APPEARANCE)
	public String color = "#70a0ffa0";

	/**
	 * The task should be executed when the edge get touched.
	 */
	@EditEntry.FieldConfig(res = R.array.seek_task_d, section = Constant.SECTION_ACTIONS, editor = EditString.class)
	@EditString.FieldConfig(res = R.array.seek_tasks, posibles = {Constant.TASK_NOTHING, Constant.TASK_CONTROL_BRIGHTNESS, Constant.TASK_CONTROL_AUDIO})
	public String seek_task = Constant.TASK_CONTROL_BRIGHTNESS;

	/**
	 * The task should be executed when the edge get double touched.
	 */
	@EditEntry.FieldConfig(res = R.array.double_click_task_d, section = Constant.SECTION_ACTIONS, editor = EditString.class)
	@EditString.FieldConfig(res = R.array.double_click_tasks, posibles = {Constant.TASK_NOTHING, Constant.TASK_EXPAND_STATUSBAR})
	public String double_click_task = Constant.TASK_NOTHING;

	/**
	 * The vibration duration when the edge get touched.
	 */
	@EditEntry.FieldConfig(res = R.array.vibration_d, editor = EditNumber.class)
	@EditNumber.FieldConfig(max = 20)
	public Integer vibration = 1;

	/**
	 * Maximum value allowed to be set.
	 */
	@EditEntry.FieldConfig(res = R.array.maximum_d, editor = EditNumber.class)
	@EditNumber.FieldConfig(max = 255)
	public Integer maximum = 255;

	/**
	 * Minimum value allowed to be set.
	 */
	@EditEntry.FieldConfig(res = R.array.minimum_d, editor = EditNumber.class)
	@EditNumber.FieldConfig(max = 255)
	public Integer minimum = 0;

	/**
	 * For audio task. To define what stream to be targeted.
	 */
	@EditEntry.FieldConfig(res = R.array.stream_d, editor = EditNumber.class)
	@EditNumber.FieldConfig(res = R.array.streams, max = 9)
	public Integer stream = AudioManager.STREAM_MUSIC;

	/**
	 * The current displaying overlay at the position of this edge.
	 */
	private transient EdgeOverlay overlay;

	/**
	 * Split this edge into two edges with the same {@link #position} but a different {@link #xposition}.
	 */
	public void split() {
		this.delete();
		this.xposition = this.position == 0 || this.position == 2 ? 1 : 0;
		this.remote(f -> f.sibling(Common.positionName(this.position) + " " + Common.positionName(this.xposition)));
		this.save();
		this.xposition = this.position == 0 || this.position == 2 ? 3 : 2;
		this.remote(f -> f.sibling(Common.positionName(this.position) + " " + Common.positionName(this.xposition)));
		this.save();
	}

	/**
	 * Merge this edge with all edges with the same {@link #position} of this to became one edge with a {@link #xposition} of 4 (aka null).
	 */
	public void merge() {
		App.edges.forEach((k, v)-> {
			if (v instanceof Edge && ((Edge) v).position.equals(this.position))
				((Edge) v).delete();
		});
		this.xposition = 4;
		this.remote(r -> r.sibling(Common.positionName(this.position)));
		this.save();
	}

	/**
	 * Get the color set to this.
	 *
	 * @return the color set to this
	 */
	public int color(){
		try {
			return Color.parseColor(this.color);
		}catch (Exception e){
			return Color.GREEN;
		}
	}

	/**
	 * Get the current displaying overlay at the position of this edge.
	 * Or create a new one if no overlay displaying.
	 *
	 * @param context to create a new overlay if no overlay displaying
	 * @return the current displaying overlay at the position of this edge
	 */
	public EdgeOverlay overlay(Context context) {
		return this.overlay == null ? this.overlay = new EdgeOverlay(context, this) : this.overlay;
	}
}
