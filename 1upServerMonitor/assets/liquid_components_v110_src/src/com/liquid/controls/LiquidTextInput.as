/**
*
*
*	LiquidTextInput
*	
* 	This class is distributed under Creative Commons License.
* 	http://creativecommons.org/licenses/by/2.0/fr/
*	
*	@author		Didier Brun
*	@version	1.1
* 	@link		http://www.bytearray.org
*
*/

package com.liquid.controls{
	
	import com.liquid.skin.SkinLoader;
	import fl.core.InvalidationType;
	import fl.controls.TextInput;
	import fl.core.UIComponent;
	import flash.display.BitmapData;
	import flash.events.Event;
	import flash.events.EventPhase;
	import flash.system.LoaderContext;
	import flash.text.TextFormat;
	
	import com.liquid.core.SkinBox;
	import com.liquid.core.ILiquidComponent;
	import com.liquid.core.LiquidManager;
	import com.liquid.skin.SkinAsset;
	import com.liquid.skin.SkinLoader;
	import com.liquid.events.LiquidEvent;
	
	/**
	 * @author Didier Brun
	 * @version 1.0
	 */
	public class LiquidTextInput extends TextInput {

		// ------------------------------------------------
		//
		// ---o inspectable properties
		//
		// ------------------------------------------------
		
		
		[Inspectable (defaultValue = "LiquidTextInput_back", name = "::skin back")]
		/**
		 * @private
		 */
		public var _pSkinBack:String = "LiquidTextInput_back";
		
		[Inspectable (defaultValue = "", name = "::load back")]
		/**
		 * @private
		 */
		public var _pLoadBack:String = "";
		
		// ------------------------------------------------
		//
		// ---o properties
		//
		// ------------------------------------------------
		
		private static const SKIN_UP:int = 0;
		private static const SKIN_DISABLED:int = 1;

		private var _skinBack:SkinBox;								// background
			
		private static var _instances:Array = [];
		
		// ------------------------------------------------
		//
		// ---o constructor
		//
		// ------------------------------------------------
		
		function LiquidTextInput(){
			
			super();
						
			// register component
			LiquidManager.register(this);
			
			// init
			initializeLiquid();
			
			// instances
			_instances.push(this);
			
		}
		
		// ------------------------------------------------
		//
		// ---o public methods
		//
		// ------------------------------------------------
		
		/**
		 * Set a BitmapData skin for the LiquidTextInput instance.
		 * 
		 * @example 	<listing version="3.0" >myTextInput.setSkin("back",myBitmapId(0,0));</listing> 
		 * 
		 * @param id	String indentifier, this property take the folowing value :
		 * <p><table class=innertable>
		 * <tr><th>id</th><th>Description</th></tr>
		 * <tr><td>back</td><td>The background of the text input</td></tr>
		 * </table></p>
		 * @param bmp	BitmapData instance
		 */
		public function setSkin(id:String, bmp:BitmapData):void {
				
			switch (id) {
				case "back":
					_pLoadBack = "";
					_pSkinBack = "";
					skin_back(bmp);
				break;
			}
		}
		
		/**
		 * Load an external bitmap skin file and apply it to the LiquidTextInput instance.
		 * 
		 * @example 	<listing version="3.0" >myTextInput.loadSkin("back","./skin/mySkin.png");</listing> 
		 * 
		 * @param id	String indentifier, this property take the folowing value :
		 * <p><table class=innertable>
		 * <tr><th>id</th><th>Description</th></tr>
		 * <tr><td>back</td><td>The background of the text input.</td></tr>
		 * </table></p>
		 * @param url		URL of the bitmap file
		 * @param context 	A LoaderContext object, which has properties that define the following :
  
  <ul>
  
  <li>Whether or not Flash Player should check for the existence of a policy file 
  upon loading the object</li>

  
  <li>The ApplicationDomain for the loaded object</li>
  
  <li>The SecurityDomain for the loaded object</li>
  
  </ul>
  
  <p>For complete details, see the description of the properties in the 
  <a target='_blank' href="http://livedocs.adobe.com/flash/9.0/ActionScriptLangRefV3/flash/system/LoaderContext.html">LoaderContext</a> class.</p>
		 */
		public function loadSkin(id:String, url:String, context:LoaderContext = null):void {
			
			if (isLivePreview) return;
			
			switch (id) {
				case "back":
					_pLoadBack = "";
					_pSkinBack = "";
					load_back(url, context);
				break;
			}
		}

		// ------------------------------------------------
		//
		// ---o protected methods
		//
		// ------------------------------------------------
		
		/**
		 * configUI
		 * @private
		 */
		override protected function configUI():void {
			
			super.configUI();

		}
		
		/**
		 * draw background
		 * @private
		 */
		override protected function drawBackground():void {
			
			// add background
			if (background == null) {
				background = _skinBack;
				addChildAt(background, 0);
			}
										
			var _state:int = SKIN_UP;
			if (!enabled)_state = SKIN_DISABLED;
				
			_skinBack.state = _state;
			
			// tf
			var tf:TextFormat = getStyleValue("textFormat") as TextFormat;
			tf.color = _skinBack.getColor();
			drawTextFormat();
		}
		
		/**
		 * initialize the liquid component
		 * @private
		 */
		protected function initializeLiquid():void {
			
			// skin
			_skinBack = new SkinBox(width, height);
					
			setStyle("upSkin", _skinBack);
			setStyle("disabledSkin", _skinBack);
			setStyle("focusRectSkin", LiquidManager.getFocusSkinBox());
						
			// added
			addEventListener(Event.ADDED, addedHandler);
		}
		
		/**
		 * added handler
		 * @private
		 */
		protected function addedHandler(e:Event):void {
			if (e.eventPhase==EventPhase.AT_TARGET){
				addEventListener(Event.ENTER_FRAME, renderHandler);
				addEventListener(Event.RENDER, renderHandler);
			}
		}
		
		/**
		 * render handler
		 * @private
		 */
		protected function renderHandler(e:Event):void {
			var cmp:LiquidTextInput = e.target as LiquidTextInput;
			cmp.removeEventListener(Event.RENDER, renderHandler);
			cmp.removeEventListener(Event.ENTER_FRAME, renderHandler)
			
			initSkin();
		}
		
		/**
		 * init skin
		 * @private
		 */
		protected function initSkin():void {
			
			if (!_skinBack.isReady && _pLoadBack == "") {
				_skinBack.setSkin(SkinAsset.getBitmapData(_pSkinBack));
			}
			
			if (_pLoadBack != "") {
				loadSkin("back", _pLoadBack);
			}
			
			invalidate(InvalidationType.SIZE);
			drawNow();
		}
		
		// ------------------------------------------------
		//
		// ---o private methods
		//
		// ------------------------------------------------
		
		/**
		 * skin back
		 */
		private function skin_back(bmp:BitmapData):void {
			if (bmp != null) {
				_skinBack.setSkin(bmp);
			}
		}
		
		/**
		 * load back
		 */
		private function load_back(url:String, context:LoaderContext = null):void {
			if (url != "") {
				var sl:SkinLoader = SkinAsset.loadSkin(url, context);
				if (sl.getState() == SkinLoader.STATE_LOADED) {
					
					_skinBack.setSkin(sl.getBitmap());
					_skinBack.drawNow();
					drawBackground();
				}else{
					sl.addEventListener(LiquidEvent.SKIN_LOADED, back_loaded);
				}
			}
		}
		
		
		/**
		 * back loaded
		 */
		private function back_loaded(e:Event):void {
			var sl:SkinLoader = e.target as SkinLoader;
			sl.removeEventListener(LiquidEvent.SKIN_LOADED, back_loaded);
			
			_skinBack.setSkin(sl.getBitmap());
			_skinBack.drawNow();
				
			drawBackground();
		}
		
		
		// ------------------------------------------------
		//
		// ---o static methods
		//
		// ------------------------------------------------
		
		/**
		 * Set a BitmapData skin for all LiquidTextInput instances.
		 * 
		 * @example 	<listing version="3.0" >LiquidTextInput.setSkin("back",myBitmapId(0,0));</listing> 
		 * 
		 * @param id	String indentifier, this property take the folowing value :
		 * <p><table class=innertable>
		 * <tr><th>id</th><th>Description</th></tr>
		 * <tr><td>back</td><td>The background of the text input</td></tr>
		 * </table></p>
		 * @param bmp	BitmapData instance
		 */
		public static function setDefaultSkin(id:String, bmp:BitmapData):void {
			var nb:int = _instances.length;
			while (--nb >= 0)_instances[nb].setSkin(id,bmp);
		}
		
		/**
		 * Load an external bitmap skin file and apply it to all LiquidTextInput instances.
		 * 
		 * @example 	<listing version="3.0" >LiquidTextInput.loadSkin("back","./skin/mySkin.png");</listing> 
		 * 
		 * @param id	String indentifier, this property take the folowing value :
		 * <p><table class=innertable>
		 * <tr><th>id</th><th>Description</th></tr>
		 * <tr><td>back</td><td>The background of the text input.</td></tr>
		 * </table></p>
		 * @param url		URL of the bitmap file
		 * @param context 	A LoaderContext object, which has properties that define the following :
  
  <ul>
  
  <li>Whether or not Flash Player should check for the existence of a policy file 
  upon loading the object</li>

  
  <li>The ApplicationDomain for the loaded object</li>
  
  <li>The SecurityDomain for the loaded object</li>
  
  </ul>
  
  <p>For complete details, see the description of the properties in the 
  <a target='_blank' href="http://livedocs.adobe.com/flash/9.0/ActionScriptLangRefV3/flash/system/LoaderContext.html">LoaderContext</a> class.</p>
		 */
		public static function loadDefaultSkin(id:String, url:String, context:LoaderContext=null):void {
			var nb:int = _instances.length;
			while (--nb >= 0)_instances[nb].loadSkin(id, url, context);
		}
		
	}
}