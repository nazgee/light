package eu.nazgee.light;

import org.andengine.engine.Engine;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.render.RenderTexture;
import org.andengine.util.adt.color.Color;
import org.andengine.util.debug.Debug;
import org.andengine.util.texturepack.TexturePack;
import org.andengine.util.texturepack.TexturePackLoader;
import org.andengine.util.texturepack.TexturePackTextureRegion;
import org.andengine.util.texturepack.exception.TexturePackParseException;

import android.content.Context;

public class TexturesLibrary {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private BitmapTextureAtlas mFontAtlas;
	private Font mFont;

	// needed to dynamically load some bigger textures which we do not want to
	// have always loaded
	private final Context mContext;
	private TexturePack mSpritesheetMain;
	private RenderTexture mRenderTexture;

	// ===========================================================
	// Constructors
	// ===========================================================
	public TexturesLibrary(final Context pContext) {
		mContext = pContext;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public RenderTexture getRenderTexture() {
		return mRenderTexture;
	}

	public TexturePackTextureRegion getRocket() {
		return mSpritesheetMain.getTexturePackTextureRegionLibrary().get(TextureMain.PROPS_ROCKET_ID);
	}

	public TexturePackTextureRegion getSun() {
		return mSpritesheetMain.getTexturePackTextureRegionLibrary().get(TextureMain.PROPS_SUN_ID);
	}
//
//	public ITiledTextureRegion getPropTeleport() {
//		return new TiledTextureRegion(mSpritesheetTextureETC1.getTexture(),
//				mSpritesheetTextureETC1.getTexturePackTextureRegionLibrary()
//						.get(TextureETC1.TELEPORT_1_ID),
//				mSpritesheetTextureETC1.getTexturePackTextureRegionLibrary()
//						.get(TextureETC1.TELEPORT_2_ID),
//				mSpritesheetTextureETC1.getTexturePackTextureRegionLibrary()
//						.get(TextureETC1.TELEPORT_3_ID),
//				mSpritesheetTextureETC1.getTexturePackTextureRegionLibrary()
//						.get(TextureETC1.TELEPORT_4_ID),
//				mSpritesheetTextureETC1.getTexturePackTextureRegionLibrary()
//						.get(TextureETC1.TELEPORT_5_ID),
//				mSpritesheetTextureETC1.getTexturePackTextureRegionLibrary()
//						.get(TextureETC1.TELEPORT_6_ID),
//				mSpritesheetTextureETC1.getTexturePackTextureRegionLibrary()
//						.get(TextureETC1.TELEPORT_7_ID),
//				mSpritesheetTextureETC1.getTexturePackTextureRegionLibrary()
//						.get(TextureETC1.TELEPORT_8_ID));
//	}

	public static int moduloBounds(int value, final int max) {
		value %= (max);
		value = Math.abs(value);
		return value;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	public void load(final Engine e, final Context c) {
		// Prepare a reloaders that will store and reload big texture atlasses
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		FontFactory.setAssetBasePath("fonts/");

		// Load spritesheet
		try {
			TexturePackLoader tploader = new TexturePackLoader(c.getAssets(), e.getTextureManager());
			mSpritesheetMain = tploader.loadFromAsset("spritesheets/TextureMain.xml", "spritesheets/");
			mSpritesheetMain.loadTexture();
		} catch (final TexturePackParseException ex) {
			Debug.e(ex);
		}

		// Load fonts
		final TextureManager textureManager = e.getTextureManager();
		final FontManager fontManager = e.getFontManager();

		mFontAtlas = new BitmapTextureAtlas(textureManager, 256, 256, TextureOptions.BILINEAR);
		mFont = FontFactory.createFromAsset(fontManager, mFontAtlas, c.getAssets(), Globals.FONT_NAME, Globals.FONT_SIZE, true,
				Color.WHITE.getARGBPackedInt());
		mFont.load();

		mRenderTexture = new RenderTexture(e.getTextureManager(), Globals.CAMERA_WIDTH, Globals.CAMERA_HEIGHT);
	}

	public void unload() {
		mSpritesheetMain.unloadTexture();
		mFont.unload();
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}