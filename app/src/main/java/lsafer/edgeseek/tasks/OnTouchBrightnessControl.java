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

import android.content.ContentResolver;
import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Objects;

import lsafer.edgeseek.Edge;
import lsafer.edgeseek.R;
import lsafer.edgeseek.legacy.SingleToast;
import lsafer.edgeseek.util.Util;

/**
 * A controller that listens for touches applied to an edge and
 * controls the brightness depending on the user input and the data
 * provided to it by the edge it targets.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 28-May-20
 */
public class OnTouchBrightnessControl implements View.OnTouchListener {
	/**
	 * The name of this task.
	 */
	final public static String TASK = "brightness";

	/**
	 * The previous axis.
	 */
	private Float axis;
	/**
	 * The context to be used by this listener.
	 */
	private Context context;
	/**
	 * The edge to work with.
	 */
	private Edge edge;
	/**
	 * The content resolver to be used by this.
	 */
	private ContentResolver resolver;

	/**
	 * Construct a new touch listener that controls brightness when it get invoked.
	 *
	 * @param context to be used
	 * @param edge    to work with
	 * @throws NullPointerException if the given 'context' or 'edge' is null
	 */
	public OnTouchBrightnessControl(Context context, Edge edge) {
		Objects.requireNonNull(context, "context");
		Objects.requireNonNull(edge, "edge");
		this.context = context;
		this.edge = edge;

		//-----
		this.resolver = context.getContentResolver();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_DOWN:
				//end of motion
				this.axis = null;
			case MotionEvent.ACTION_UP:
				//start/end of motion
				Vibrator vibrator = this.context.getSystemService(Vibrator.class);
				vibrator.vibrate(VibrationEffect.createOneShot(this.edge.edgeData.vibration, VibrationEffect.DEFAULT_AMPLITUDE));
				break;
			default:
				//the seek is on
				float axis = this.edge.landscape ? event.getX() : event.getY();

				try {
					int value = Util.compute(
							//old axis
							this.axis,
							//new axis
							axis,
							//sensitivity
							this.edge.edgeData.sensitivity,
							//sensitivity factor
							100,
							//current volume
							Settings.System.getInt(this.resolver, Settings.System.SCREEN_BRIGHTNESS),
							//maximum volume
							255,
							//minimum volume
							0
					);

					try {
						Settings.System.putInt(this.resolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
						Settings.System.putInt(this.resolver, Settings.System.SCREEN_BRIGHTNESS, value);

						//toast
						if (this.edge.edgeData.toast)
							SingleToast.makeText(this.context, String.valueOf(value), Toast.LENGTH_SHORT).show();
					} catch (Exception e) {
						//permission not granted
						Toast.makeText(this.context, R.string._lak_perm_WRITE_SETTINGS, Toast.LENGTH_LONG).show();
					}

				} catch (Settings.SettingNotFoundException e) {
					Toast.makeText(this.context, this.context.getString(R.string._des_erro_ERROR, "SettingNotFound"), Toast.LENGTH_LONG).show();
				}

				this.axis = axis;
		}

		return false;
	}
}
