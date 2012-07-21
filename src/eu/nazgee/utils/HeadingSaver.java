package eu.nazgee.utils;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.Entity;
import org.andengine.util.math.MathConstants;
import org.andengine.util.math.MathUtils;

public class HeadingSaver implements IUpdateHandler {
	// ===========================================================
	// Constants
	// ===========================================================
	public static final float MIN_SPEED_DEFAULT = 5;
	// ===========================================================
	// Fields
	// ===========================================================
	private final Entity mEntity;
	private float lastX;
	private float lastY;
	private final float mMinSpeed;
	private float mDeltaX;
	private float mDeltaY;

	// ===========================================================
	// Constructors
	// ===========================================================
	public HeadingSaver(final Entity pEntity) {
		this(pEntity, MIN_SPEED_DEFAULT);
	}

	public HeadingSaver(final Entity pEntity, final float pMinSpeed) {
		mEntity = pEntity;
		mMinSpeed = pMinSpeed;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	synchronized public void onUpdate(final float pSecondsElapsed) {
		final float dX = mEntity.getX() - lastX;
		final float dY = mEntity.getY() - lastY;
		final float speed = MathUtils.length(dX, dY) / pSecondsElapsed;

		if (speed > mMinSpeed) {
			lastX = mEntity.getX();
			lastY = mEntity.getY();
			mDeltaX = dX;
			mDeltaY = dY;
		}
	}

	synchronized public float getHeadingDeg() {
		float angle = MathUtils.atan2(mDeltaY, mDeltaX);
		if (angle < 0) {
			angle += MathConstants.PI_TWICE; /* 360 degrees. */
		}
		return angle;
	}

	@Override
	public void reset() {
	}
	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}