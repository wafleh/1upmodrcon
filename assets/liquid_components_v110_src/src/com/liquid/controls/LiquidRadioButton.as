/**
*
*
*	LiquidRadioButton
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
	
	import fl.controls.RadioButton;
	import fl.core.InvalidationType;
	
	import flash.display.Bitmap;
	import flash.events.Event;
	import flash.events.EventPhase;
	import flash.text.TextFormat;
	import flash.display.BitmapData;
	import flash.system.LoaderContext;
	
	import com.liquid.core.LiquidManager;
	import com.liquid.core.SkinBox;
	import com.liquid.skin.SkinAsset;
	import com.liquid.skin.SkinLoader;
	import com.liquid.events.LiquidEvent;
	
	/**
	 * @author Didier Brun
	 * @version 1.0
	 */
	public class LiquidRadioButton extends RadioButton {

		// ------------------------------------------------
		//
		// ---o inspectable properties
		//
		// ------------------------------------------------
		
		[Inspectable (defaultValue = "LiquidRadioButton_icon", name = "::skin icon")]
		/**
		 * @private
		 */
		public var _pSkinIcon:String = "LiquidRadioButton_icon";
		
		[Inspectable (defaultValue = "", name = "::load icon")]
		/**
		 * @private
		 */
		public var _pLoadIcon:String = "";
		
		// ------------------------------------------------
		//
		// ---o properties
		//
		// ------------------------------------------------
		
		private static const SKIN_UP:int = 0;
		private static const SKIN_OVER:int = 1;
		private static const SKIN_DOWN:int = 2;
		private static const SKIN_DISABLED:int = 3;
		private static const SKIN_SELECTED_UP:int = 4;
		private static const SKIN_SELECTED_OVER:int = 5;
		private static const SKIN_SELECTED_DOWN:int = 6;
		private static const SKIN_SELECTED_DISABLED:int = 7;
		
		private static const DEFAULT_ICON_WIDTH:int = 14;
		private static const DEFAULT_ICON_HEIGHT:int = 14;
		
		private var _skinIcon:SkinBox;								// background
		
		private static var _instances:Array = [];
				
		// ------------------------------------------------
		//
		// ---o constructor
		//
		// ------------------------------------------------
		
		function LiquidRadioButton(){
			
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
		 * Set a BitmapData skin for the RadioButton instance.
		 * 
		 * @example 	<listing version="3.0" >myRadioButton.setSkin("icon",myBitmapId(0,0));</listing> 
		 * 
		 * @param id	String indentifier, this property take the folowing value :
		 * <p><table class=innertable>
		 * <tr><th>id</th><th>Description</th></tr>
		 * <tr><td>icon</td><td>The icon of the radui button</td></tr>
		 * </table></p>
		 * @param bmp	BitmapData instance
		 */
		public function setSkin(id:String, bmp:BitmapData):void {
				
			switch (id) {
				case "icon":
					_pLoadIcon = "";
					_pSkinIcon = "";
					skin_icon(bmp);
				break;
			}
		}
		
		/**
		 * Load an external bitmap skin file and apply it to the LiquidRadioButton instance.
		 * 
		 * @example 	<listing version="3.0" >myRadioButton.loadSkin("icon","./skin/mySkin.png");</listing> 
		 * 
		 * @param id	String indentifier, this property take the folowing value :
		 * <p><table class=innertable>
		 * <tr><th>id</th><th>Description</th></tr>
		 * <tr><td>icon</td><td>The icon of the radio button</td></tr>
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
				case "icon":
					_pLoadIcon = "";
					_pSkinIcon = "";
					load_icon(url, context);
				break;
			}
		}
		
		// ------------------------------------------------
		//
		// ---o protected methods
		//
		// ------------------------------------------------
		
		/**
		 * initialize the liquid component
		 * @private
		 */
		protected function initializeLiquid():void {
			
			// skin
			_skinIcon = new SkinBox(DEFAULT_ICON_WIDTH, DEFAULT_ICON_HEIGHT);
			
					
			setStyle("upIcon", _skinIcon);
			setStyle("overIcon", _skinIcon);
			setStyle("downIcon", _skinIcon);
			setStyle("disabledIcon", _skinIcon);
			setStyle("selectedUpIcon", _skinIcon);
			setStyle("selectedOverIcon", _skinIcon);
			setStyle("selectedDownIcon", _skinIcon);
			setStyle("selectedDisabledIcon", _skinIcon);
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
			var cmp:LiquidRadioButton = e.target as LiquidRadioButton;
			cmp.removeEventListener(Event.RENDER, renderHandler);
			cmp.removeEventListener(Event.ENTER_FRAME, renderHandler)
			
			initSkin();
		}
		
		/**
		 * init skin
		 * @private
		 */
		protected function initSkin():void {
			
			if (!_skinIcon.isReady && _pLoadIcon == "") {
				
				_skinIcon.setSkin(SkinAsset.getBitmapData(_pSkinIcon));
			}
			
			if (_pLoadIcon != "") {
				loadSkin("icon", _pLoadIcon);
			}
			
			invalidate(InvalidationType.SIZE);
			drawNow();

		}
		
		/**
		 * draw
		 */
		override protected function draw():void {
			
			// draw state
			var states:Object = { 	"up":SKIN_UP, 
									"over":SKIN_OVER,
									"down":SKIN_DOWN };
				
							
			var _state:int = states[mouseState];
			if (!enabled)_state = SKIN_DISABLED;
			if (selected)_state += SKIN_SELECTED_UP;
			
			_skinIcon.state = _state;
			
			// tf
			var tf:TextFormat = getStyleValue("textFormat") as TextFormat;
			tf.color = _skinIcon.getColor();
			drawTextFormat();
			
			super.draw();
		}
		
			// ------------------------------------------------
		//
		// ---o private methods
		//
		// ------------------------------------------------
		
		/**
		 * skin icon
		 */
		private function skin_icon(bmp:BitmapData):void {
			if (bmp != null) {
				
				_skinIcon.setSkin(bmp);
				_skinIcon.resetSize();
			
			}
		}
		
		/**
		 * load icon
		 */
		private function load_icon(url:String, context:LoaderContext = null):void {
			if (url != "") {
				
				var sl:SkinLoader = SkinAsset.loadSkin(url, context);
				if (sl.getState() == SkinLoader.STATE_LOADED) {
					
					_skinIcon.setSkin(sl.getBitmap());
					_skinIcon.drawNow();
					_skinIcon.resetSize();
					draw();
					drawLayout();
				}else{
					sl.addEventListener(LiquidEvent.SKIN_LOADED, icon_loaded);
				}
			}
		}
		
		
		/**
		 * icon loaded
		 */
		private function icon_loaded(e:Event):void {
			
			var sl:SkinLoader = e.target as SkinLoader;
			sl.removeEventListener(LiquidEvent.SKIN_LOADED, icon_loaded);
			_skinIcon.setSkin(sl.getBitmap());
			_skinIcon.resetSize();
			draw();
			drawLayout();
		}
		
		// ------------------------------------------------
		//
		// ---o static methods
		//
		// ------------------------------------------------
		
		/**
		 * Set a BitmapData skin for all LiquidRadioButton instances.
		 * 
		 * @example 	<listing version="3.0" >LiquidRadioButton.setSkin("icon",myBitmapId(0,0));</listing> 
		 * 
		 * @param id	String indentifier, this property take the folowing value :
		 * <p><table class=innertable>
		 * <tr><th>id</th><th>Description</th></tr>
		 * <tr><td>icon</td><td>The icon of the radio button</td></tr>
		 * </table></p>
		 * @param bmp	BitmapData instance
		 */
		public static function setDefaultSkin(id:String, bmp:BitmapData):void {
			var nb:int = _instances.length;
			while (--nb >= 0)_instances[nb].setSkin(id,bmp);
		}
		
		/**
		 * Load an external bitmap skin file and apply it to all RadioButton instances.
		 * 
		 * @example 	<listing version="3.0" >LiquidRadioButton.loadSkin("icon","./skin/mySkin.png");</listing> 
		 * 
		 * @param id	String indentifier, this property take the folowing value :
		 * <p><table class=innertable>
		 * <tr><th>id</th><th>Description</th></tr>
		 * <tr><td>icon</td><td>The icon of the radio button</td></tr>
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
