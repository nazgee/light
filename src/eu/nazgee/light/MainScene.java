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

public class MainScene extends Scene{
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private Rocket mRocket;
	private RenderTexture mRenderTexture;

	// ===========================================================
	// Constructors
	// ===========================================================
	public MainScene(TexturesLibrary pTexturesLibrary, Camera pCamera, VertexBufferObjectManager pVertexBufferObject) {
		super();
		mRenderTexture = pTexturesLibrary.getRenderTexture();
	
		// Prepare Sprite hierarchy that will be renderred offscreen to the Texture
		Sprite offscreenSun = new Sprite(mRenderTexture.getWidth()/2, mRenderTexture.getHeight()/2, pTexturesLibrary.getSun(), pVertexBufferObject) {
			@Override
			protected void onManagedDraw(GLState pGLState, Camera pCamera) {
				if (!mRenderTexture.isInitialized()) {
					mRenderTexture.init(pGLState);
				}

				{
					mRenderTexture.begin(pGLState, false, true, Color.TRANSPARENT);
					super.onManagedDraw(pGLState, pCamera);
					mRenderTexture.end(pGLState);
				}
			}
		};
		attachReferencePlanets(offscreenSun, pTexturesLibrary, pVertexBufferObject);
		attachChild(offscreenSun);

		// Prepare sprite which will display offscreen-generated hierarchy
		Sprite resultSprite = new Sprite(200, 200, TextureRegionFactory.extractFromTexture(mRenderTexture), pVertexBufferObject);
		attachChild(resultSprite);

		// Prepare reference Sprite hierarchy, rendered in a traditional way 
		Sprite referenceSun = populateReferenceHierarchy(pTexturesLibrary, pVertexBufferObject);
		attachChild(referenceSun);

		referenceSun.registerEntityModifier(new LoopEntityModifier(new RotationModifier(25, 0, 360)));
		referenceSun.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new MoveByModifier(5, 100, 0),
				new MoveByModifier(5, -100, 0)
				)));

		offscreenSun.registerEntityModifier(new LoopEntityModifier(new RotationModifier(25, 0, 360)));
		resultSprite.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
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

	private Sprite populateReferenceHierarchy(TexturesLibrary pTexturesLibrary, VertexBufferObjectManager pVertexBufferObject) {
		Sprite referenceSun = new Sprite(500, 200, pTexturesLibrary.getSun(), pVertexBufferObject);
		attachReferencePlanets(referenceSun, pTexturesLibrary, pVertexBufferObject);
		return referenceSun;
	}
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
