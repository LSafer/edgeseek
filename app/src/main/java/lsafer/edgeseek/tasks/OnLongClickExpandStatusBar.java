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
package lsafer.edgeseek.tasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Toast;
import lsafer.edgeseek.Edge;
import lsafer.edgeseek.R;

import java.util.Objects;

/**
 * A listener that expands the status bar when called when a long click occurred on its target edge.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 07-Jun-20
 */
final public class OnLongClickExpandStatusBar implements View.OnLongClickListener {
	/**
	 * The task name of this.
	 */
	final public static String TASK = "expand_status_bar";

	/**
	 * The context to be used by this listener.
	 */
	private Context context;
	/**
	 * The edge that this listener is targeting.
	 */
	private Edge edge;

	/**
	 * Construct a new long-click-listener that expands the status bar when called.
	 *
	 * @param context to be used
	 * @param edge    to target
	 * @throws NullPointerException if the given 'context' or 'edge' is null.
	 */
	public OnLongClickExpandStatusBar(Context context, Edge edge) {
		Objects.requireNonNull(context, "context");
		Objects.requireNonNull(edge, "edge");
		this.context = context;
		this.edge = edge;
	}

	@SuppressLint("WrongConstant")
	@Override
	public boolean onLongClick(View v) {
		try {
			//noinspection JavaReflectionMemberAccess, WrongConstant
			Class.forName("android.app.StatusBarManager")
					.getMethod("expandNotificationsPanel")
					.invoke(this.context.getSystemService("statusbar"));

		} catch (ReflectiveOperationException e) {
			Toast.makeText(this.context, this.context.getString(R.string._des_erro_ERROR, e.getMessage()), Toast.LENGTH_LONG).show();
		}

		return true;
	}
}
