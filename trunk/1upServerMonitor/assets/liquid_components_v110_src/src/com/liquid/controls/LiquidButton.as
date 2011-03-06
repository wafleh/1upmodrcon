/**
*
*
*	LiquidButton
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
	
	import com.liquid.display.LiquidIcon;
	import com.liquid.skin.SkinLoader;
	import fl.controls.Button;
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
	import com.liquid.display.LiquidIcon;

	
	/**
	 * @author Didier Brun
	 * @version 1.0
	 */
	public class LiquidButton extends Button {

		// ------------------------------------------------
		//
		// ---o inspectable properties
		//
		// ------------------------------------------------
		
		
		[Inspectable (defaultValue = "LiquidButton_back", name = "::skin back")]
		/**
		 * @private
		 */
		public var _pSkinBack:String = "LiquidButton_back";
		
		[Inspectable (defaultValue = "", name = "::load back")]
		/**
		 * @private
		 */
		public var _pLoadBack:String = "";
		
		[Inspectable (defaultValue = "", name = "::icon")]
		/**
		 * @private
		 */
		public var _pIcon:String = "";
		
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
		private static const SKIN_EMPHASIZED:int = 8;

		protected var _skinBack:SkinBox;							// background
		private var _skinEmphasized:SkinBox;						// emphasized
		
		private var _state:int;
		private var _icon:LiquidIcon;
					
		private static var _instances:Array = [];
		
		// ------------------------------------------------
		//
		// ---o constructor
		//
		// ------------------------------------------------
		
		function LiquidButton(){
			
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
		 * Set a BitmapData skin for the LiquidButton instance.
		 * 
		 * @example 	<listing version="3.0" >myButton.setSkin("back",myBitmapId(0,0));</listing> 
		 * 
		 * @param id	String indentifier, this property take the folowing value :
		 * <p><table class=innertable>
		 * <tr><th>id</th><th>Description</th></tr>
		 * <tr><td>back</td><td>The background of the button</td></tr>
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
		 * Load an external bitmap skin file and apply it to the LiquidButton instance.
		 * 
		 * @example 	<listing version="3.0" >myButton.loadSkin("back","./skin/mySkin.png");</listing> 
		 * 
		 * @param id	String indentifier, this property take the folowing value :
		 * <p><table class=innertable>
		 * <tr><th>id</th><th>Description</th></tr>
		 * <tr><td>back</td><td>The background of the button.</td></tr>
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
		
		/**
		 * Create a LiquidIcon for the button instance.
		 * 
		 * @example 	<listing version="3.0" >myButton.setIcon(myBitmapId(0,0));</listing> 
		 * 
		 * @param bmp		BitmapData instance
		 * @param offset 	Offset added to the current number of the icon to be displayed
		 * @param states	Number of states for the LiquidIcon
		 */
		public function setIcon(bmp:BitmapData,offset:int=0,states:int=0):void {
			
			if (bmp != null) {
				_icon = new LiquidIcon();
				_icon.offset = offset;
				_icon.statesCount = states;
				_icon.setIcon(bmp);
				setStyle("icon", _icon);
			}
		}
		
		/**
		 * Load an external bitmap source file and create a LiquidIcon instance.
		 * 
		 * @example 	<listing version="3.0" >myButton.loadIcon("./skin/mySkin.png");</listing> 
		 * 
		 * @param url		URL of the bitmap file
		 * @param offset 	Offset added to the current number of the icon to be displayed
		 * @param states	Number of states for the LiquidIcon
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
		public function loadIcon(url:String, offset:int = 0, states:int = 0, context:LoaderContext = null):void {
			
			if (url != "") {
				_icon = new LiquidIcon();
				_icon.loadIcon(url);
				_icon.offset = offset;
				_icon.statesCount = states;
				_icon.addEventListener(LiquidEvent.SKIN_LOADED, iconLoadedHandler);
				setStyle("icon", _icon);
			}
			
		}
		
		/**
		 * override set size
		 * @private
		 */
		override public function setSize(width:Number, height:Number):void {
			if (_skinBack!=null && _skinBack.isReady){
				height = Math.max(height,_skinBack.minHeight);
				width = Math.max(width, _skinBack.minWidth);
			}
			super.setSize(width, height);
		}
		
		/**
		 * get text color
		 * @private
		 */
		public function getTextColor():Number {
			return _skinBack.getColor();
		}

		// ------------------------------------------------
		//
		// ---o protected methods
		//
		// ------------------------------------------------
		
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
			
			// draw state
			var states:Object = { 	"up":SKIN_UP, 
									"over":SKIN_OVER,
									"down":SKIN_DOWN };
										
			_state = states[mouseState];
			
			if (!enabled)_state = SKIN_DISABLED;
			if (selected)_state += SKIN_SELECTED_UP;
		
				
			_skinBack.state = _state;
			
			// tf
			var tf:TextFormat = getStyleValue("textFormat") as TextFormat;
			tf.color = _skinBack.getColor();
			drawTextFormat();
		}
		
		/**
		 * draw icon
		 *	@private
		 */
		override protected function drawIcon():void {
			
			super.drawIcon();
			
			if (icon != null && icon is LiquidIcon) {
				(icon as LiquidIcon).state = _state;
			}
		}
		
		/**
		 * initialize the liquid component
		 * @private
		 */
		protected function initializeLiquid():void {
			
			// skin
			_skinBack = new SkinBox(width, height);
			_skinEmphasized = new SkinBox(width, height);
					
			setStyle("upSkin", _skinBack);
			setStyle("overSkin", _skinBack);
			setStyle("downSkin",_skinBack);
			setStyle("selectedUpSkin", _skinBack);
			setStyle("selectedOverSkin", _skinBack);
			setStyle("selectedDownSkin", _skinBack);
			setStyle("downSkin", _skinBack);
			setStyle("disabledSkin", _skinBack);
			setStyle("emphasizedSkin", _skinEmphasized);
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
			
			var cmp:LiquidButton = e.target as LiquidButton;
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
				_skinBack.setSize(width, height);
				_skinEmphasized.setSkin(SkinAsset.getBitmapData(_pSkinBack));
				_skinEmphasized.state = SKIN_EMPHASIZED;
			}
			
			if (_pLoadBack != "") {
				loadSkin("back", _pLoadBack);
			}
			
		
			if (_pIcon != "" && _pLoadIcon == "") {
				var _bmp:BitmapData = SkinAsset.getBitmapData(_pIcon);
				if (_bmp != null) setIcon(_bmp);
			}
			
			if (_pLoadIcon != "") {
				loadIcon(_pLoadIcon);
			}
			
			invalidate();
		}
		
		/**
		 * draw emphasized
		 * @private
		 */
		override protected function drawEmphasized():void {
			super.drawEmphasized();
			if (emphasizedBorder != null) {
				emphasizedBorder.visible = _skinEmphasized.nbLayers()-1 == SKIN_EMPHASIZED;
			}
		}
		
		/**
		 * icon loaded
		 * @private
		 */
		protected function iconLoadedHandler(e:LiquidEvent):void {
			e.target.removeEventListener(LiquidEvent.SKIN_LOADED, iconLoadedHandler);
			super.drawLayout();		
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
				_skinEmphasized.setSkin(bmp);
				_skinEmphasized.state = SKIN_EMPHASIZED;
				_skinBack.drawNow();
				
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
					
					_skinEmphasized.setSkin(sl.getBitmap());
					_skinEmphasized.state = SKIN_EMPHASIZED;
					_skinEmphasized.drawNow();
					
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
			_skinBack.setSize(width, height);
			_skinBack.drawNow();
			
			_skinEmphasized.setSkin(sl.getBitmap());
			_skinEmphasized.state = SKIN_EMPHASIZED;
			_skinEmphasized.drawNow();
			
			drawBackground();
			
			dispatchEvent(new LiquidEvent(LiquidEvent.SKIN_LOADED));
		}
		
		// ------------------------------------------------
		//
		// ---o static methods
		//
		// ------------------------------------------------
		
		/**
		 * Set a BitmapData skin for all LiquidButton instances.
		 * 
		 * @example 	<listing version="3.0" >LiquidButton.setSkin("back",myBitmapId(0,0));</listing> 
		 * 
		 * @param id	String indentifier, this property take the folowing value :
		 * <p><table class=innertable>
		 * <tr><th>id</th><th>Description</th></tr>
		 * <tr><td>back</td><td>The background of the button</td></tr>
		 * </table></p>
		 * @param bmp	BitmapData instance
		 */
		public static function setDefaultSkin(id:String, bmp:BitmapData):void {
			var nb:int = _instances.length;
			while (--nb >= 0)_instances[nb].setSkin(id,bmp);
		}
		
		/**
		 * Load an external bitmap skin file and apply it to all LiquidButton instances.
		 * 
		 * @example 	<listing version="3.0" >LiquidButton.loadSkin("back","./skin/mySkin.png");</listing> 
		 * 
		 * @param id	String indentifier, this property take the folowing value :
		 * <p><table class=innertable>
		 * <tr><th>id</th><th>Description</th></tr>
		 * <tr><td>back</td><td>The background of the button.</td></tr>
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