package eu.nazgee.props;

import org.andengine.engine.handler.runnable.RunnableHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import eu.nazgee.utils.Animator;
import eu.nazgee.utils.BaseMoveTo;
import eu.nazgee.utils.BaseMoveTo.IMovementListener;
import eu.nazgee.utils.BaseMoveTo.MovementParam.eConstraintType;
import eu.nazgee.utils.BezzierMoveTo;
import eu.nazgee.utils.DampedMoveByModifier;
import eu.nazgee.utils.HeadingToRotation;

public class Rocket extends Sprite {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	final Animator mIdleAnimator = new Animator(this);
	final BaseMoveTo mMoveTo;
	final HeadingToRotation mHeadingToRotation = new HeadingToRotation(this);
	final RunnableHandler mRunnableHandler = new RunnableHandler();

	float mVelocityMax = 120;
	// ===========================================================
	// Constructors
	// ===========================================================
	public Rocket(final float pX, final float pY, final ITextureRegion pTextureRegion,
			final VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);

		mMoveTo = new BezzierMoveTo(this, new BezzierMoveTo.MovementParam(eConstraintType.VELOCITY, mVelocityMax, 100), mRunnableHandler);
		mMoveTo.setMovementListener(new IMovementListener() {
			@Override
			public void onDestination(BaseMoveTo pBaseMoveTo) {
				if (!mMoveTo.isMoving()) {
					mIdleAnimator.run(new DampedMoveByModifier(1, mHeadingToRotation.getVelocityX(), mHeadingToRotation.getVelocityY(), 0.01f));
				}
			}
		});

		registerUpdateHandler(mHeadingToRotation);
		registerUpdateHandler(mRunnableHandler);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	/**
	 * Move the rocket to a given location. To increase realism, rocket will
	 * move a little bit forward at first (bezzier overshot).
	 * 
	 * @param pX
	 * @param pY
	 * @return ETA
	 */
	public float flyTo(final float pX, final float pY) {
		mIdleAnimator.stop();
		return mMoveTo.moveTo(pX, pY);
	}

	public boolean isFlying() {
		return mMoveTo.isMoving();
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}