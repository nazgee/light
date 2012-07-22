package eu.nazgee.utils;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DoubleValueChangeEntityModifier;
import org.andengine.entity.modifier.MoveByModifier;

import android.util.Log;

public class DampedMoveByModifier extends MoveByModifier {


	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	public float mDamping;
	// ===========================================================
	// Constructors
	// ===========================================================
	public DampedMoveByModifier(DoubleValueChangeEntityModifier pDoubleValueChangeEntityModifier, float pDamping) {
		super(pDoubleValueChangeEntityModifier);
		mDamping = pDamping;
	}

	public DampedMoveByModifier(float pDuration, float pX, float pY, IEntityModifierListener pEntityModifierListener, float pDamping) {
		super(pDuration, pX, pY, pEntityModifierListener);
		mDamping = pDamping;
	}

	public DampedMoveByModifier(float pDuration, float pX, float pY, float pDamping) {
		super(pDuration, pX, pY);
		mDamping = pDamping;
	}
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	protected void onChangeValues(final float pSecondsElapsed, final IEntity pEntity, final float pX, final float pY) {
		final float elapsedRatio = getSecondsElapsed() / getDuration();
		final float strength = mDamping + (1-mDamping) * (1-elapsedRatio);
		Log.e(getClass().getSimpleName(), "strength =" +strength + "; elapsedRatio=" + elapsedRatio);
		super.onChangeValues(pSecondsElapsed, pEntity, pX * strength, pY * strength);
	}
	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
