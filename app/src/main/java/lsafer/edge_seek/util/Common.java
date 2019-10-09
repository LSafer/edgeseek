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
package lsafer.edge_seek.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.IdRes;

import lsafer.edge_seek.R;
import lsafer.edge_seek.io.App;
import lsafer.edge_seek.io.Edge;

/**
 * @author LSaferSE
 * @version 1 alpha (07-Oct-19)
 * @since 07-Oct-19
 */
@SuppressWarnings("JavaDoc")
final public class Common {
	@IdRes
	public static int positionId(Edge edge) {
		if (edge.xposition == -1)
			switch (edge.position) {
				case 0:
					return R.id.bottom;
				case 1:
					return R.id.left;
				case 2:
					return R.id.top;
				case 3:
					return R.id.right;
			}
		else switch (edge.position) {
			case 0:
				switch (edge.xposition) {
					case 1:
						return R.id.bottom_left;
					case 3:
						return R.id.bottom_right;
				}
				break;
			case 1:
				switch (edge.xposition) {
					case 0:
						return R.id.left_bottom;
					case 2:
						return R.id.left_top;
				}
				break;
			case 2:
				switch (edge.xposition) {
					case 1:
						return R.id.top_left;
					case 3:
						return R.id.top_right;
				}
				break;
			case 3:
				switch (edge.xposition) {
					case 0:
						return R.id.right_bottom;
					case 2:
						return R.id.right_top;
				}
				break;
		}
		throw new AssertionError("position of " + edge.position + " and xposition of " + edge.xposition + " not allowed");
	}
	public static String positionName(int position) {
		return App.resources.getStringArray(R.array.edges)[position];
	}

	public static class FreeReceiver extends BroadcastReceiver {
		private Runnable runnable;
		private boolean active = true;

		public FreeReceiver(){
		}

		public FreeReceiver(Runnable runnable) {
			this.runnable = runnable;
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			if (this.active)
				this.runnable.run();
		}

		public void stop(){
			this.active = false;
		}
	}
}
