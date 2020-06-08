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

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
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
 * controls the audio depending on the user input and the data
 * provided to it by the edge it targets.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 28-May-20
 */
public class OnTouchAudioControl implements View.OnTouchListener {
	/**
	 * The name of the music task.
	 */
	final public static String TASK_MUSIC = "music";
	/**
	 * The name of the alarm task.
	 */
	final public static String TASK_ALARM = "alarm";
	/**
	 * The name of the ring task.
	 */
	final public static String TASK_RING = "ring";
	/**
	 * The name of the system task.
	 */
	final public static String TASK_SYSTEM = "system";

	/**
	 * The audio manager.
	 */
	protected AudioManager am;
	/**
	 * The previous axis.
	 */
	protected Float axis;
	/**
	 * The context to be used by this listener.
	 */
	protected Context context;
	/**
	 * The edge to work with.
	 */
	protected Edge edge;
	/**
	 * The audio-type this is targeting.
	 */
	protected int type;

	/**
	 * Constructs a new listener that targets the given edge.
	 *
	 * @param context to be used
	 * @param edge    to target
	 * @throws NullPointerException if the given 'context' or 'edge' is null
	 */
	public OnTouchAudioControl(Context context, Edge edge) {
		Objects.requireNonNull(context, "context");
		Objects.requireNonNull(edge, "edge");
		this.context = context;
		this.edge = edge;

		//----
		this.am = context.getSystemService(AudioManager.class);

		switch (edge.data.seek) {
			case TASK_MUSIC:
				this.type = AudioManager.STREAM_MUSIC;
				break;
			case TASK_SYSTEM:
				this.type = AudioManager.STREAM_SYSTEM;
				break;
			case TASK_ALARM:
				this.type = AudioManager.STREAM_ALARM;
				break;
			case TASK_RING:
				this.type = AudioManager.STREAM_RING;
				break;
		}
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
				vibrator.vibrate(VibrationEffect.createOneShot(this.edge.data.vibration, VibrationEffect.DEFAULT_AMPLITUDE));
				break;
			default:
				//the seek is on
				float axis = this.edge.landscape ? event.getX() : event.getY();

				int value = Util.compute(
						//old axis
						this.axis,
						//new axis
						axis,
						//sensitivity
						this.edge.data.sensitivity,
						//sensitivity factor
						1000,
						//current volume
						this.am.getStreamVolume(this.type),
						//maximum volume
						this.am.getStreamMaxVolume(this.type),
						//minimum volume
						Build.VERSION.SDK_INT >= Build.VERSION_CODES.P ?
						this.am.getStreamMinVolume(this.type) : 0
				);

				try {
					this.am.setStreamVolume(this.type, value, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);

					//toast
					if (this.edge.data.toast)
						SingleToast.makeText(this.context, String.valueOf(value), Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					//permission not granted
					Toast.makeText(this.context, R.string._lak_perm_WRITE_SETTINGS, Toast.LENGTH_LONG).show();
				}

				this.axis = axis;
		}

		return false;
	}
}
