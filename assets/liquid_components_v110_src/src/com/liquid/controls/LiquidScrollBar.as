/**
*
*
*	LiquidScrollBar
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
	
	import com.liquid.containers.LiquidScrollPane;
	import fl.controls.ScrollBar;
	import fl.events.ComponentEvent;
	import fl.controls.LabelButton;
	import fl.controls.BaseButton;
	import flash.display.Sprite;
		
	import flash.display.BitmapData;
	import flash.events.Event;
	import flash.events.EventPhase;
	import flash.events.MouseEvent;
	import flash.system.LoaderContext;
	
	import com.liquid.controls.LiquidButton;
	import com.liquid.skin.SkinAsset;
	import com.liquid.core.LiquidManager;
	
	
	/**
	 * @author Didier Brun
	 * @version 1.0
	 */
	public class LiquidScrollBar extends ScrollBar {
		
		// ------------------------------------------------
		//
		// ---o inspectable properties
		//
		// ------------------------------------------------
		
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
		
		private static const ICON_THUMB_OFFSET:int = 0;
		private static const ICON_UP_ARROW_OFFSET:int = 4;
		private static const ICON_DOWN_ARROW_OFFSET:int = 8;
		
		private static const ICON_STATES_COUNT:int = 8;
		
		private static var _instances:Array = [];
				
		// ------------------------------------------------
		//
		// ---o constructor
		//
		// ------------------------------------------------	
		
		function LiquidScrollBar() {
			
			super();
		
			// register component
			LiquidManager.register(this);
			
			// init
			initializeLiquid();
			
			// instances
			_instances.push(this);
			
			// livre preview
			if (isLivePreview) {
				setScrollProperties(50, 0, 200);
			}
			
			// round size
			width = Math.floor(width);
			height = Math.floor(height);
		}

		// ------------------------------------------------
		//
		// ---o public methods
		//
		// ------------------------------------------------
		
		/**
		 * Set a BitmapData skin for the LiquidScrollBar instance.
		 * 
		 * @example 	<listing version="3.0" >myScroll.setSkin("upArrow",myBitmapId(0,0));</listing> 
		 * 
		 * @param id	String indentifier, this property take the folowing value :
		 * <p><table class=innertable>
		 * <tr><th>id</th><th>Description</th></tr>
		 * <tr><td>upArrow</td><td>The up arrow button (left when horizontal)</td></tr>
		 * <tr><td>downArrow</td><td>The down arrow button (right when horizontal)</td></tr>
		 * <tr><td>track</td><td>The track background of the scrollbar</td></tr>
		 * <tr><td>thumb</td><td>The draggable thumb</td></tr>
		 * <tr><td>icons</td><td>The 12 icons of the scrollbar</td></tr>
		 * </table></p>
		 * @param bmp	BitmapData instance
		 */
		public function setSkin(id:String, bmp:BitmapData):void {
			
			switch (id) {
				case "upArrow":
					_pLoadUpArrow = "";
					_pSkinUpArrow = "";
					(upArrow as LiquidButton).setSkin("back",bmp);
				break;
				case "downArrow":
					_pLoadDownArrow = "";
					_pSkinDownArrow = "";
					(downArrow as LiquidButton).setSkin("back",bmp);
				break;
				case "track":
					_pLoadTrack = "";
					_pSkinTrack = "";
					(track as LiquidButton).setSkin("back",bmp);
				break;
				case "thumb":
					_pLoadThumb = "";
					_pSkinThumb = "";
					(thumb as LiquidButton).setSkin("back",bmp);
				break;
				case "icons":
					_pLoadIcons = "";
					_pSkinIcons = "";
					(upArrow as LiquidButton).setIcon(bmp, ICON_UP_ARROW_OFFSET);
					(downArrow as LiquidButton).setIcon(bmp, ICON_DOWN_ARROW_OFFSET);
					(thumb as LiquidButton).setIcon(bmp, ICON_THUMB_OFFSET);
				break;
			}
		}
		
		/**
		 * Load an external bitmap skin file and apply it to the LiquidScrollBar instance.
		 * 
		 * @example 	<listing version="3.0" >myScroll.loadSkin("upArrow","./skin/mySkin.png");</listing> 
		 * 
		 * @param id	String indentifier, this property take the folowing value :
		* <p><table class=innertable>
		 * <tr><th>id</th><th>Description</th></tr>
		 * <tr><td>upArrow</td><td>The up arrow button (left when horizontal)</td></tr>
		 * <tr><td>downArrow</td><td>The down arrow button (right when horizontal)</td></tr>
		 * <tr><td>track</td><td>The track background of the scrollbar</td></tr>
		 * <tr><td>thumb</td><td>The draggable thumb</td></tr>
		 * <tr><td>icons</td><td>The 12 icons of the scrollbar</td></tr>
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
					(upArrow as LiquidButton).loadSkin("back",url,context);
				break;
				case "downArrow":
					_pLoadDownArrow = "";
					_pSkinDownArrow = "";
					(downArrow as LiquidButton).loadSkin("back",url,context);
				break;
				case "track":
					_pLoadTrack = "";
					_pSkinTrack = "";
					(track as LiquidButton).loadSkin("back",url,context);
				break;
				case "thumb":
					_pLoadThumb = "";
					_pSkinThumb = "";
					(thumb as LiquidButton).loadSkin("back",url,context);
				break;
				case "icons":
					_pLoadIcons = "";
					_pSkinIcons = "";
					(upArrow as LiquidButton).loadIcon(url, ICON_UP_ARROW_OFFSET, ICON_STATES_COUNT, context);
					(downArrow as LiquidButton).loadIcon(url, ICON_DOWN_ARROW_OFFSET, ICON_STATES_COUNT, context);
					(thumb as LiquidButton).loadIcon(url, ICON_THUMB_OFFSET, ICON_STATES_COUNT, context );
					
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
			
			// focusrect
			setStyle("focusRectSkin", LiquidManager.getFocusSkinBox());
						
			// added
			addEventListener(Event.ADDED, addedHandler);
			
			// default style
			delete THUMB_STYLES.icon;
			
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
			
			var cmp:LiquidScrollBar = e.target as LiquidScrollBar;
			cmp.removeEventListener(Event.RENDER, renderHandler);
			cmp.removeEventListener(Event.ENTER_FRAME, renderHandler)
			
			initSkin();
		}
		
		/**
		 * init skin
		 * @private
		 */
		protected function initSkin():void {
			
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
			
			draw();
		}
		
		/**
		 * configUI
		 * @private
		 */
		override protected function configUI():void {
			
			super.configUI();
			
			removeChild(track);
			track = new LiquidButton();
			track.move(0, 15);
			(track as LiquidButton).label = "";
			track.useHandCursor = false;
			track.autoRepeat = true;
			track.focusEnabled = false;
			addChild(track);
			
			removeChild(upArrow);
			upArrow = new LiquidButton();
			upArrow.setSize(WIDTH, 15);
			(upArrow as LiquidButton).label = "";
			upArrow.move(0,0);
			upArrow.autoRepeat = true;
			upArrow.focusEnabled = false;
			addChild(upArrow);
			
			removeChild(downArrow);
			downArrow = new LiquidButton();
			downArrow.setSize(WIDTH, 15);
			(downArrow as LiquidButton).label = "";
			downArrow.autoRepeat = true;
			downArrow.focusEnabled = false;
			addChild(downArrow);
			
			removeChild(thumb);
			thumb = new LiquidButton();
			(thumb as LiquidButton).label = "";
			thumb.setSize(WIDTH,15);
			thumb.move(0, 15);
			thumb.focusEnabled = false;
			addChild(thumb);

			upArrow.addEventListener(ComponentEvent.BUTTON_DOWN,scrollPressHandler,false,0,true);
			downArrow.addEventListener(ComponentEvent.BUTTON_DOWN,scrollPressHandler,false,0,true);
			track.addEventListener(ComponentEvent.BUTTON_DOWN,scrollPressHandler,false,0,true);
			thumb.addEventListener(MouseEvent.MOUSE_DOWN, thumbPressHandler, false, 0, true);
			
			enabled = false;
		}
		
		/**
		 * update thumb
		 * @private
		 */
		override protected function updateThumb():void {
			super.updateThumb();
			thumb.height = Math.round(thumb.height);
		}
		
		// ------------------------------------------------
		//
		// ---o private methods
		//
		// ------------------------------------------------
		

		

	}
}