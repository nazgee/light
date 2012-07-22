package eu.nazgee.light;

import org.andengine.engine.Engine;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.texture.PixelFormat;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
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
	private static final int TEXTURE_FIRST_PLANET = TextureMain.PROPS_SURFACE_01_ID;
	private static final int TEXTURE_COUNT_PLANET = 3;
	// ===========================================================
	// Fields
	// ===========================================================
	private BitmapTextureAtlas mFontAtlas;
	private Font mFont;

	// needed to dynamically load some bigger textures which we do not want to
	// have always loaded
	private final Context mContext;
	private TexturePack mSpritesheetMain;
	private RenderTexture mRenderTextureARGB8888;
	private BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlasARGB8888;
	private BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlasI8;
	private TextureRegion mBoxFaceTextureRegion;
	private TextureRegion mLightRay;

	// ===========================================================
	// Constructors
	// ===========================================================
	public TexturesLibrary(final Context pContext) {
		mContext = pContext;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public ITextureRegion getLightRay() {
		return mLightRay;
	}

	public ITextureRegion getLightShockwave() {
		return mSpritesheetMain.getTexturePackTextureRegionLibrary().get(TextureMain.LIGHT_SHOCKWAVE_ID);
	}

	public ITextureRegion getLightFlames() {
		return mSpritesheetMain.getTexturePackTextureRegionLibrary().get(TextureMain.LIGHT_RADIAL_ID);
	}

	public ITextureRegion getRocketFlames() {
		return mSpritesheetMain.getTexturePackTextureRegionLibrary().get(TextureMain.MISC_BURST_ID);
	}

	public ITextureRegion getBoxFace() {
		return mBoxFaceTextureRegion;
	}

	public RenderTexture getRenderTextureARGB8888() {
		return mRenderTextureARGB8888;
	}

	public TexturePackTextureRegion getRocket() {
		return mSpritesheetMain.getTexturePackTextureRegionLibrary().get(TextureMain.PROPS_ROCKET_ID);
	}

	public TexturePackTextureRegion getSun() {
		return mSpritesheetMain.getTexturePackTextureRegionLibrary().get(TextureMain.PROPS_SUN_ID);
	}

	public TexturePackTextureRegion getPlanet(int mPlanetNumber) {
		return mSpritesheetMain.getTexturePackTextureRegionLibrary().get(TEXTURE_FIRST_PLANET + moduloBounds(mPlanetNumber, TEXTURE_COUNT_PLANET));
	}

	public ITextureRegion getBackground() {
		return mSpritesheetMain.getTexturePackTextureRegionLibrary().get(TextureMain.BG_BG_ID);
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

		// Load ARGB8888 textures
		this.mBuildableBitmapTextureAtlasARGB8888 = new BuildableBitmapTextureAtlas(e.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		this.mBoxFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlasARGB8888, c, "face_box.png");
//		this.mLightRay = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlasARGB8888, c, "light.png");

		try {
			this.mBuildableBitmapTextureAtlasARGB8888.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
			this.mBuildableBitmapTextureAtlasARGB8888.load();
		} catch (TextureAtlasBuilderException ex) {
			Debug.e(ex);
		}

		// Load I8  textures
		this.mBuildableBitmapTextureAtlasI8 = new BuildableBitmapTextureAtlas(e.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		this.mLightRay = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlasI8, c, "light.png");

		try {
			this.mBuildableBitmapTextureAtlasI8.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
			this.mBuildableBitmapTextureAtlasI8.load();
		} catch (TextureAtlasBuilderException ex) {
			Debug.e(ex);
		}

		// Load fonts
		final TextureManager textureManager = e.getTextureManager();
		final FontManager fontManager = e.getFontManager();

		mFontAtlas = new BitmapTextureAtlas(textureManager, 256, 256, TextureOptions.BILINEAR);
		mFont = FontFactory.createFromAsset(fontManager, mFontAtlas, c.getAssets(), Globals.FONT_NAME, Globals.FONT_SIZE, true,
				Color.WHITE.getARGBPackedInt());
		mFont.load();

		// Prepare render textures
		mRenderTextureARGB8888 = new RenderTexture(e.getTextureManager(), 256, 128, PixelFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
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