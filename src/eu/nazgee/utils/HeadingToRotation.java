package eu.nazgee.utils;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.Entity;
import org.andengine.util.math.MathConstants;
import org.andengine.util.math.MathUtils;

public class HeadingToRotation implements IUpdateHandler {
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
	private final float mRotationOffset;
	private final float mMinSpeed;

	// ===========================================================
	// Constructors
	// ===========================================================
	public HeadingToRotation(final Entity pEntity) {
		this(pEntity, 90);
	}

	public HeadingToRotation(final Entity pEntity, final float pRotationOffset) {
		this(pEntity, pRotationOffset, MIN_SPEED_DEFAULT);
	}

	public HeadingToRotation(final Entity pEntity, final float pRotationOffset, final float pMinSpeed) {
		mEntity = pEntity;
		mRotationOffset = pRotationOffset;
		mMinSpeed = pMinSpeed;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public void onUpdate(final float pSecondsElapsed) {
		final float dX = mEntity.getX() - lastX;
		final float dY = mEntity.getY() - lastY;
		final float speed = MathUtils.length(dX, dY) / pSecondsElapsed;

		if (speed > mMinSpeed) {
			float angle = MathUtils.atan2(dY, dX);
			lastX = mEntity.getX();
			lastY = mEntity.getY();
			if (angle < 0) {
				angle += MathConstants.PI_TWICE; /* 360 degrees. */
			}
			mEntity.setRotation(-MathUtils.radToDeg(angle) + mRotationOffset);
		}
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