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
package lsafer.edgeseek.legacy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import lsafer.edgeseek.App;
import lsafer.edgeseek.R;

/**
 * Two toasts of this class can't be displayed at the same time.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 08-Oct-2019
 */
final public class SingleToast extends Toast {
	/**
	 * The current displaying toast.
	 */
	private static SingleToast current;

	/**
	 * Constructs a new toast.
	 *
	 * @param context to be used
	 * @throws NullPointerException if the given context is null
	 */
	public SingleToast(Context context) {
		super(context);
	}

	/**
	 * relevant to the method {@link Toast#makeText(Context, CharSequence, int)}.
	 *
	 * @param context  to be used
	 * @param string   to be displayed
	 * @param duration to show the toast within
	 * @return the toast constructed
	 * @throws NullPointerException if the given 'context' or 'string' is null
	 */
	public static SingleToast makeText(Context context, String string, int duration) {
		Objects.requireNonNull(context, "context");
		Objects.requireNonNull(string, "string");

		context.setTheme(App.data.getTheme());
		SingleToast toast = new SingleToast(context);

		toast.setDuration(duration);
		View view = LayoutInflater.from(context).inflate(R.layout.legacy_toast, null);
		view.<TextView>findViewById(R.id.txt).setText(string);

		toast.setView(view);
		return toast;
	}

	@Override
	public void show() {
		synchronized (SingleToast.class) {
			if (SingleToast.current != null)
				SingleToast.current.cancel();
			SingleToast.current = this;
			super.show();
		}
	}
}
