package eu.nazgee.utils;

import org.andengine.entity.IEntity;
import org.andengine.util.Constants;
import org.andengine.util.adt.transformation.Transformation;

public class SimpleTracker extends BaseTracker {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	final float[] mPosReuse1 = new float[2];
	final float[] mPosReuse2 = new float[2];
	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public SimpleTracker(IEntity pEntityToMove, IEntity pEntityToFollow) {
		super(pEntityToMove, pEntityToFollow);
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public void onUpdate(float pSecondsElapsed) {
		// simple move around (no rotation)
		mEntityToFollow.getSceneCenterCoordinates(mPosReuse1);
		mEntityToMove.getParent().convertSceneCoordinatesToLocalCoordinates(mPosReuse1, mPosReuse2);
		mEntityToMove.setPosition(mPosReuse2[Constants.VERTEX_INDEX_X], mPosReuse2[Constants.VERTEX_INDEX_Y]);

		mEntityToMove.setRotation(mEntityToFollow.getRotation() + 90);
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
