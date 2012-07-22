package eu.nazgee.props;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.particle.BatchedPseudoSpriteParticleSystem;
import org.andengine.entity.particle.emitter.BaseParticleEmitter;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.AlphaParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.ScaleParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ColorParticleModifier;
import org.andengine.entity.particle.modifier.ExpireParticleInitializer;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.math.MathUtils;

import android.opengl.GLES20;

public class RocketFlame extends BatchedPseudoSpriteParticleSystem {

	// ===========================================================
	// Constants
	// ===========================================================
	private final Rocket mRocket;
	private final RotationParticleInitializer<Entity> mRotationParticleInitializer;
	private final VelocityParticleInitializer<Entity> mVelocityParticleInitializer;

	// ===========================================================
	// Fields
	// ===========================================================
	// ===========================================================
	// Constructors
	// ===========================================================
	public RocketFlame(final Rocket pRocket, final ITextureRegion pTextureRegionThruster,
			final VertexBufferObjectManager pVertexBufferObjectManager) {
		super(new PointParticleEmitter(500, 500), 10, 20, 120, pTextureRegionThruster, pVertexBufferObjectManager);
		mRocket = pRocket;

		setBlendFunction(GLES20.GL_ONE, GLES20.GL_ONE);

		final float initialScale = 1.3f;
		addParticleInitializer(new ColorParticleInitializer<Entity>(1, 0, 0));
		addParticleInitializer(new ScaleParticleInitializer<Entity>(initialScale));
		addParticleInitializer(new AlphaParticleInitializer<Entity>(0));
		mVelocityParticleInitializer = new VelocityParticleInitializer<Entity>(-2, 2, 10, 20);
		addParticleInitializer(mVelocityParticleInitializer);
		mRotationParticleInitializer = new RotationParticleInitializer<Entity>(0.0f, 360.0f);
		addParticleInitializer(mRotationParticleInitializer);
		addParticleInitializer(new ExpireParticleInitializer<Entity>(6));

		addParticleModifier(new ScaleParticleModifier<Entity>(1, 2, initialScale, 1f));
		addParticleModifier(new ScaleParticleModifier<Entity>(2, 5, 1f, 2.0f));
		addParticleModifier(new ColorParticleModifier<Entity>(0, 1.0f, 1, 1, 0, 0.5f, 0, 0)); // red->orange
		addParticleModifier(new ColorParticleModifier<Entity>(1.5f, 2.0f, 1, 0.7f, 0.5f, 0.7f, 0, 0.7f)); // orange->gray
		addParticleModifier(new AlphaParticleModifier<Entity>(0, 0.1f, 0, 1)); // show
		addParticleModifier(new AlphaParticleModifier<Entity>(2.0f, 2.5f, 1, 0.5f)); // hide
		addParticleModifier(new AlphaParticleModifier<Entity>(4.5f, 6, 0.5f, 0)); // hide

		registerUpdateHandler(new FlamePositioner(this, pRocket));
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public void setRotation(float rotation) {
		final float range = 15;
		final float length = 20;
		rotation = 360 - rotation - 90;
		final float rot1 = MathUtils.degToRad(rotation - range);
		final float rot2 = MathUtils.degToRad(rotation + range);
		final float x1 = (float) (length * Math.cos(rot1));
		final float y1 = (float) (length * Math.sin(rot1));
		final float x2 = (float) (length * Math.cos(rot2));
		final float y2 = (float) (length * Math.sin(rot2));
		mVelocityParticleInitializer.setVelocity(Math.min(x1, x2), Math.max(x1, x2), Math.min(y1, y2), Math.max(y1, y2));

		setParticlesSpawnEnabled(mRocket.isFlying());
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	public class ParticleEmitterPositioner implements IUpdateHandler {
		// ===========================================================
		// Constants
		// ===========================================================
		public static final float MIN_SPEED_DEFAULT = 5;
		// ===========================================================
		// Fields
		// ===========================================================
		protected final Entity mEntityToTrack;
		protected final BaseParticleEmitter mEmitterToPosition;

		// ===========================================================
		// Constructors
		// ===========================================================

		public ParticleEmitterPositioner(final BaseParticleEmitter pEmitterToPosition, final Entity pEntityToTrack) {
			mEmitterToPosition = pEmitterToPosition;
			mEntityToTrack = pEntityToTrack;
		}

		// ===========================================================
		// Getter & Setter
		// ===========================================================

		// ===========================================================
		// Methods for/from SuperClass/Interfaces
		// ===========================================================
		@Override
		public void onUpdate(final float pSecondsElapsed) {
			mEmitterToPosition.setCenter(mEntityToTrack.getX(), mEntityToTrack.getY());
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

	public class FlamePositioner extends ParticleEmitterPositioner {
		private final RocketFlame mFlame;

		public FlamePositioner(final RocketFlame pFlame, final Entity pEntityToTrack) {
			super((BaseParticleEmitter) pFlame.getParticleEmitter(), pEntityToTrack);
			mFlame = pFlame;
		}

		@Override
		public void onUpdate(final float pSecondsElapsed) {
			super.onUpdate(pSecondsElapsed);
			mFlame.setRotation(mEntityToTrack.getRotation());
		}
	}
}