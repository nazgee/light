package eu.nazgee.light;

import org.andengine.engine.camera.Camera;
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
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

import android.opengl.GLES20;

import eu.nazgee.utils.OffscreenEntity;
import eu.nazgee.utils.OffscreenSprite;

public class MainScene extends Scene{
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private Rocket mRocket;
	private RenderTexture mRenderTexture8888;
	private RenderTexture mRenderTextureI8;

	// ===========================================================
	// Constructors
	// ===========================================================
	public MainScene(TexturesLibrary pTexturesLibrary, Camera pCamera, VertexBufferObjectManager pVertexBufferObject) {
		super();
		mRenderTexture8888 = pTexturesLibrary.getRenderTextureARGB8888();
		mRenderTextureI8 = mRenderTexture8888;

		// Prepare reference Sprite hierarchy, rendered in a traditional way 
		Sprite referenceHierarchy = new Sprite(500, 200, pTexturesLibrary.getSun(), pVertexBufferObject);
		attachReferencePlanets(referenceHierarchy, pTexturesLibrary, pVertexBufferObject);
		attachChild(referenceHierarchy);

		referenceHierarchy.registerEntityModifier(new LoopEntityModifier(new RotationModifier(25, 0, 360)));
		referenceHierarchy.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new MoveByModifier(5, 100, 0),
				new MoveByModifier(5, -100, 0)
				)));

		OffscreenEntity offscreenShadow = new OffscreenEntity(mRenderTextureI8.getWidth()/2, mRenderTextureI8.getHeight()/2, mRenderTextureI8);
		Sprite lightray = new Sprite(0,0, pTexturesLibrary.getLightRay(), pVertexBufferObject);
		lightray.registerEntityModifier(new LoopEntityModifier(new RotationModifier(10, 0, 360)));
		offscreenShadow.attachChild(lightray);
		attachChild(offscreenShadow);
		Sprite shadow = new Sprite(200, 200, TextureRegionFactory.extractFromTexture(mRenderTextureI8), pVertexBufferObject);
		attachChild(shadow);
		shadow.setBlendFunction(GLES20.GL_DST_COLOR, GLES20.GL_ZERO);

		// Prepare offscreen Sprite
		OffscreenSprite offscreen = new OffscreenSprite(mRenderTexture8888.getWidth()/2, mRenderTexture8888.getHeight()/2, mRenderTexture8888, pTexturesLibrary.getSun(), pVertexBufferObject);
		attachReferencePlanets(offscreen, pTexturesLibrary, pVertexBufferObject);
		attachChild(offscreen);

		// Prepare sprite which will display offscreen-generated stuff
		Sprite resultSprite = new Sprite(200, 200, TextureRegionFactory.extractFromTexture(mRenderTexture8888), pVertexBufferObject);
		attachChild(resultSprite);

		offscreen.registerEntityModifier(new LoopEntityModifier(new RotationModifier(25, 0, 360)));
		resultSprite.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new MoveByModifier(5, 100, 0),
				new MoveByModifier(5, -100, 0)
				)));

		// Prepare offscreen Entity
		OffscreenEntity offscreenEntity = new OffscreenEntity(mRenderTexture8888.getWidth()/2, mRenderTexture8888.getHeight()/2, mRenderTexture8888);
		Sprite offscrenSun = new Sprite(0, 0, pTexturesLibrary.getSun(), pVertexBufferObject);
		attachReferencePlanets(offscrenSun, pTexturesLibrary, pVertexBufferObject);
		offscreenEntity.attachChild(offscrenSun);
		attachChild(offscreenEntity);

		// Prepare sprite which will display offscreen-generated stuff
		Sprite resultSprite2 = new Sprite(400, 300, TextureRegionFactory.extractFromTexture(mRenderTexture8888), pVertexBufferObject);
		attachChild(resultSprite2);
		resultSprite2.setBlendFunction(GLES20.GL_ONE, GLES20.GL_ONE);

		offscreenEntity.registerEntityModifier(new LoopEntityModifier(new RotationModifier(5, 0, 360)));
		resultSprite2.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new MoveByModifier(5, 100, 0),
				new MoveByModifier(5, -100, 0)
				)));

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
	}
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	private void attachReferencePlanets(Sprite referenceSun, TexturesLibrary pTexturesLibrary, VertexBufferObjectManager pVertexBufferObject) {
		Sprite planet1 = new Sprite(0, 0, pTexturesLibrary.getPlanet(0), pVertexBufferObject);
		Sprite planet2 = new Sprite(200, 200, pTexturesLibrary.getPlanet(1), pVertexBufferObject);
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
