package eu.nazgee.utils;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier;

/**
 * This utility class is used to run a Modifier on a given Entity. When running a
 * modifier it takes care of unregistering an old modifier before registering a
 * new one, to avoid silly situations where similar/same effect is applied multiple
 * times on a single entity.
 * 
 * @author Michal Stawinski
 * 
 */
public class Animator {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private IEntity mEntity;
	private IEntityModifier mAnimationModifier;

	// ===========================================================
	// Constructors
	// ===========================================================
	public Animator() {
		this(null);
	}

	public Animator(final IEntity mEntity) {
		this.mEntity = mEntity;
	}


	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public synchronized IEntity getEntity() {
		return mEntity;
	}

	public synchronized void setEntity(final IEntity pNewEntity) {
		stop();
		mEntity = pNewEntity;
	}

	public synchronized boolean isAnimating() {
		return (mAnimationModifier != null && !mAnimationModifier.isFinished());
	}

	public synchronized IEntityModifier getModifier() {
		return mAnimationModifier;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	public synchronized void stop() {
		if (mEntity != null) {
			mEntity.unregisterEntityModifier(mAnimationModifier);
		}
	}

	public synchronized void run(final IEntityModifier pModifier) {
		if (mEntity == null) {
			return;
		}

		pModifier.setAutoUnregisterWhenFinished(false);
		mEntity.unregisterEntityModifier(mAnimationModifier);
		mEntity.registerEntityModifier(pModifier);
		mAnimationModifier = pModifier;
	}
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}