package eu.nazgee.utils;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.render.RenderTexture;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.color.Color;

public class OffscreenFramebuffer extends Entity {
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

	public OffscreenFramebuffer(final float pWidth, final float pHeight, final RenderTexture pRenderTexture) {
		this(pWidth, pHeight, pRenderTexture, DEFAULT_CLEAR_COLOR);
	}

	public OffscreenFramebuffer(final float pWidth, final float pHeight, final RenderTexture pRenderTexture, final Color pClearColor) {
		super(pWidth/2, pHeight/2, pWidth, pHeight);
		this.mRenderTexture = pRenderTexture;
		this.mClearColor = pClearColor;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public ITextureRegion getTextureRegion() {
		return TextureRegionFactory.extractFromTexture(mRenderTexture);
	}
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

			final float scaleX = mRenderTexture.getWidth()/getWidth();
			final float scaleY = mRenderTexture.getHeight()/getHeight();
			pGLState.scaleProjectionGLMatrixf(scaleX, scaleY, 1);

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
