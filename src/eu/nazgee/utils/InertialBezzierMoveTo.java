package eu.nazgee.utils;

import org.andengine.engine.handler.runnable.RunnableHandler;
import org.andengine.entity.IEntity;

public class InertialBezzierMoveTo extends BezzierMoveTo {

	// ===========================================================
	// Constants
	// ===========================================================
	public InertialBezzierMoveTo(IEntity pEntity, MovementParam mMovementParam, final RunnableHandler pRunnableHandler) {
		super(pEntity, mMovementParam, pRunnableHandler);
	}
	// ===========================================================
	// Fields
	// ===========================================================

	@Override
	public float moveTo(float pX, float pY) {
		final float ETA = super.moveTo(pX, pY);

		return ETA;
	}

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	@Override
	synchronized protected void onDestinationHandler() {
		super.onDestinationHandler();
	}
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	static public class MovementParam extends BezzierMoveTo.MovementParam {
		private final float mIdleSpeed;

		public MovementParam(eConstraintType mType, final float pValue, final float pBezzierOvershot, final float pIdleSpeed) {
			super(mType, pValue, pBezzierOvershot);
			this.mIdleSpeed = pIdleSpeed;
		}

		public float getIdleSpeed() {
			return mIdleSpeed;
		}
	}
}
