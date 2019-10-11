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
package lsafer.edge_seek.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.gesture.GestureOverlayView;
import android.graphics.Point;
import android.os.Build;
import android.os.Vibrator;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import lsafer.edge_seek.R;
import lsafer.edge_seek.io.Edge;
import lsafer.edge_seek.util.Common;
import lsafer.edge_seek.util.abst.Task;
import lsafer.io.File;

/**
 * @author LSaferSE
 * @version 1 alpha (05-Oct-19)
 * @since 05-Oct-19
 */
@SuppressLint("ViewConstructor")
@SuppressWarnings({"JavaDoc", "unused"})
public class EdgeOverlay extends GestureOverlayView {
	final private WindowManager.LayoutParams params = new WindowManager.LayoutParams();

	private boolean attached = false;
	private File remote;
	private OnGestureListener listener;
	public Edge edge;
	private boolean landscape;
	private Task seek_task;
	private Task double_click_task;
	private int dci = 0;
	private int clicks = 0;
	private Integer ppo = null;

	public EdgeOverlay(Context context, Edge edge){
		super(context);
		this.edge = edge;
		this.seek_task = Task.newFor(edge.seek_task).attach(this);
		this.double_click_task = Task.newFor(edge.double_click_task).attach(this);
	}

	public void build() {
		WindowManager window = this.getContext().getSystemService(WindowManager.class);
		assert window != null;
		Display display = window.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);

		int position = this.edge.rotate ? this.edge.position : Math.abs(display.getRotation() * 3 + this.edge.position) % 4;
		int xposition = this.edge.xposition == 4 ? 4:
						this.edge.rotate ? this.edge.xposition : Math.abs(display.getRotation() * 3 + this.edge.xposition) % 4;
		this.landscape = position == 0 || position == 2;
		int height = xposition == 4 ? LayoutParams.MATCH_PARENT : (this.landscape ? size.x : size.y) / 2;
//		System.out.println("LALA_LAND o:" + display.getRotation() + " ep:"+this.edge.position+" exp:"+this.edge.xposition+" cp:"+this.position+" cxp:"+this.xposition);
		this.params.gravity = Common.gravity(position) | (xposition == 4 ? 0 : Common.gravity(xposition));

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

		this.setBackgroundColor(this.edge.color());

		if (this.listener == null)
			this.addOnGestureListener(this.listener = new OnGestureListener() {
				@Override
				public void onGestureStarted(GestureOverlayView view, MotionEvent event) {
					Vibrator vibrator = EdgeOverlay.this.getContext().getSystemService(Vibrator.class);
					assert vibrator != null;
					vibrator.vibrate(EdgeOverlay.this.edge.vibration);

					EdgeOverlay.this.resolve_double_click();
					view.clear(false);
				}

				@Override
				public void onGesture(GestureOverlayView view, MotionEvent event) {
					EdgeOverlay.this.resolve_seek((int) (EdgeOverlay.this.landscape ? event.getX() : event.getY()));
					view.clear(false);
				}

				@Override
				public void onGestureEnded(GestureOverlayView view, MotionEvent event) {
					view.clear(false);
					EdgeOverlay.this.ppo = null;
				}

				@Override
				public void onGestureCancelled(GestureOverlayView view, MotionEvent event) {
					Vibrator vibrator = EdgeOverlay.this.getContext().getSystemService(Vibrator.class);
					assert vibrator != null;
					vibrator.vibrate(EdgeOverlay.this.edge.vibration);
					view.clear(false);
					EdgeOverlay.this.ppo = null;
				}
			});
	}

	public EdgeOverlay show() {
		if (!this.attached)
			try {
				WindowManager window = this.getContext().getSystemService(WindowManager.class);
				assert window != null;
				this.build();
				window.addView(this, this.params);
				this.attached = true;
			} catch (Exception e){
				e.printStackTrace();
			}
		return this;
	}

	public EdgeOverlay dismiss(){
		if (this.attached)
			try {
				WindowManager window = this.getContext().getSystemService(WindowManager.class);
				assert window != null;
				window.removeView(this);
			} catch (Exception e) {
				e.printStackTrace();
			}

		this.attached = false;
		return this;
	}

	public void resolve_seek(int integer) {
		Integer previous = ppo;
		ppo = integer *= this.landscape && this.edge.position == 3 ? -1 : 1;

		if (previous == null)
			previous = integer;

		integer = (int) ((float)(previous - integer) * this.edge.sensitivity);
		System.out.println("LALA_LAND: "+ integer);
		this.seek_task.accept(this.getContext(), integer);
	}

	public void resolve_double_click() {
		int dci = ++this.dci;
		this.clicks++;

		if (this.clicks >= 2)
			this.double_click_task.accept(this.getContext(), 0);

		new Thread(()-> {
			try {
				Thread.sleep(1000);
				if (this.dci == dci)
					this.clicks = 0;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
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
}
