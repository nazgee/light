package eu.nazgee.utils;

import org.andengine.entity.IEntity;
import org.andengine.entity.particle.emitter.BaseParticleEmitter;

import eu.nazgee.props.BaseFlame;

public class SimpleTrackingFlames extends SimpleTrackingParticles {
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

	public SimpleTrackingFlames(BaseFlame pParticlesToMover, IEntity pEntityToFollow) {
		super(pParticlesToMover, pEntityToFollow);
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public void onUpdate(float pSecondsElapsed) {
		super.onUpdate(pSecondsElapsed);

		mParticlesToMove.setRotation(mEntityToFollow.getRotation() + 90);
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
