/**
 * 
 */
package com.yeegol.DIYWear.clz;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * customize SurfaceView class base on {@link SurfaceView} which can catch the
 * touch event
 * 
 * @author sharl
 * 
 */
public class MySurfaceView extends SurfaceView {

	private static final String TAG = MySurfaceView.class.getName();

	public MySurfaceView(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public MySurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
}
