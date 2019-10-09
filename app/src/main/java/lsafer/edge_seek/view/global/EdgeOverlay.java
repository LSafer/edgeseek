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
package lsafer.edge_seek.view.global;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.function.BiConsumer;
import java.util.function.Function;

import lsafer.edge_seek.R;
import lsafer.edge_seek.io.Edge;
import lsafer.edge_seek.util.Graphics;
import lsafer.io.File;
import lsafer.view.Refreshable;

/**
 * @author LSaferSE
 * @version 1 alpha (05-Oct-19)
 * @since 05-Oct-19
 */
@SuppressLint("ViewConstructor")
@SuppressWarnings({"JavaDoc", "unused"})
public class EdgeOverlay extends GestureOverlayView implements Refreshable {
	final private WindowManager.LayoutParams params = new WindowManager.LayoutParams();

	private boolean attached = false;
	private File remote;
	private int center = 0;
	private OnGestureListener listener;
	private Edge edge;
	private boolean landscape;

	public EdgeOverlay(Context context, Edge edge){
		super(context);
		this.edge = edge;
	}

	@Override
	public void refresh() {
		WindowManager window = this.getContext().getSystemService(WindowManager.class);
		assert window != null;
		Display display = window.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);

		int position = this.edge.rotate ? this.edge.position : Math.abs(display.getRotation() * 3 + this.edge.position) % 4;
		int xposition = this.edge.xposition == -1 ? -1 :
						this.edge.rotate ? this.edge.xposition : Math.abs(display.getRotation() * 3 + this.edge.xposition) % 4;
		this.landscape = position == 0 || position == 2;
		int height = xposition == -1 ? LayoutParams.MATCH_PARENT : (this.landscape ? size.x : size.y) / 2;
//		System.out.println("LALA_LAND o:" + display.getRotation() + " ep:"+this.edge.position+" exp:"+this.edge.xposition+" cp:"+this.position+" cxp:"+this.xposition);
		this.params.gravity = Graphics.gravity(position) | (xposition == -1 ? 0 : Graphics.gravity(xposition));

		this.params.alpha = this.edge.alpha;
		this.params.width = this.landscape ? height : this.edge.width;
		this.params.height = !this.landscape ? height  : this.edge.width;

		this.params.type = Build.VERSION.SDK_INT >= 26 ?
						   WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
						   WindowManager.LayoutParams.TYPE_PHONE;
		this.params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
							WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
							WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
							WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

		this.setBackgroundColor(Color.parseColor(this.edge.color));

		if (this.listener == null)
			this.addOnGestureListener(this.listener = new OnGestureListener() {
				@Override
				public void onGestureStarted(GestureOverlayView view, MotionEvent event) {
					Vibrator vibrator = EdgeOverlay.this.getContext().getSystemService(Vibrator.class);
					assert vibrator != null;
					vibrator.vibrate(EdgeOverlay.this.edge.vibration);
					EdgeOverlay.this.center = (int) (EdgeOverlay.this.landscape ? event.getX() : event.getY());
					view.clear(false);
				}

				@Override
				public void onGesture(GestureOverlayView view, MotionEvent event) {
					EdgeOverlay.this.run((int) (EdgeOverlay.this.landscape ? event.getX() : event.getY()));
					view.clear(false);
				}

				@Override
				public void onGestureEnded(GestureOverlayView view, MotionEvent event) {
					view.clear(false);
				}

				@Override
				public void onGestureCancelled(GestureOverlayView view, MotionEvent event) {
					Vibrator vibrator = EdgeOverlay.this.getContext().getSystemService(Vibrator.class);
					assert vibrator != null;
					vibrator.vibrate(EdgeOverlay.this.edge.vibration);
					view.clear(false);
				}
			});
	}

	public void show() {
		if (!this.attached)
			try {
				WindowManager window = this.getContext().getSystemService(WindowManager.class);
				assert window != null;
				this.refresh();
				window.addView(this, this.params);
				this.attached = true;
			} catch (Exception e){
				e.printStackTrace();
			}
	}

	public void dismiss(){
		if (this.attached)
			try {
				WindowManager window = this.getContext().getSystemService(WindowManager.class);
				assert window != null;
				window.removeView(this);
			} catch (Exception e) {
				e.printStackTrace();
			}

		this.attached = false;
	}

	public void run(int integer) {
		integer = (int) ((this.center - integer) * this.edge.sensitivity);
		integer *= this.landscape && this.edge.position == 3 ? -1 : 1;
		integer += this.tasksx[this.edge.task].apply(this.getContext());
		integer = integer > this.edge.maximum ? this.edge.maximum : integer < this.edge.minimum ? this.edge.minimum : integer;

		this.tasks[this.edge.task].accept(this.getContext(), integer);
		this.toast(integer);
	}

	@SuppressLint("InflateParams")
	public void toast(int integer){
		View view = LayoutInflater.from(this.getContext()).inflate(R.layout.view_toast, null);
		view.<TextView>findViewById(R.id.txt).setText(String.valueOf(integer));
		new SingleToast(this.getContext(), view).show();
	}

	@Override
	protected void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		this.dismiss();
		this.show();
	}

	@SuppressWarnings("unchecked")
	final private static BiConsumer<Context, Integer>[] tasks = new BiConsumer[]{
			(BiConsumer<Context, Integer>)(context, integer) -> {
				ContentResolver resolver = context.getContentResolver();
				Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
				Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, integer);
			}
	};

	@SuppressWarnings("unchecked")
	final private static Function<Context, Integer>[] tasksx = new Function[] {
			(Function<Context, Integer>) (context) -> {
				try {
					return Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
				} catch (Settings.SettingNotFoundException e) {
					throw new RuntimeException(e);
				}
			}
	};
}
