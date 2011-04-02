/**
*
*
*	Icon
*
* 	This class is distributed under Creative Commons License.
* 	http://creativecommons.org/licenses/by/2.0/fr/
*	
*	@author		Didier Brun
*	@version	1.1
* 	@link		http://www.bytearray.org
*
*/

package com.liquid.display{
	
	import com.liquid.core.SkinBox;
	import fl.core.UIComponent;
	
	import flash.display.BitmapData;
	import flash.geom.Matrix;
	import flash.events.Event;
	import flash.events.EventPhase;
	import flash.system.LoaderContext;
	
	import com.liquid.skin.SkinAsset;
	import com.liquid.events.LiquidEvent;
	import com.liquid.skin.SkinLoader;
	
	/**
	 * @author Didier Brun
	 * @version 1.0
	 */
	public class LiquidIcon extends UIComponent{
		
		// ------------------------------------------------
		//
		// ---o inspectable properties
		//
		// ------------------------------------------------
		
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
		
		private var _bmp:BitmapData;
		private var _iconWidth:int;
		private var _iconHeight:int;
		private var _state:int=0;
		private var _offset:int = 0;
		private var _statesCount:int = 0;
			
		private var _live:SkinBox;

		// ------------------------------------------------
		//
		// ---o constructor
		//
		// ------------------------------------------------
		
		function LiquidIcon() {
			
			super();
			
			// init
			initializeLiquid();
			
			// livre preview
			if (isLivePreview) {
				initLivePreview();
			}
		}
		
		// ------------------------------------------------
		//
		// ---o public methods
		//
		// ------------------------------------------------
		
		/**
		 * Set the number of states of the LiquidIcon.
		 * If the index(offset) is defined to a value greater or equal than statesCount, then the first icon is used (state=0).
		 * 
		 * @param pCount	the number of states
		 */
		public function set statesCount(pCount:int):void {
			_statesCount = pCount;
			draw();
		}
		
		/**
		 * Get the number of states of the LiquidIcon.
		 * 
		 * @return the number of states of the LiquidIcon.
		 */
		public function get statesCount():int {
			return _statesCount;
		}
		
		/**
		 * Set the current state of LiquidIcon (begin from 0) and redraw it.
		 * Note that the icon displayed by the LiquidIcon is always : <code>offset + state</code>.
		 * 
		 * @example 	<listing version="3.0" >myIcon.state=1;</listing> 
		 * 
		 * @param pState	the state of the LiquidIcon
		 */
		public function set state(pState:int):void {
			if (_state!=pState){
				_state = pState;
				draw();
			}
		}
		
		/**
		 * Return the current state of the LiquidIcon.
		 * 
		 * @example 	<listing version="3.0" >trace (myIcon.state);</listing> 
		 * 
		 * @return the current state of the LiquidIcon
		 */
		public function get state():int {
			return _state;
		}
		
		/**
		 * Set the current offset of LiquidIcon (begin from 0) and redraw it.
		 * Note that the icon displayed by the LiquidIcon is always : <code>offset + state</code>.
		 * 
		 * @example 	<listing version="3.0" >myIcon.offset=4;</listing> 
		 * 
		 * @param pOffsset	the offset of the LiquidIcon
		 */
		public function set offset(pOffset:int):void {
			if (_offset!=pOffset){
				_offset = pOffset;
				draw();
			}
		}
		
		/**
		 * Return the current offset of the LiquidIcon.
		 * 
		 * @example 	<listing version="3.0" >trace (myIcon.offset);</listing> 
		 * 
		 * @return the current offset of the LiquidIcon
		 */
		public function get offset():int {
			return offset;
		}
		
		/**
		 * Set a BitmapData source for the LiquidIcon instance.
		 * 
		 * @example 	<listing version="3.0" >myIcon.setIcon(myBitmapId(0,0));</listing> 
		 * 
		 * @param bmp	BitmapData instance
		 */
		public function setIcon(bmp:BitmapData):void {
			
			_pIcon = "";
			_pLoadIcon = "";
			
			_bmp = bmp;
			
			if (_bmp != null) {
				buildIcon();
				draw();
			}
		}
		
		/**
		 * Load an external bitmap source file and apply it to the LiquidIcon instance.
		 * 
		 * @example 	<listing version="3.0" >myIcon.loadIcon("./skin/mySkin.png");</listing> 
		 * 
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
		public function loadIcon(url:String, context:LoaderContext = null):void {
			
			if (isLivePreview) return;
			
			_pIcon = "";
			_pLoadIcon = "";
					
			if (url != "") {
				var sl:SkinLoader = SkinAsset.loadSkin(url, context);
				if (sl.getState() == SkinLoader.STATE_LOADED) {
					setIcon(sl.getBitmap());
				}else{
					sl.addEventListener(LiquidEvent.SKIN_LOADED, iconLoaded);
				}
			}
			
		}
		
		/**
		 * clone
		 */
		public function clone():LiquidIcon {
			var li:LiquidIcon = new LiquidIcon();
			li.setIcon(_bmp);
			li.offset = _offset;
			li.statesCount = _statesCount;
			li.state = 0;
			return li;
		}
	
		
		
		// ------------------------------------------------
		//
		// ---o protected function
		//
		// ------------------------------------------------
	
		/**
		 * initialize the liquid component
		 * @private
		 */
		protected function initializeLiquid():void {
							
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
			var cmp:LiquidIcon = e.target as LiquidIcon;
			cmp.removeEventListener(Event.RENDER, renderHandler);
			cmp.removeEventListener(Event.ENTER_FRAME, renderHandler)
	
			// init skin
			initSkin();
		}
		
		/**
		 * init skin
		 * @private
		 */
		protected function initSkin():void {
			if (_pIcon != "" && _pLoadIcon == "") {
				_bmp = SkinAsset.getBitmapData(_pIcon);
				if (_bmp != null) {
					buildIcon();
				}
			}	
			
			if (_pLoadIcon != "") {
				loadIcon(_pLoadIcon);
			}
			
			draw();
		}
		
		/**
		 * init icon
		 * @private
		 */
		protected function buildIcon():void {
			
			// icon width
			_iconWidth = _bmp.width;
			
			// icon height
			var py:int=1;
			var pcol:Number=_bmp.getPixel32(1,py);
			while (_bmp.getPixel32(1,py)==pcol)py++;
			_iconHeight = py + 1;
			
			// state length
			if (_statesCount==0) {
				_statesCount = Math.floor(_bmp.height / _iconHeight)-1;
			}
			
			setSize(_iconHeight, _iconHeight);
		}
		
		/**
		 * draw
		 * @private
		 */
		override protected function draw():void {
			
			if (_bmp != null) {
				var i:int = _offset +(_state >= _statesCount ? 0 : _state);
				var mat:Matrix = new Matrix();
				mat.ty = -(i * _iconHeight + _iconHeight);
				graphics.clear();
				graphics.beginBitmapFill(_bmp, mat, false);
				graphics.drawRect(0, 0, _iconWidth, _iconHeight);
				graphics.endFill();
			}
			
			if (isLivePreview) {
				_live.setSize(width, height);
			}
			
			super.draw();
		}
		
		/**
		 * icon loaded
		 * @private
		 */
		protected function iconLoaded(e:LiquidEvent):void {
			var sl:SkinLoader = e.target as SkinLoader;
			sl.removeEventListener(LiquidEvent.SKIN_LOADED, iconLoaded);
			setIcon(sl.getBitmap());
			dispatchEvent(new LiquidEvent(LiquidEvent.SKIN_LOADED));
		}
		
			
		// ------------------------------------------------
		//
		// ---o private methods
		//
		// ------------------------------------------------
		
		/**
		 * initLivePreview
		 */
		private function initLivePreview():void {
			_live = new SkinBox(12,12);
			_live.setSkin(SkinAsset.getBitmapData("LiquidIcon_live"));
			addChild(_live);
		}
		
	}
	
}
