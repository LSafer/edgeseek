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

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ArrayRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import lsafer.edge_seek.R;
import lsafer.edge_seek.view.abst.EditEntry;
import lsafer.util.Arrays;
import lsafer.util.JSObject;

/**
 * @author LSaferSE
 * @version 1 alpha (08-Oct-19)
 * @since 08-Oct-19
 */
@SuppressWarnings("ALL")
@EditEntry.Configurations(fieldConfig = EditString.FieldConfig.class)
final public class EditString extends EditEntry<String, EditString.FieldConfig> {
	public EditString(Context context, ViewGroup[] parents, JSObject.Entry<Object, String> entry) {
		super(context, parents, entry);
	}

	@SuppressWarnings("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
		View view = inflater.inflate(R.layout.view_epva_strings, null);
		TextView value = view.findViewById(R.id.value);

		Object val = this.entry.getValue();

		String[] posibles = this.field_config.posibles();
		String[] strings = this.getContext().getResources().getStringArray(this.field_config.res());

		int index = Arrays.indexOf(val, this.field_config.posibles());

		value.setText(index == -1? String.valueOf(val) : strings[index]);
		value.setOnClickListener(v -> new AlertDialog.Builder(this.getContext(), R.style.KroovAlertDialogTheme)
				.setItems(strings, (d, i) -> {
					this.entry.setValue(posibles[i]);
					value.setText(strings[i]);
				}).show()
		);

		parent.addView(view);
		return view;
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface FieldConfig {
		public String[] posibles();
		@ArrayRes int res();
	}
}
