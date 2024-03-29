package eu.nazgee.props;

import org.andengine.entity.Entity;
import org.andengine.entity.particle.initializer.AlphaParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.ScaleParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ColorParticleModifier;
import org.andengine.entity.particle.modifier.ExpireParticleInitializer;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.opengl.GLES20;

public class RocketFlame extends BaseFlame {

	// ===========================================================
	// Constants
	// ===========================================================

	private final Rocket mRocket;

	// ===========================================================
	// Fields
	// ===========================================================
	// ===========================================================
	// Constructors
	// ===========================================================
	public RocketFlame(final Rocket pRocket, final ITextureRegion pTextureRegionThruster,
			final VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pRocket, pTextureRegionThruster, 10, 20, 120, pVertexBufferObjectManager);
		this.mRocket = pRocket;

		setBlendFunction(GLES20.GL_ONE, GLES20.GL_ONE);

		final float initialScale = 1.3f;
		addParticleInitializer(new ColorParticleInitializer<Entity>(1, 0.9f, 0.1f));
		addParticleInitializer(new ScaleParticleInitializer<Entity>(initialScale));
		addParticleInitializer(new AlphaParticleInitializer<Entity>(0));
		addParticleInitializer(new ExpireParticleInitializer<Entity>(6));

		addParticleModifier(new ScaleParticleModifier<Entity>(1, 2, initialScale, 1f));
		addParticleModifier(new ScaleParticleModifier<Entity>(2, 5, 1f, 2.0f));
		addParticleModifier(new ColorParticleModifier<Entity>(0, 0.5f, 1, 1, 0.9f, 0, 0.1f, 0)); // yellow->red
		addParticleModifier(new ColorParticleModifier<Entity>(0.5f, 1.0f, 1, 1, 0, 0.5f, 0, 0)); // red->orange
		addParticleModifier(new ColorParticleModifier<Entity>(1.5f, 2.0f, 1, 0.7f, 0.5f, 0.7f, 0, 0.7f)); // orange->gray
		addParticleModifier(new AlphaParticleModifier<Entity>(0, 0.1f, 0, 1)); // show
		addParticleModifier(new AlphaParticleModifier<Entity>(2.0f, 2.5f, 1, 0.5f)); // hide
		addParticleModifier(new AlphaParticleModifier<Entity>(4.5f, 6, 0.5f, 0)); // hide
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public void setRotation(float rotation) {
		super.setRotation(rotation);
		setParticlesSpawnEnabled(mRocket.isFlying());
	}
	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}