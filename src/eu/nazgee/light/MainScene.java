package eu.nazgee.light;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.UncoloredSprite;
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
	public MainScene(TexturesLibrary pTexturesLibrary, VertexBufferObjectManager pVertexBufferObject) {
		super();
		mRocket = new Rocket(100, 100, pTexturesLibrary.getRocket(), pVertexBufferObject);
		attachChild(mRocket);

		setOnSceneTouchListener(new IOnSceneTouchListener() {
			@Override
			public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
				mRocket.flyTo(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
				return true;
			}
		});
	
		mRenderTexture = pTexturesLibrary.getRenderTexture();
		Sprite dummy = new Sprite(mRenderTexture.getWidth()/2, mRenderTexture.getHeight()/2, pTexturesLibrary.getSun(), pVertexBufferObject) {
			
//			@Override
//			protected void preDraw(GLState pGLState, Camera pCamera) {
//				// TODO Auto-generated method stub
//				if (!mRenderTexture.isInitialized()) {
//					mRenderTexture.init(pGLState);
//				}
//				super.preDraw(pGLState, pCamera);
//				mRenderTexture.begin(pGLState, Color.TRANSPARENT);
//			}

//			@Override
//			protected void draw(GLState pGLState, Camera pCamera) {
//				if (!mRenderTexture.isInitialized()) {
//					mRenderTexture.init(pGLState);
//				}
//				mRenderTexture.begin(pGLState, Color.TRANSPARENT);
//				{
//					super.draw(pGLState, pCamera);
//				}
//				mRenderTexture.end(pGLState);
//			}

			@Override
			protected void onManagedDraw(GLState pGLState, Camera pCamera) {
				if (!mRenderTexture.isInitialized()) {
					mRenderTexture.init(pGLState);
				}
				mRenderTexture.begin(pGLState, Color.BLACK);
				super.onManagedDraw(pGLState, pCamera);
				mRenderTexture.end(pGLState);
			}

//			@Override
//			protected void postDraw(GLState pGLState, Camera pCamera) {
//				super.postDraw(pGLState, pCamera);
//				mRenderTexture.end(pGLState);
//				// TODO Auto-generated method stub
//			}

		};
		Sprite child1 = new Sprite(0, 0, pTexturesLibrary.getSun(), pVertexBufferObject);
		Sprite child2 = new Sprite(200, 200, pTexturesLibrary.getSun(), pVertexBufferObject);
		dummy.attachChild(child1);
		dummy.attachChild(child2);
		attachChild(dummy);

		Sprite pattern = new Sprite(500, 200, pTexturesLibrary.getSun(), pVertexBufferObject);
		attachChild(pattern);

		Sprite resultSprite = new Sprite(200, 200, TextureRegionFactory.extractFromTexture(mRenderTexture), pVertexBufferObject);
		attachChild(resultSprite);
	}
	// ===========================================================
	// Getter & Setter
	// ===========================================================

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
