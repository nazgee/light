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
	private RenderTexture mRT;

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
	
		mRT = pTexturesLibrary.getRenderTexture();
		Sprite dummy = new Sprite(mRT.getWidth()/2, mRT.getHeight()/2, pTexturesLibrary.getSun(), pVertexBufferObject) {
			@Override
			protected void draw(GLState pGLState, Camera pCamera) {
				if (!mRT.isInitialized()) {
					mRT.init(pGLState);
				}
				mRT.begin(pGLState, Color.TRANSPARENT);
				{
					super.draw(pGLState, pCamera);
				}
				mRT.end(pGLState);
			}
		};
		Sprite child1 = new Sprite(0, 0, pTexturesLibrary.getSun(), pVertexBufferObject);
		Sprite child2 = new Sprite(200, 200, pTexturesLibrary.getSun(), pVertexBufferObject);
		dummy.attachChild(child1);
		dummy.attachChild(child2);
		attachChild(dummy);

		Sprite resultSprite = new Sprite(200, 200, TextureRegionFactory.extractFromTexture(mRT), pVertexBufferObject);
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
