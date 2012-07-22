package eu.nazgee.props;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.particle.BatchedPseudoSpriteParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.math.MathUtils;

import eu.nazgee.utils.SimpleTrackingFlames;

public class BaseFlame extends BatchedPseudoSpriteParticleSystem {

	// ===========================================================
	// Constants
	// ===========================================================
	private final RotationParticleInitializer<Entity> mRotationParticleInitializer;
	private final VelocityParticleInitializer<Entity> mVelocityParticleInitializer;

	// ===========================================================
	// Fields
	// ===========================================================
	// ===========================================================
	// Constructors
	// ===========================================================
	public BaseFlame(final IEntity pEntity, final ITextureRegion pTextureRegionThruster,
			final float pRateMinimum, final float pRateMaximum, final int pParticlesMaximum,
			final VertexBufferObjectManager pVertexBufferObjectManager) {
		super(new PointParticleEmitter(500, 500), pRateMinimum, pRateMaximum, pParticlesMaximum, pTextureRegionThruster, pVertexBufferObjectManager);

		mVelocityParticleInitializer = new VelocityParticleInitializer<Entity>(-2, 2, 10, 20);
		addParticleInitializer(mVelocityParticleInitializer);
		mRotationParticleInitializer = new RotationParticleInitializer<Entity>(0.0f, 360.0f);
		addParticleInitializer(mRotationParticleInitializer);

		registerUpdateHandler(new SimpleTrackingFlames(this, pEntity));
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	public void setThrustRotation(float rotation, final float rotationRange, final float thrustLength) {
		rotation = 360 - rotation - 90;
		final float rot1 = MathUtils.degToRad(rotation - rotationRange);
		final float rot2 = MathUtils.degToRad(rotation + rotationRange);
		final float x1 = (float) (thrustLength * Math.cos(rot1));
		final float y1 = (float) (thrustLength * Math.sin(rot1));
		final float x2 = (float) (thrustLength * Math.cos(rot2));
		final float y2 = (float) (thrustLength * Math.sin(rot2));
		mVelocityParticleInitializer.setVelocity(Math.min(x1, x2), Math.max(x1, x2), Math.min(y1, y2), Math.max(y1, y2));
	}

	// ===========================================================
	// Methods
	// ===========================================================
//	protected abstract void updateRotation(final float pEntityRotation) {
//		
//	}
//
//	protected abstract void updatePosition(final float pEntityRotation) {
//		
//	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
//	public class ParticleEmitterPositioner implements IUpdateHandler {
//		// ===========================================================
//		// Constants
//		// ===========================================================
//
//		// ===========================================================
//		// Fields
//		// ===========================================================
//		protected final Entity mEntityToTrack;
//		protected final BaseParticleEmitter mEmitterToPosition;
//
//		// ===========================================================
//		// Constructors
//		// ===========================================================
//
//		public ParticleEmitterPositioner(final BaseParticleEmitter pEmitterToPosition, final Entity pEntityToTrack) {
//			mEmitterToPosition = pEmitterToPosition;
//			mEntityToTrack = pEntityToTrack;
//		}
//
//		// ===========================================================
//		// Getter & Setter
//		// ===========================================================
//
//		// ===========================================================
//		// Methods for/from SuperClass/Interfaces
//		// ===========================================================
//		@Override
//		public void onUpdate(final float pSecondsElapsed) {
//			mEmitterToPosition.setCenter(mEntityToTrack.getX(), mEntityToTrack.getY());
//		}
//
//		@Override
//		public void reset() {
//		}
//		// ===========================================================
//		// Methods
//		// ===========================================================
//
//		// ===========================================================
//		// Inner and Anonymous Classes
//		// ===========================================================
//	}
//
//	public class FlamePositioner extends ParticleEmitterPositioner {
//		private final BaseFlame mFlame;
//
//		public FlamePositioner(final BaseFlame pFlame, final Entity pEntityToTrack) {
//			super((BaseParticleEmitter) pFlame.getParticleEmitter(), pEntityToTrack);
//			mFlame = pFlame;
//		}
//
//		@Override
//		public void onUpdate(final float pSecondsElapsed) {
//			super.onUpdate(pSecondsElapsed);
//			mFlame.setRotation(mEntityToTrack.getRotation());
//		}
//	}
}