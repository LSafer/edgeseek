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
package lsafer.edge_seek.view.global.perference;

import android.content.Context;
import android.view.ViewGroup;

import java.lang.reflect.Field;

import lsafer.edge_seek.io.Edge;
import lsafer.view.ViewAdapter;

/**
 * @author LSaferSE
 * @version 1 alpha (08-Oct-19)
 * @since 08-Oct-19
 */
@SuppressWarnings({"WeakerAccess", "JavaDoc"})
public abstract class EdgePerformanceViewAdapter <V> extends ViewAdapter {
	protected Edge.Node annotation;
	protected Edge edge;
	protected Object key;
	protected Field field;
	protected String[] resources;
	protected int mode;
	protected Class<?> type;

	public EdgePerformanceViewAdapter(Context context, ViewGroup parent0, ViewGroup parent1, Class<?> type, Edge edge, Object key) {
		this.edge = edge;
		this.key = key;
		this.type = type;
		this.field = this.edge.getField(key);
		this.annotation = this.field == null ? null : this.field.getAnnotation(Edge.Node.class);
		this.resources = this.annotation == null ? new String[]{"#", "#"} : context.getResources().getStringArray(this.annotation.value());
		this.mode = this.annotation == null ? 1 : this.annotation.mode();

		ViewGroup group;
		switch (this.mode) {
			case 0:
				group = parent0;
				break;
			case 1:
			default:
				group = parent1;
		}

		this.initialize(context, group);
	}

	public V get(){
		return (V) this.edge.get(this.key);
	}

	public void set(V value) {
		this.edge.put(this.key, value);
		this.edge.save();
	}
}
