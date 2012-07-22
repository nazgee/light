package eu.nazgee.utils;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;

public abstract class BaseTracking implements IUpdateHandler {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	protected IEntity mEntityToMove;
	protected IEntity mEntityToFollow;
	// ===========================================================
	// Constructors
	// ===========================================================
	public BaseTracking(IEntity pEntityToMove, IEntity pEntityToFollow) {
		this.mEntityToMove = pEntityToMove;
		this.setEntityToFollow(pEntityToFollow);
	}
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public IEntity getEntityToMove() {
		return mEntityToMove;
	}
	public void setEntityToMove(IEntity mEntityToMove) {
		this.mEntityToMove = mEntityToMove;
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
