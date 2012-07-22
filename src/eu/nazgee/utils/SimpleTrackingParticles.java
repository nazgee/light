package eu.nazgee.utils;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.particle.ParticleSystem;
import org.andengine.entity.particle.emitter.BaseParticleEmitter;

public class SimpleTrackingParticles extends BaseTrackingParticles {
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

	public SimpleTrackingParticles(ParticleSystem<Entity> pParticlesToMover, IEntity pEntityToFollow) {
		super(pParticlesToMover, pEntityToFollow);
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public void onUpdate(float pSecondsElapsed) {
		// simple move around (no rotation)
		mEntityToFollow.getSceneCenterCoordinates(mPosReuse1);

		BaseParticleEmitter emitter = (BaseParticleEmitter) mParticlesToMove.getParticleEmitter();
		emitter.setCenterX(mEntityToFollow.getX());
		emitter.setCenterY(mEntityToFollow.getY());

		//emitter.setRotation(mEntityToFollow.getRotation() + 90);

//		mEmitterToMove.setCenterX(mPosReuse2[Constants.VERTEX_INDEX_X]);
//		mEmitterToMove.setCenterY(mPosReuse2[Constants.VERTEX_INDEX_Y]);
//
//		mEmitterToMove.getParent().convertSceneCoordinatesToLocalCoordinates(mPosReuse1, mPosReuse2);
//		mEmitterToMove.setPosition(mPosReuse2[Constants.VERTEX_INDEX_X], mPosReuse2[Constants.VERTEX_INDEX_Y]);
//		mEmitterToMove.setRotation(mEntityToFollow.getRotation() + 90);
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
