package eu.nazgee.utils;

import org.andengine.engine.handler.runnable.RunnableHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.util.modifier.IModifier;

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
	private ModifierListener mModifierListener = new ModifierListener();
	private AnimationListener mAnimationListener; 
	private RunnableHandler mRunnableHandler;
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

	public synchronized AnimationListener getAnimationListener() {
		return this.mAnimationListener;
	}

	public synchronized void setAnimationListener(final AnimationListener pAnimationListener, final RunnableHandler pRunnableHandler) {
		this.mAnimationListener = pAnimationListener;
		this.mRunnableHandler = pRunnableHandler;
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

		pModifier.removeModifierListener(mModifierListener);
		pModifier.addModifierListener(mModifierListener);

		pModifier.setAutoUnregisterWhenFinished(false);
		mEntity.unregisterEntityModifier(mAnimationModifier);
		mEntity.registerEntityModifier(pModifier);
		mAnimationModifier = pModifier;
	}
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	public static interface AnimationListener {
		public void onAnimationFinished(Animator pAnimator);
	}

	protected class ModifierListener implements IEntityModifierListener {
		@Override
		public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
		}
		@Override
		public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
			if (getAnimationListener() != null) {
				mRunnableHandler.postRunnable(new Runnable() {
					@Override
					public void run() {
						if (getAnimationListener() != null) {
							getAnimationListener().onAnimationFinished(Animator.this);
						}
					}
				});
			}
		}
	}

}