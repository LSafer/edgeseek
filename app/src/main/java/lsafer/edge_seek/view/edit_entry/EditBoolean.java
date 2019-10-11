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
import android.widget.Switch;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import lsafer.edge_seek.R;
import lsafer.edge_seek.view.abst.EditEntry;
import lsafer.util.JSObject;

/**
 * @author LSaferSE
 * @version 1 alpha (08-Oct-19)
 * @since 08-Oct-19
 */
@SuppressWarnings({"unused", "JavaDoc"})
@EditEntry.Configurations(fieldConfig = EditBoolean.FieldConfig.class)
final public class EditBoolean extends EditEntry<Boolean, EditBoolean.FieldConfig> {
	public EditBoolean(Context context, ViewGroup[] parents, JSObject.Entry<Object, Boolean> entry) {
		super(context, parents, entry);
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
		View view = inflater.inflate(R.layout.view_epva_boolean, null);
		Switch box = view.findViewById(R.id.value);

		box.setChecked(this.entry.getValue());
		box.setOnClickListener(v -> this.entry.setValue(((Switch) v).isChecked()));

		parent.addView(view);
		return view;
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface FieldConfig {
	}
}
