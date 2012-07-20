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
		this(pX, pY, 0, 0, pRenderTexture);
	}

	public OffscreenEntity(float pX, float pY, final float pWidth, final float pHeight, final RenderTexture pRenderTexture) {
		super(pX, pY, pWidth, pHeight);
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
//			pGLState.pushProjectionGLMatrix();
//			pGLState.pushModelViewGLMatrix();
			mRenderTexture.begin(pGLState, false, true, mClearColor);
//			pGLState.orthoProjectionGLMatrixf(0, getWidth(), 0, getHeight(), -1, 1);
//			pGLState.orthoModelViewGLMatrixf(0, getWidth(), 0, getHeight(), -1, 1);
			super.onManagedDraw(pGLState, pCamera);
			mRenderTexture.end(pGLState);
//			pGLState.popModelViewGLMatrix();
//			pGLState.popProjectionGLMatrix();
		}
	}
	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	}
