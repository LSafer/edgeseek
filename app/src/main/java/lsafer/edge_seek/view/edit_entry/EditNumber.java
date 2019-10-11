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
package lsafer.edge_seek.view.edit_entry;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.annotation.ArrayRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;

import lsafer.edge_seek.R;
import lsafer.edge_seek.view.abst.EditEntry;
import lsafer.util.JSObject;

/**
 * @author LSaferSE
 * @version 1 alpha (09-Oct-19)
 * @since 09-Oct-19
 */
@SuppressWarnings({"JavaDoc", "unused"})
@EditEntry.Configurations(fieldConfig = EditNumber.FieldConfig.class)
final public class EditNumber extends EditEntry<Number, EditNumber.FieldConfig> {
	private int fix, split, fmin, fmax;

	public EditNumber(Context context, ViewGroup[] parents, JSObject.Entry<Object, Number> entry) {
		super(context, parents, entry);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		this.fix = this.field_config.fix();
		this.split = this.field_config.split();
		this.fmin = (int)((float) fix * this.field_config.min());
		this.fmax = (int)((float) fix * this.field_config.max());
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
		View view = inflater.inflate(R.layout.view_epva_number, null);
		NumberPicker nm = view.findViewById(R.id.value);

		nm.setWrapSelectorWheel(false);
		nm.setMinValue(this.fmin / this.split);
		nm.setMaxValue(this.fmax / this.split);
		nm.setValue(this.position(this.entry.getValue()));

		nm.setDisplayedValues(this.field_config.res() == -1 ? this.positions() : this.getContext().getResources().getStringArray(this.field_config.res()));
		nm.setOnValueChangedListener((picker, oi, ni) -> this.entry.setValue(this.value(ni)));

		parent.addView(view);
		return view;
	}

	private String[] positions() {
		ArrayList<String> list = new ArrayList<>();

		for (int i = this.fmin; i<=this.fmax; i+= this.split)
			list.add(String.valueOf((float) i / this.fix));

		return list.toArray(new String[0]);
	}

	private int position(Number value) {
		float v = Float.valueOf(value.toString());

		int position = (int)(float) (v * this.fix) / this.split;
		return position < this.fmin ? this.fmin : position > this.fmax ? this.fmax : position;
	}

	private float value(int position) {
		position = position < this.fmin ? this.fmin : position > this.fmax ? this.fmax : position;
		return (float) (position * split) / this.fix;
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface FieldConfig {
		@ArrayRes int res() default -1;
		float max(); // >= 0
		float min() default 0; // >=0
		int split() default 1; // > 0
		int fix() default 1; // >0 & x10
	}
}
