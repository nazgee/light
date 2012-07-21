package eu.nazgee.light;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveByModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
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
import org.andengine.util.modifier.ease.EaseStrongIn;
import org.andengine.util.modifier.ease.EaseStrongInOut;
import org.andengine.util.modifier.ease.EaseStrongOut;

import android.opengl.GLES20;
import eu.nazgee.utils.OffscreenFramebuffer;
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
	private OffscreenFramebuffer mFramebuffer;

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

		// ==== Prepare shadow 
		Sprite shadow = populateShadow(pTexturesLibrary, pCamera, pVertexBufferObject);
		attachChild(mFramebuffer);
		attachChild(shadow);

		SimpleTracker tracker = new SimpleTracker(mTorch, mRocket);
		registerUpdateHandler(tracker);

		// prepare Backgroung
		setBackground(new SpriteBackground(new Sprite(pCamera.getWidth()/2, pCamera.getHeight()/2, pCamera.getWidth(), pCamera.getHeight(), pTexturesLibrary.getBackground(), pVertexBufferObject)));
	}
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	private Sprite populateShadow(TexturesLibrary pTexturesLibrary, Camera pCamera,
			VertexBufferObjectManager pVertexBufferObject) {
		mFramebuffer = new OffscreenFramebuffer(pCamera.getWidth(), pCamera.getHeight(), mRenderTexture, Color.TRANSPARENT);

		mTorch = new Entity();
		mFramebuffer.attachChild(mTorch);
		Sprite lightray = new Sprite(0, 0, 200, 200, pTexturesLibrary.getLightRay(), pVertexBufferObject);
		lightray.setOffsetCenter(1, 0.5f);
		lightray.setRotationCenter(1, 0.5f);
		lightray.registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(
						new RotationModifier(0.5f, 0, -15, EaseStrongOut.getInstance()),
						new RotationModifier(1, -15, 15, EaseStrongInOut.getInstance()),
						new RotationModifier(0.5f, 15, 0, EaseStrongIn.getInstance()))));
		mTorch.attachChild(lightray);
		Sprite lightwave = new Sprite(0, 0, 200, 200, pTexturesLibrary.getLightShockwave(), pVertexBufferObject);
		lightwave.registerEntityModifier(new LoopEntityModifier(
				new ParallelEntityModifier(
						new SequenceEntityModifier(
								new ScaleModifier(2, 0, 8, EaseStrongIn.getInstance()),
								new DelayModifier(3)
								),
						new SequenceEntityModifier(
								new FadeInModifier(0.1f),
								new DelayModifier(0.9f),
								new FadeOutModifier(1),
								new DelayModifier(3)
								)
						)));
		mTorch.attachChild(lightwave);
		Sprite shadow = new Sprite(pCamera.getWidth()/2, pCamera.getHeight()/2, pCamera.getWidth(), pCamera.getHeight(), mFramebuffer.getTextureRegion(), pVertexBufferObject);
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

	@Override
	protected void onManagedDraw(GLState pGLState, Camera pCamera) {
		pGLState.pushProjectionGLMatrix();
		this.onApplyMatrix(pGLState, pCamera);
		pGLState.loadModelViewGLMatrixIdentity();
		mFramebuffer.onManagedDraw(pGLState, pCamera);
		pGLState.popProjectionGLMatrix();

		super.onManagedDraw(pGLState, pCamera);
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
