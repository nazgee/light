package eu.nazgee.utils;

import org.andengine.engine.handler.runnable.RunnableHandler;
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
	private MovementListener mMovementListener;
	private final RunnableHandler mRunnableHandler;

	// ===========================================================
	// Constructors
	// ===========================================================
	BaseMoveTo(RunnableHandler pRunnableHandler) {
		this(null, pRunnableHandler);
	}

	BaseMoveTo(final IEntity pEntity, RunnableHandler pRunnableHandler) {
		this(null, null, pRunnableHandler);
	}

	public BaseMoveTo(Animator mAnimator, MovementParam mMovementParam, RunnableHandler pRunnableHandler) {

		this.mAnimator = mAnimator;
		this.mMovementParam = mMovementParam;
		this.mRunnableHandler = pRunnableHandler;
	}

	// ===========================================================
	// Getter & Setter
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

	public MovementListener getMovementListener() {
		return mMovementListener;
	}

	public void setMovementListener(MovementListener mMovementListener) {
		this.mMovementListener = mMovementListener;
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	protected void onDestination() {
		mRunnableHandler.postRunnable(new Runnable() {
			@Override
			public void run() {
				onDestinationHandler();
			}
		});
	}

	synchronized protected void onDestinationHandler() {
		if (mMovementListener != null) {
			mMovementListener.onDestination(BaseMoveTo.this);
		}
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	static public interface MovementListener {
		public void onDestination(BaseMoveTo pBaseMoveTo);
	}

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

		public float getVelocity(final float distance) {
			switch (mMovementType) {
			case TIME:
				return distance / mValue;
			case VELOCITY:
			default:
				return mValue;
			}
		}

		public enum eConstraintType {
			TIME,
			VELOCITY
		}
	}
}
