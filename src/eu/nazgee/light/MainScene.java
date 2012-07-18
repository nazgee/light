package eu.nazgee.light;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class MainScene extends Scene{
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private Rocket mRocket;

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
