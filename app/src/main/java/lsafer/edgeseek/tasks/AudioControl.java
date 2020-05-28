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
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Objects;

import lsafer.edgeseek.Edge;
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
public class AudioControl implements View.OnTouchListener {
	/**
	 * The audio manager.
	 */
	private AudioManager am;
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
	 * The audio-type this is targeting.
	 */
	private int type;

	/**
	 * Constructs a new listener that targets the given edge.
	 *
	 * @param context to be used
	 * @param edge    to target
	 * @throws NullPointerException if the given 'context' or 'edge' is null
	 */
	public AudioControl(Context context, Edge edge) {
		Objects.requireNonNull(context, "context");
		Objects.requireNonNull(edge, "edge");
		this.context = context;
		this.edge = edge;

		//----
		this.am = context.getSystemService(AudioManager.class);

		switch (edge.data.seek) {
			case "music":
				this.type = AudioManager.STREAM_MUSIC;
				break;
			case "system":
				this.type = AudioManager.STREAM_SYSTEM;
				break;
			case "alarm":
				this.type = AudioManager.STREAM_ALARM;
				break;
			case "ring":
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
				vibrator.vibrate(1);
				break;
			default:
				//the seek is on
				float axis = this.edge.landscape ? event.getX() : event.getY();

				int value = (int) Util.range(
						//the change between the current axis and the previous one
						Util.change(this.axis, axis, (float) this.edge.data.sensitivity / 100) +
						//plus the current brightness value
						this.am.getStreamVolume(this.type),
						//capped to the maximum audio level allowed
						this.am.getStreamMaxVolume(this.type),
						//limited to not be a negative number
						Build.VERSION.SDK_INT >= Build.VERSION_CODES.P ?
						this.am.getStreamMinVolume(this.type) : 0
				);

				this.am.setStreamVolume(this.type, value, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);

				SingleToast.makeText(this.context, String.valueOf(value), Toast.LENGTH_SHORT).show();

				this.axis = axis;
		}
		return true;
	}
}
