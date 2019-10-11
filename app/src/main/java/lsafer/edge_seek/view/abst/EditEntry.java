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
package lsafer.edge_seek.view.abst;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ArrayRes;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import lsafer.edge_seek.R;
import lsafer.edge_seek.constant.Constant;
import lsafer.edge_seek.view.edit_entry.EditJSON;
import lsafer.util.Configurable;
import lsafer.util.JSObject;
import lsafer.view.ViewAdapter;

/**
 * @author LSaferSE
 * @version 1 alpha (08-Oct-19)
 * @since 08-Oct-19
 */
@SuppressWarnings("ALL")
public abstract class EditEntry<V, C> extends ViewAdapter implements Configurable {
	protected JSObject.Entry<Object, V> entry;
	protected FieldConfig base_field_config;
	protected C field_config;

	public EditEntry(Context context, ViewGroup[] sections, JSObject.Entry<Object, V> entry) {
		this.entry = entry;
		this.base_field_config = entry.field == null ? null : entry.field.getAnnotation(FieldConfig.class);
		this.field_config = entry.field == null ? null : (C) entry.field.getAnnotation(
				this.configurations(Configurations.class, null).fieldConfig()
		);

		this.initialize(context, sections[this.base_field_config == null ? Constant.SECTION_CUSTOM : this.base_field_config.section()]);

		String[] strings = this.base_field_config == null ? new String[]{String.valueOf(entry.getKey()), "Custom property"} :
						   this.getContext().getResources().getStringArray(this.base_field_config.res());
		this.getView().<TextView>findViewById(R.id.title).setText(strings[0]);
		this.getView().<TextView>findViewById(R.id.description).setText(strings[1]);
	}

	public static EditEntry<?, ?> newFor(Context context, ViewGroup[] sections, JSObject.Entry<Object, ?> entry) {
		if (entry.field == null)
			return new EditJSON(context, sections, (JSObject.Entry<Object, Object>) entry);
		try {
			return entry.field.getAnnotation(FieldConfig.class).editor()
					.getConstructor(Context.class, ViewGroup[].class, JSObject.Entry.class)
					.newInstance(context, sections, entry);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface FieldConfig {
		int section() default Constant.SECTION_MISC;
		Class<? extends EditEntry> editor() default EditJSON.class;
		@ArrayRes int res();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Inherited
	public @interface Configurations {
		Class<? extends Annotation> fieldConfig() default FieldConfig.class;
	}
}
