package eu.nazgee.utils;

import org.andengine.entity.Entity;

public class HeadingToRotation extends HeadingSaver {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private final float mRotationOffset;

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
		super(pEntity, pMinSpeed);
		mRotationOffset = pRotationOffset;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public void onUpdate(final float pSecondsElapsed) {
		super.onUpdate(pSecondsElapsed);
		mEntity.setRotation(-getHeadingDeg() + mRotationOffset);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}