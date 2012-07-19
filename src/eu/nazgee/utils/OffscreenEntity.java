package eu.nazgee.utils;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.opengl.texture.render.RenderTexture;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.color.Color;

public class OffscreenEntity extends Entity {
	// ===========================================================
	// Constants
	// ===========================================================
	private static final Color DEFAULT_CLEAR_COLOR = Color.TRANSPARENT;
	// ===========================================================
	// Fields
	// ===========================================================
	private final RenderTexture mRenderTexture;
	private final Color mClearColor;
	// ===========================================================
	// Constructors
	// ===========================================================

	public OffscreenEntity(float pX, float pY, final RenderTexture pRenderTexture) {
		super(pX, pY);
		this.mRenderTexture = pRenderTexture;
		this.mClearColor = DEFAULT_CLEAR_COLOR;
	}


	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	protected void onManagedDraw(GLState pGLState, Camera pCamera) {
		if (!mRenderTexture.isInitialized()) {
			mRenderTexture.init(pGLState);
		}

		{
			mRenderTexture.begin(pGLState, false, true, mClearColor);
			super.onManagedDraw(pGLState, pCamera);
			mRenderTexture.end(pGLState);
		}
	}
	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	}
