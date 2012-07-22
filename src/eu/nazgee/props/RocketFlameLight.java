package eu.nazgee.props;

import org.andengine.entity.Entity;
import org.andengine.entity.particle.initializer.AlphaParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.ScaleParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ExpireParticleInitializer;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.shape.IShape;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class RocketFlameLight extends BaseFlame {

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
	public RocketFlameLight(final Rocket pRocket, final ITextureRegion pTextureRegionThruster,
			final VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pRocket, pTextureRegionThruster, 7, 10, 40, pVertexBufferObjectManager);
		mRocket = pRocket;

		setBlendFunction(IShape.BLENDFUNCTION_SOURCE_DEFAULT, IShape.BLENDFUNCTION_DESTINATION_DEFAULT);

		final float initialScale = 2f;
		addParticleInitializer(new ScaleParticleInitializer<Entity>(initialScale));
		addParticleInitializer(new AlphaParticleInitializer<Entity>(0));
		addParticleInitializer(new ExpireParticleInitializer<Entity>(3));
		addParticleInitializer(new ColorParticleInitializer<Entity>(1, 0.5f, 0.1f));

		addParticleModifier(new ScaleParticleModifier<Entity>(0f, 2, initialScale, 0.5f));
		addParticleModifier(new AlphaParticleModifier<Entity>(0f, 0.25f, 0f, 1f)); // show
		addParticleModifier(new AlphaParticleModifier<Entity>(0.25f, 2.8f, 1f, 0f)); // hide
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