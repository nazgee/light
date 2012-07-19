package eu.nazgee.light;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.BaseResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

public class MainActivity extends BaseGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	TexturesLibrary mTexturesLibrary;
	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public EngineOptions onCreateEngineOptions() {
		final Camera camera = new Camera(0, 0, Globals.CAMERA_WIDTH, Globals.CAMERA_HEIGHT);
		BaseResolutionPolicy resolutionPolicy = new RatioResolutionPolicy((int) camera.getWidth(), (int) camera.getHeight());

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, resolutionPolicy, camera);
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {
		mTexturesLibrary = new TexturesLibrary(this);
		mTexturesLibrary.load(getEngine(), this);

		// push the Engine forward
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException {
		// TODO Auto-generated method stub
		MainScene scene = new MainScene(mTexturesLibrary, getEngine().getCamera(), getVertexBufferObjectManager());

		// push the Engine forward
		pOnCreateSceneCallback.onCreateSceneFinished(scene);
	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {

		// push the Engine forward
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}