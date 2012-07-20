package eu.nazgee.light;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveByModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.render.RenderTexture;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.ease.EaseStrongIn;
import org.andengine.util.modifier.ease.EaseStrongInOut;
import org.andengine.util.modifier.ease.EaseStrongOut;

import android.opengl.GLES20;
import eu.nazgee.utils.OffscreenFramebuffer;
import eu.nazgee.utils.OffscreenSprite;
import eu.nazgee.utils.SimpleTracker;

public class MainScene extends Scene{
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private Rocket mRocket;
	private RenderTexture mRenderTexture;
	private IEntity mTorch;

	// ===========================================================
	// Constructors
	// ===========================================================
	public MainScene(TexturesLibrary pTexturesLibrary, Camera pCamera, VertexBufferObjectManager pVertexBufferObject) {
		super();
		mRenderTexture = pTexturesLibrary.getRenderTextureARGB8888();


		// ==== Prepare reference Sprite hierarchy, rendered in a traditional way 
		Sprite referenceHierarchy = new Sprite(pCamera.getWidth() * 0.8f, pCamera.getHeight() * 0.25f, pTexturesLibrary.getSun(), pVertexBufferObject);
		attachReferencePlanets(referenceHierarchy, pTexturesLibrary, pVertexBufferObject);
		attachChild(referenceHierarchy);

		referenceHierarchy.registerEntityModifier(new LoopEntityModifier(new RotationModifier(25, 0, 360)));
		referenceHierarchy.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new MoveByModifier(5, 100, 0),
				new MoveByModifier(5, -100, 0)
				)));

		// ==== Prepare offscreen Sprite
		OffscreenSprite offscreen = new OffscreenSprite(mRenderTexture.getWidth()/2, mRenderTexture.getHeight()/2, mRenderTexture, pTexturesLibrary.getSun(), pVertexBufferObject);
		attachReferencePlanets(offscreen, pTexturesLibrary, pVertexBufferObject);
		attachChild(offscreen);
		// Prepare sprite which will display offscreen-generated stuff
		Sprite resultSprite = new Sprite(pCamera.getWidth() * 0.8f, pCamera.getHeight() * 0.75f, TextureRegionFactory.extractFromTexture(mRenderTexture), pVertexBufferObject);
		attachChild(resultSprite);
		offscreen.registerEntityModifier(new LoopEntityModifier(new RotationModifier(25, 0, 360)));
		resultSprite.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new MoveByModifier(5, 100, 0),
				new MoveByModifier(5, -100, 0)
				)));

		// ==== Prepare shadow 
		Sprite shadow = populateShadow(pTexturesLibrary, pCamera, pVertexBufferObject);
		attachChild(shadow);

		// prepare Backgroung
		setBackground(new SpriteBackground(new Sprite(pCamera.getWidth()/2, pCamera.getHeight()/2, pCamera.getWidth(), pCamera.getHeight(), pTexturesLibrary.getBackground(), pVertexBufferObject)));

		// prepare a Rocket
		mRocket = new Rocket(100, 100, pTexturesLibrary.getRocket(), pVertexBufferObject);
		attachChild(mRocket);

		setOnSceneTouchListener(new IOnSceneTouchListener() {
			@Override
			public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
				mRocket.flyTo(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
				return true;
			}
		});

		SimpleTracker tracker = new SimpleTracker(mTorch, mRocket);
		registerUpdateHandler(tracker);
	}
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	private Sprite populateShadow(TexturesLibrary pTexturesLibrary, Camera pCamera,
			VertexBufferObjectManager pVertexBufferObject) {
		OffscreenFramebuffer framebuffer = new OffscreenFramebuffer(pCamera.getWidth(), pCamera.getHeight(), mRenderTexture);
		attachChild(framebuffer);
		mTorch = new Entity();
		framebuffer.attachChild(mTorch);
		Sprite lightray = new Sprite(0, 0, 200, 200, pTexturesLibrary.getLightRay(), pVertexBufferObject);
		lightray.setOffsetCenter(1, 0.5f);
		lightray.setRotationCenter(1, 0.5f);
		lightray.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(
						new RotationModifier(0.5f, 0, -15, EaseStrongOut.getInstance()),
						new RotationModifier(1, -15, 15, EaseStrongInOut.getInstance()),
						new RotationModifier(0.5f, 15, 0, EaseStrongIn.getInstance()))));
		mTorch.attachChild(lightray);
		Sprite shadow = new Sprite(pCamera.getWidth()/2, pCamera.getHeight()/2, pCamera.getWidth(), pCamera.getHeight(), TextureRegionFactory.extractFromTexture(mRenderTexture), pVertexBufferObject);
		shadow.setBlendFunction(GLES20.GL_DST_COLOR, GLES20.GL_ZERO);
		return shadow;
	}

	private void attachReferencePlanets(Sprite referenceSun, TexturesLibrary pTexturesLibrary, VertexBufferObjectManager pVertexBufferObject) {
		final float w = referenceSun.getWidth();
		final float h = referenceSun.getWidth();
		Sprite planet1 = new Sprite(0.3f * w, 0.3f * w, pTexturesLibrary.getPlanet(0), pVertexBufferObject);
		Sprite planet2 = new Sprite(0.7f * w, 0.7f * h, pTexturesLibrary.getPlanet(1), pVertexBufferObject);
		referenceSun.attachChild(planet1);
		referenceSun.attachChild(planet2);
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
