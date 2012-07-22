package eu.nazgee.utils;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.particle.ParticleSystem;

public abstract class BaseTrackingParticles implements IUpdateHandler {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	protected ParticleSystem<Entity> mParticlesToMove;
	protected IEntity mEntityToFollow;
	// ===========================================================
	// Constructors
	// ===========================================================
	public BaseTrackingParticles(ParticleSystem<Entity> pParticlesToMove, IEntity pEntityToFollow) {
		this.mParticlesToMove = pParticlesToMove;
		this.setEntityToFollow(pEntityToFollow);
	}
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public ParticleSystem<Entity> getParticlesToMove() {
		return mParticlesToMove;
	}
	public void setParticlesToMove(ParticleSystem<Entity> pPatriclesToMove) {
		this.mParticlesToMove = pPatriclesToMove;
	}
	public IEntity getEntityToFollow() {
		return mEntityToFollow;
	}
	public void setEntityToFollow(IEntity mEntityToFollow) {
		this.mEntityToFollow = mEntityToFollow;
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
