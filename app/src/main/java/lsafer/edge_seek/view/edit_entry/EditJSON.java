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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import lsafer.edge_seek.R;
import lsafer.edge_seek.view.abst.EditEntry;
import lsafer.json.JSON;
import lsafer.util.JSObject;

/**
 * @author LSaferSE
 * @version 1 alpha (08-Oct-19)
 * @since 08-Oct-19
 */
@SuppressWarnings({"WeakerAccess", "JavaDoc"})
@EditEntry.Configurations(fieldConfig = EditJSON.FieldConfig.class)
final public class EditJSON extends EditEntry<Object, EditJSON.FieldConfig> {
	public EditJSON(Context context, ViewGroup[] parents, JSObject.Entry<Object, Object> entry) {
		super(context, parents, entry);
	}

	@Override
	@SuppressLint("InflateParams")
	public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
		View view = inflater.inflate(R.layout.view_epva_json, null);
		EditText value = view.findViewById(R.id.value);

		view.<TextView>findViewById(R.id.type).setText("Type: " + (this.entry.field == null ? "Any" : this.entry.field.getType().getSimpleName()));

		value.setText(JSON.instance.stringify(this.entry.getValue()));
		value.addTextChangedListener(
				new TextWatcher() {
					@Override
					public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

					}

					@Override
					public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
					}

					@Override
					public void afterTextChanged(Editable editable) {
						try {
							EditJSON.this.entry.setValue(JSON.instance.parse(value.getText().toString()));
						} catch (Exception ignored) {
						}
					}
				}
		);

		parent.addView(view);
		return view;
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface FieldConfig {
	}
}
