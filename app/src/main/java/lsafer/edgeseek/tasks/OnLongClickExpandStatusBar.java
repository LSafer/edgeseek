package lsafer.edgeseek.tasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import lsafer.edgeseek.Edge;
import lsafer.edgeseek.R;

/**
 * @author lsafer
 * @since 07-Jun-20
 */
public class OnLongClickExpandStatusBar implements View.OnLongClickListener {
	/**
	 * The edge that this listener is targeting.
	 */
	private Edge edge;
	/**
	 * The context to be used by this listener.
	 */
	private Context context;

	/**
	 * Construct a new long-click-listener that expands the status bar when called.
	 *
	 * @param context to be used
	 * @param edge    to target
	 * @throws NullPointerException if the given 'context' or 'edge' is null
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

		} catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			Toast.makeText(this.context, this.context.getString(R.string._des_erro_ERROR, e.getMessage()), Toast.LENGTH_LONG).show();
		}

		return true;
	}
}
