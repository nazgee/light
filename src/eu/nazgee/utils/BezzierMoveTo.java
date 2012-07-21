package eu.nazgee.utils;

import org.andengine.engine.handler.runnable.RunnableHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.QuadraticBezierCurveMoveModifier;
import org.andengine.util.math.MathUtils;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

import android.util.FloatMath;

public class BezzierMoveTo extends BaseMoveTo {

	// ===========================================================
	// Constants
	// ===========================================================
	public BezzierMoveTo(IEntity pEntity, MovementParam mMovementParam, final RunnableHandler pRunnableHandler) {
		super(new Animator(pEntity), mMovementParam, pRunnableHandler);
	}
	// ===========================================================
	// Fields
	// ===========================================================

	@Override
	public float moveTo(float pX, float pY) {
		IEntity entity = getEntityToMove();
		final float x = entity.getX();
		final float y = entity.getY();

		final float startX = x;
		final float startY = y;

		final float rotationRad = MathUtils.degToRad(entity.getRotation());
		final float overshot = ((MovementParam) getMovementParam()).getBezzierOvershot();
		final float cnt1X = (x + FloatMath.sin(rotationRad) * overshot);
		final float cnt1Y = (y + FloatMath.cos(rotationRad) * overshot);

		final float endX = pX;
		final float endY = pY;

		// approximately calculate ditance
		final float distance = MathUtils.distance(pX, pY, x, y) + overshot;
		final float time = getMovementParam().getETA(distance);

		final QuadraticBezierCurveMoveModifier bezzier = new QuadraticBezierCurveMoveModifier(time, startX, startY,
				cnt1X, cnt1Y, endX, endY);

		bezzier.addModifierListener(new IModifierListener<IEntity>() {
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
			}

			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				onDestination();
			}
		});
		mAnimator.run(bezzier);
		return time;
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

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	static public class MovementParam extends BaseMoveTo.MovementParam {
		private final float mOvershot;

		public MovementParam(eConstraintType mType, final float pValue, final float pBezzierOvershot) {
			super(mType, pValue);
			this.mOvershot = pBezzierOvershot;
		}

		public float getBezzierOvershot() {
			return mOvershot;
		}
	}
}
