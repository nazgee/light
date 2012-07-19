package eu.nazgee.light;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import eu.nazgee.utils.BaseMoveTo.MovementParam.eConstraintType;
import eu.nazgee.utils.BezzierMoveTo;
import eu.nazgee.utils.HeadingToRotation;
import eu.nazgee.utils.IMoveTo;

public class Rocket extends Sprite {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	final IMoveTo mMoveTo;
	// ===========================================================
	// Constructors
	// ===========================================================
	public Rocket(final float pX, final float pY, final ITextureRegion pTextureRegion,
			final VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);

		mMoveTo = new BezzierMoveTo(this, new BezzierMoveTo.MovementParam(eConstraintType.VELOCITY, 200, 100));

		registerUpdateHandler(new HeadingToRotation(this));
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
		return mMoveTo.moveTo(pX, pY);
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