package eu.nazgee.utils;

import org.andengine.entity.IEntity;

public abstract class BaseMoveTo implements IMoveTo {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	protected final Animator mAnimator;
	private MovementParam mMovementParam;

	// ===========================================================
	// Constructors
	// ===========================================================
	BaseMoveTo() {
		this(null);
	}

	BaseMoveTo(final IEntity pEntity) {
		this(null, null);
	}

	public BaseMoveTo(Animator mAnimator, MovementParam mMovementParam) {

		this.mAnimator = mAnimator;
		this.mMovementParam = mMovementParam;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	public IEntity getEntityToMove() {
		return mAnimator.getEntity();
	}

	public void setEntityToMove(IEntity mEntityToMove) {
		mAnimator.setEntity(mEntityToMove);
	}

	public MovementParam getMovementParam() {
		return mMovementParam;
	}

	public void setMovementParam(MovementParam mMovementParam) {
		this.mMovementParam = mMovementParam;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	static public class MovementParam {
		private final eConstraintType mMovementType;
		private final float mValue;

		public MovementParam(eConstraintType mType, final float pValue) {
			this.mMovementType = mType;
			this.mValue = pValue;
		}

		public float getETA(final float distance) {
			switch (mMovementType) {
			case TIME:
				return mValue;
			case VELOCITY:
			default:
				return distance / mValue;
			}
		}

		public enum eConstraintType {
			TIME,
			VELOCITY
		}
	}
}
