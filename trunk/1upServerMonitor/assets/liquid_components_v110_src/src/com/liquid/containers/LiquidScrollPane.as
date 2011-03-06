/**
*
*
*	LiquidScrollPane
* 	
* 	This class is distributed under Creative Commons License.
* 	http://creativecommons.org/licenses/by/2.0/fr/
*	
*	@author		Didier Brun
*	@version	1.1
* 	@link		http://www.bytearray.org
*  	
*
*/

package com.liquid.containers{
	
	import com.liquid.skin.SkinLoader;
	import fl.containers.ScrollPane;
	import fl.core.InvalidationType;
	import fl.controls.ScrollBarDirection;
	import fl.core.UIComponent;
	import fl.events.ScrollEvent;
	import flash.display.BitmapData;
	import flash.events.Event;
	import flash.events.EventPhase;
	import flash.system.LoaderContext;
	
	import com.liquid.core.SkinBox;
	import com.liquid.core.ILiquidComponent;
	import com.liquid.core.LiquidManager;
	import com.liquid.skin.SkinAsset;
	import com.liquid.skin.SkinLoader;
	import com.liquid.events.LiquidEvent;
	import com.liquid.controls.LiquidScrollBar;

		
	/**
	 * @author Didier Brun
	 * @version 1.0
	 */
	public class LiquidScrollPane extends ScrollPane {

		// ------------------------------------------------
		//
		// ---o inspectable properties
		//
		// ------------------------------------------------
		
		[Inspectable (defaultValue = "LiquidScrollPane_back", name = "::skin back")]
		/**
		 * @private
		 */
		public var _pSkinBack:String = "LiquidScrollPane_back";
		
		
		[Inspectable (defaultValue = "", name = "::load back")]
		/**
		 * @private
		 */
		public var _pLoadBack:String = "";
		
		[Inspectable (defaultValue = "LiquidScrollBar_arrow", name = "::skin upArrow")]
		/**
		 * @private
		 */
		public var _pSkinUpArrow:String = "LiquidScrollBar_arrow";
		
		[Inspectable (defaultValue = "LiquidScrollBar_arrow", name = "::skin downArrow")]
		/**
		 * @private
		 */
		public var _pSkinDownArrow:String = "LiquidScrollBar_arrow";
		
		[Inspectable (defaultValue = "LiquidScrollBar_thumb", name = "::skin thumb")]
		/**
		 * @private
		 */
		public var _pSkinThumb:String = "LiquidScrollBar_thumb";
		
		[Inspectable (defaultValue = "LiquidScrollBar_track", name = "::skin track")]
		/**
		 * @private
		 */
		public var _pSkinTrack:String = "LiquidScrollBar_track";
		
		[Inspectable (defaultValue = "LiquidScrollBar_icons", name = "::skin icons")]
		/**
		 * @private
		 */
		public var _pSkinIcons:String = "LiquidScrollBar_icons";
		
		[Inspectable (defaultValue = "", name = "::load upArrow")]
		/**
		 * @private
		 */
		public var _pLoadUpArrow:String = "";
		
		[Inspectable (defaultValue = "", name = "::load downArrow")]
		/**
		 * @private
		 */
		public var _pLoadDownArrow:String = "";
		
		[Inspectable (defaultValue = "", name = "::load thumb")]
		/**
		 * @private
		 */
		public var _pLoadThumb:String = "";
		
		[Inspectable (defaultValue = "", name = "::load icons")]
		/**
		 * @private
		 */
		public var _pLoadIcons:String = "";
		
		[Inspectable (defaultValue = "", name = "::load track")]
		/**
		 * @private
		 */
		public var _pLoadTrack:String = "";
		
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
		
		function LiquidScrollPane(){
			
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
		 * Set a BitmapData skin for the LiquidScrollPane instance.
		 * 
		 * @example 	<listing version="3.0" >myScrollPane.setSkin("upArrow",myBitmapId(0,0));</listing> 
		 * 
		 * @param id	String indentifier, this property take the folowing value :
		 * <p><table class=innertable>
		 * <tr><th>id</th><th>Description</th></tr>
		 * <tr><td>upArrow</td><td>The up/left arrow button of the scrollbars</td></tr>
		 * <tr><td>downArrow</td><td>The down/right arrow button of the scrollbars</td></tr>
		 * <tr><td>track</td><td>The track background of the scrollbars</td></tr>
		 * <tr><td>thumb</td><td>The draggable thumb of the scrollbars</td></tr>
		 * <tr><td>icons</td><td>The 12 icons the scrollbars</td></tr>
		 * <tr><td>back</td><td>The background of the scrollpane</td></tr>
		 * </table></p>
		 * @param bmp	BitmapData instance
		 */
		public function setSkin(id:String, bmp:BitmapData):void {
				
			switch (id) {
				case "upArrow":
					_pLoadUpArrow = "";
					_pSkinUpArrow = "";
					(_horizontalScrollBar as LiquidScrollBar).setSkin("upArrow", bmp);
					(_verticalScrollBar as LiquidScrollBar).setSkin("upArrow",bmp);
				break;
				case "downArrow":
					_pLoadDownArrow = "";
					_pSkinDownArrow = "";
					(_horizontalScrollBar as LiquidScrollBar).setSkin("downArrow", bmp);
					(_verticalScrollBar as LiquidScrollBar).setSkin("downArrow",bmp);
				break;
				case "track":
					_pLoadTrack = "";
					_pSkinTrack = "";
					(_horizontalScrollBar as LiquidScrollBar).setSkin("track", bmp);
					(_verticalScrollBar as LiquidScrollBar).setSkin("track",bmp);
				break;
				case "thumb":
					_pLoadThumb = "";
					_pSkinThumb = "";
					(_horizontalScrollBar as LiquidScrollBar).setSkin("thumb", bmp);
					(_verticalScrollBar as LiquidScrollBar).setSkin("thumb",bmp);
				break;
				case "icons":
					_pLoadIcons = "";
					_pSkinIcons = "";
					(_horizontalScrollBar as LiquidScrollBar).setSkin("icons", bmp);
					(_verticalScrollBar as LiquidScrollBar).setSkin("icons",bmp);
				break;
				case "back":
					_pLoadBack = "";
					_pSkinBack = "";
					_skinBack.setSkin(bmp);
				break;
			}
		}
		
		/**
		 * Load an external bitmap skin file and apply it to the LiquidScrollPane instance.
		 * 
		 * @example 	<listing version="3.0" >myScrollPane.loadSkin("back","./skin/mySkin.png");</listing> 
		 * 
		 * @param id	String indentifier, this property take the folowing value :
			 * <p><table class=innertable>
		 * <tr><th>id</th><th>Description</th></tr>
		 * <tr><td>upArrow</td><td>The up/left arrow button of the scrollbars</td></tr>
		 * <tr><td>downArrow</td><td>The down/right arrow button of the scrollbars</td></tr>
		 * <tr><td>track</td><td>The track background of the scrollbars</td></tr>
		 * <tr><td>thumb</td><td>The draggable thumb of the scrollbars</td></tr>
		 * <tr><td>icons</td><td>The 12 icons the scrollbars</td></tr>
		 * <tr><td>back</td><td>The background of the scrollpane</td></tr>
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
				case "upArrow":
					_pLoadUpArrow = "";
					_pSkinUpArrow = "";
					(_horizontalScrollBar as LiquidScrollBar).loadSkin("upArrow", url, context);
					(_verticalScrollBar as LiquidScrollBar).loadSkin("upArrow", url, context);
				break;
				case "downArrow":
					_pLoadDownArrow = "";
					_pSkinDownArrow = "";
					(_horizontalScrollBar as LiquidScrollBar).loadSkin("downArrow", url, context);
					(_verticalScrollBar as LiquidScrollBar).loadSkin("downArrow", url, context);
				break;
				case "track":
					_pLoadTrack = "";
					_pSkinTrack = "";
					(_horizontalScrollBar as LiquidScrollBar).loadSkin("track", url, context);
					(_verticalScrollBar as LiquidScrollBar).loadSkin("track", url, context);
				break;
				case "thumb":
					_pLoadThumb = "";
					_pSkinThumb = "";
					(_horizontalScrollBar as LiquidScrollBar).loadSkin("thumb", url, context);
					(_verticalScrollBar as LiquidScrollBar).loadSkin("thumb", url, context);
				break;
				case "icons":
					_pLoadIcons = "";
					_pSkinIcons = "";
					(_horizontalScrollBar as LiquidScrollBar).loadSkin("icons", url, context);
					(_verticalScrollBar as LiquidScrollBar).loadSkin("icons", url, context);	
				break;
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
		 * draw
		 * @private
		 */
		override protected function draw():void {
		
			// draw state
			var _state:int = enabled ? SKIN_UP : SKIN_DISABLED;
			
			_skinBack.state = _state;
			
			// super
			super.draw();
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
			var cmp:LiquidScrollPane = e.target as LiquidScrollPane;
			cmp.removeEventListener(Event.RENDER, renderHandler);
			cmp.removeEventListener(Event.ENTER_FRAME, renderHandler)
			
			initSkin();
		}
		
		/**
		 * init skin
		 * @private
		 */
		protected function initSkin():void {
			
			// back
			if (!_skinBack.isReady && _pLoadBack == "") {
				setSkin("back",SkinAsset.getBitmapData(_pSkinBack));
			}
			
			if (_pLoadBack != "") {
				loadSkin("back", _pLoadBack);
			}
			
			// upArrow
			if (_pSkinUpArrow != "" && _pLoadUpArrow == "") {
				setSkin("upArrow",SkinAsset.getBitmapData(_pSkinUpArrow));
			}
			if (_pLoadUpArrow != "")loadSkin("upArrow", _pLoadUpArrow);
			
			
			
			// downArrow
			if (_pSkinDownArrow != "" && _pLoadDownArrow=="") {
				setSkin("downArrow",SkinAsset.getBitmapData(_pSkinDownArrow));
			}
			if (_pLoadDownArrow != "")loadSkin("downArrow", _pLoadDownArrow);
			
			
			// thumb
			if (_pSkinThumb != "" && _pLoadThumb=="") {
				setSkin("thumb", SkinAsset.getBitmapData(_pSkinThumb));
			}
			if (_pLoadThumb != "")loadSkin("thumb", _pLoadThumb);
			
			
			// track
			if (_pSkinTrack != "" && _pLoadTrack=="") {
				setSkin("track", SkinAsset.getBitmapData(_pSkinTrack));
			}
			if (_pLoadTrack != "") loadSkin("track", _pLoadTrack);
			
			// icons
			if (_pSkinIcons != "" && _pLoadIcons == "") {
				setSkin("icons", SkinAsset.getBitmapData(_pSkinIcons));
			}
			if (_pLoadIcons != "") loadSkin("icons", _pLoadIcons);
			
			invalidate(InvalidationType.SIZE);
			drawNow();
		}
		
		/**
		 * config UI
		 * @private
		 */
		override protected function configUI():void {
			
			super.configUI();
			
			// vertical scrollbar
			removeChild(_verticalScrollBar);
			_verticalScrollBar = new LiquidScrollBar();
			_verticalScrollBar.addEventListener(ScrollEvent.SCROLL,handleScroll,false,0,true);
			_verticalScrollBar.visible = false;
			_verticalScrollBar.lineScrollSize = defaultLineScrollSize;
			addChild(_verticalScrollBar);
			copyStylesToChild(_verticalScrollBar, SCROLL_BAR_STYLES);
			
			// horizontal scrollbar
			removeChild(_horizontalScrollBar);
			_horizontalScrollBar = new LiquidScrollBar();
			_horizontalScrollBar.direction = ScrollBarDirection.HORIZONTAL;
			_horizontalScrollBar.addEventListener(ScrollEvent.SCROLL,handleScroll,false,0,true);
			_horizontalScrollBar.visible = false;
			_horizontalScrollBar.lineScrollSize = defaultLineScrollSize;
			addChild(_horizontalScrollBar);
			copyStylesToChild(_horizontalScrollBar, SCROLL_BAR_STYLES);
			
		}
		
		// ------------------------------------------------
		//
		// ---o private methods
		//
		// ------------------------------------------------
		
		/**
		 * load back
		 */
		private function load_back(url:String, context:LoaderContext = null):void {
			if (url != "") {
				var sl:SkinLoader = SkinAsset.loadSkin(url, context);
				
				if (sl.getState() == SkinLoader.STATE_LOADED) {
					
					_skinBack.setSkin(sl.getBitmap());
					_skinBack.drawNow();
					
					draw();
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
			
			draw();
		}
		
		
	}
}