package eu.nazgee.utils;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.QuadraticBezierCurveMoveModifier;
import org.andengine.util.math.MathUtils;

import android.util.FloatMath;

public class BezzierMoveTo extends BaseMoveTo {

	// ===========================================================
	// Constants
	// ===========================================================
	public BezzierMoveTo(IEntity pEntity, MovementParam mMovementParam) {
		super(new Animator(pEntity), mMovementParam);
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
