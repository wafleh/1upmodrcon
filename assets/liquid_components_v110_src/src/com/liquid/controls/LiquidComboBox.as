/**
*
*
*	LiquidComboBox
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
	import com.liquid.events.ButtonEvent;
	import fl.controls.ComboBox;
	import fl.core.UIComponent;
	import fl.events.ListEvent;
	import fl.core.InvalidationType;
	
	import flash.display.DisplayObject;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.display.BitmapData;
	import flash.events.EventPhase;
	import flash.text.TextField;
	import flash.geom.Point;
	import flash.text.TextFormat;
	import flash.events.KeyboardEvent;
	import flash.system.LoaderContext;
	
	import com.liquid.controls.LiquidList;
	import com.liquid.controls.LiquidButton;
	import com.liquid.core.LiquidManager;
	import com.liquid.skin.SkinAsset;
	import com.liquid.display.LiquidIcon;
	import com.liquid.events.LiquidEvent;
	
	public class LiquidComboBox extends ComboBox {
		
		// ------------------------------------------------
		//
		// ---o properties
		//
		// ------------------------------------------------
		
		private static const OPEN_SPEED:Number = 2;
		private static const EPSILON:Number = .5;
		
		private static const LIST_UP:int = 0;
		private static const LIST_DOWN:int = 1;
		
		private static var _instances:Array = [];
		
		private var _icon:LiquidIcon;
		private var _mask:Sprite;
		private var _positionList:Number;
		private var _listPosition:int;
				
		// ------------------------------------------------
		//
		// ---o inspectable properties
		//
		// ------------------------------------------------
		
		
		[Inspectable (defaultValue = "LiquidComboBox_back", name = "::skin back")]
		/**
		 * @private
		 */
		public var _pSkinBack:String = "LiquidComboBox_back";
		
		[Inspectable (defaultValue = "LiquidList_back", name = "::skin listBack")]
		/**
		 * @private
		 */
		public var _pSkinListBack:String = "LiquidList_back";
		
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
		
		[Inspectable (defaultValue = "LiquidList_cell", name = "::skin cell")]
		/**
		 * @private
		 */
		public var _pSkinCell:String = "LiquidList_cell";
		
		[Inspectable (defaultValue = "", name = "::load back")]
		/**
		 * @private
		 */
		public var _pLoadBack:String = "";
		
		[Inspectable (defaultValue = "", name = "::load listBack")]
		/**
		 * @private
		 */
		public var _pLoadListBack:String = "";
		
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
		
		[Inspectable (defaultValue = "", name = "::load track")]
		/**
		 * @private
		 */
		public var _pLoadTrack:String = "";
		
		[Inspectable (defaultValue = "", name = "::load icons")]
		/**
		 * @private
		 */
		public var _pLoadIcons:String = "";
		
		[Inspectable (defaultValue = "", name = "::load cell")]
		/**
		 * @private
		 */
		public var _pLoadCell:String = "";
		
		// ------------------------------------------------
		//
		// ---o contructor
		//
		// ------------------------------------------------
		
		function LiquidComboBox() {
			
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
		 * Set a BitmapData skin for the LiquidComboBox instance.
		 * 
		 * @example 	<listing version="3.0" >myCombo.setSkin("upArrow",myBitmapId(0,0));</listing> 
		 * 
		 * @param id	String indentifier, this property take the folowing value :
		 * <p><table class=innertable>
		 * <tr><th>id</th><th>Description</th></tr>
		 * <tr><td>upArrow</td><td>The up/left arrow button of the scrollbars</td></tr>
		 * <tr><td>downArrow</td><td>The down/right arrow button of the scrollbars</td></tr>
		 * <tr><td>track</td><td>The track background of the scrollbars</td></tr>
		 * <tr><td>thumb</td><td>The draggable thumb of the scrollbars</td></tr>
		 * <tr><td>icons</td><td>The 12 icons the scrollbars</td></tr>
		 * <tr><td>back</td><td>The 12 icons the scrollbars</td></tr>
		 * <tr><td>listBack</td><td>The background of the list</td></tr>
		 * <tr><td>cell</td><td>The list cell background</td></tr>
		 * </table></p>
		 * @param bmp	BitmapData instance
		 */
		public function setSkin(id:String, bmp:BitmapData):void {
			
			switch (id) {
				case "back":
					_pLoadBack = "";
					_pSkinBack = "";
					(background as LiquidButton).setSkin("back",bmp);
				break;
				case "listBack":
					_pLoadListBack = "";
					_pSkinListBack = "";
					(list as LiquidList).setSkin("back", bmp);
				break;
				case "upArrow":
					_pLoadUpArrow = "";
					_pSkinUpArrow = "";
					(list as LiquidList).setSkin("upArrow", bmp);
				break;
				case "downArrow":
					_pLoadDownArrow = "";
					_pSkinDownArrow = "";
					(list as LiquidList).setSkin("downArrow", bmp);
				break;
				case "track":
					_pLoadTrack = "";
					_pSkinTrack = "";
					(list as LiquidList).setSkin("track", bmp);
				break;
				case "thumb":
					_pLoadThumb = "";
					_pSkinThumb = "";
					(list as LiquidList).setSkin("thumb", bmp);
				break;
				case "icons":
					_pLoadIcons = "";
					_pSkinIcons = "";
					(list as LiquidList).setSkin("icons", bmp);
				break;
				case "cell":
					_pLoadCell = "";
					_pSkinCell = "";
					(list as LiquidList).setSkin("cell", bmp);
				break;
			}
		}
		
		/**
		 * Load an external bitmap skin file and apply it to the LiquidComboBox instance.
		 * 
		 * @example 	<listing version="3.0" >myCombo.loadSkin("back","./skin/mySkin.png");</listing> 
		 * 
		 * @param id	String indentifier, this property take the folowing value :
		 * <p><table class=innertable>
		 * <tr><th>id</th><th>Description</th></tr>
		 * <tr><td>upArrow</td><td>The up/left arrow button of the scrollbars</td></tr>
		 * <tr><td>downArrow</td><td>The down/right arrow button of the scrollbars</td></tr>
		 * <tr><td>track</td><td>The track background of the scrollbars</td></tr>
		 * <tr><td>thumb</td><td>The draggable thumb of the scrollbars</td></tr>
		 * <tr><td>icons</td><td>The 12 icons the scrollbars</td></tr>
		 * <tr><td>back</td><td>The 12 icons the scrollbars</td></tr>
		 * <tr><td>listBack</td><td>The background of the list</td></tr>
		 * <tr><td>cell</td><td>The list cell background</td></tr>
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
					_pSkinBack = "";
					_pLoadBack = "";
					(background as LiquidButton).loadSkin("back", url, context);
				break;
				case "upArrow":
					_pLoadUpArrow = "";
					_pSkinUpArrow = "";
					(list as LiquidList).loadSkin("upArrow", url, context);
				break;
				case "downArrow":
					_pLoadDownArrow = "";
					_pSkinDownArrow = "";
					(list as LiquidList).loadSkin("downArrow", url, context);
				break;
				case "track":
					_pLoadTrack = "";
					_pSkinTrack = "";
					(list as LiquidList).loadSkin("track", url, context);
				break;
				case "thumb":
					_pLoadThumb = "";
					_pSkinThumb = "";
					(list as LiquidList).loadSkin("thumb", url, context);
				break;
				case "icons":
					_pLoadIcons = "";
					_pSkinIcons = "";
					(list as LiquidList).loadSkin("icons", url, context);
				break;
				case "cell":
					_pLoadCell = "";
					_pSkinCell = "";
					(list as LiquidList).loadSkin("cell", url, context);
				break;
				case "listBack":
					_pLoadListBack = "";
					_pSkinListBack = "";
					(list as LiquidList).loadSkin("listBack", url, context);
				break;
			}
		}
		
		/**
		 * open
		 * @private
		 */
		override public function open():void {
			super.open();
			drawMask();
			stage.addChild(_mask);
			_positionList = list.y;
			
			list.y = _listPosition == LIST_UP ? list.y+list.height : list.y-list.height;
			
			list.removeEventListener(Event.ENTER_FRAME, listCloseHandler);
			list.addEventListener(Event.ENTER_FRAME, listOpenHandler);
		}
		
		/**
		 * close
		 * @private
		 */
		override public function close():void {
			
			var isOn:Boolean = stage.contains(list);
			super.close();
			
			if (isOn){
				stage.addChild(list);
				list.removeEventListener(Event.ENTER_FRAME, listOpenHandler);
				list.addEventListener(Event.ENTER_FRAME, listCloseHandler);
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
			
			delete LIST_STYLES.skin;
			delete LIST_STYLES.focusRectSkin;
								
			super.configUI();
				
			removeChild(background);
			background = new LiquidButton();
			background.focusEnabled = false;
			copyStylesToChild(background, BACKGROUND_STYLES);
			background.addEventListener(MouseEvent.MOUSE_DOWN, onToggleListVisibility, false, 0, true);
			(background as LiquidButton).addEventListener(LiquidEvent.STATE_CHANGED, stateChangedHandler);
			(background as LiquidButton).addEventListener(LiquidEvent.SKIN_LOADED,skinLoadedHandler);
		
			(background as LiquidButton).label = "";
			addChild(background);
			
			// List
			list = null;
			list = new LiquidList();
			list.focusEnabled = false;
			copyStylesToChild(list, LIST_STYLES);			
			list.addEventListener(Event.CHANGE, onListChange, false, 0, true);
			list.addEventListener(ListEvent.ITEM_CLICK, onListChange, false, 0, true);
			list.addEventListener(ListEvent.ITEM_ROLL_OUT, passEvent, false, 0, true);
			list.addEventListener(ListEvent.ITEM_ROLL_OVER, passEvent, false, 0, true);
			list.verticalScrollBar.addEventListener(Event.SCROLL, passEvent, false, 0, true);


			// List mask
			_mask = new Sprite();
			list.mask = _mask;
									
			// LiquidIcon
			_icon = new LiquidIcon();
						
			// Input 
			addChild(inputField);
			addChild(_mask);
		}
		
		/**
		 * position list
		 * @private
		 */
		override protected function positionList():void {
			
			var p:Point = localToGlobal(new Point(0, 0));
			var dfx:Number = width - (isNaN(_dropdownWidth) ? list.width : _dropdownWidth);
					
			list.x = Math.floor(p.x + dfx / 2);
									
			if (p.y + height + list.height > stage.stageHeight) {
				list.y = p.y - list.height;
				_listPosition = LIST_UP;
			} else {
				list.y = p.y + height;
				_listPosition = LIST_DOWN;
			}
		}
		
		
		/**
		 * list open handler
		 * @private
		 */
		private function listOpenHandler(e:Event):void {
			var df:Number = _positionList - list.y;
			if (Math.abs(df) > EPSILON) {
				list.y += df / OPEN_SPEED;
			}else {
				list.y = _positionList;
				list.removeEventListener(Event.ENTER_FRAME, listOpenHandler);
			}
		}
		
		/**
		 * list close handler
		 * @private
		 */
		private function listCloseHandler(e:Event):void {
			var df:Number = (_listPosition == LIST_UP ? _positionList + list.height : _positionList - list.height) - list.y;
			if (Math.abs(df) > EPSILON) {
				list.y += df / OPEN_SPEED;
			}else {
				stage.removeChild(_mask);
				stage.removeChild(list);
				list.y = _positionList-list.height;
				list.removeEventListener(Event.ENTER_FRAME, listCloseHandler);
			}
		}
		
		/**
		 * drawTextFormat
		 * @private
		 */
		override protected function drawTextFormat():void {
		
			var col:Number = (background as LiquidButton).getTextColor();
			
			var tf:TextFormat = getStyleValue(_enabled ? "textFormat":"disabledTextFormat") as TextFormat;
			/*if (tf == null) { tf = new TextFormat(); }
			var tf:TextFormat = textField.getStyle("textFormat") as TextFormat;*/
			
			if (tf == null) {
				tf = new TextFormat();
				tf.color = col;
				textField.setStyle("textFormat", tf);
			}else {
				tf.color = col;
			}
			inputField.textField.defaultTextFormat = tf;
			inputField.textField.setTextFormat(tf);		
			super.setEmbedFonts();
			
		}
		
		
		/**
		 * draw layout
		 * @private
		 */
		override protected function drawLayout():void {
			
			super.drawLayout();

		}
		
		/**
		 * draw Mask
		 * @private
		 */
		protected function drawMask():void {
			
			// List mask
			_mask.graphics.clear();
			_mask.graphics.beginFill(0xFF0000, 1 );
			_mask.graphics.lineStyle(0,0 );
			_mask.graphics.drawRect(0, 0, list.width, list.height);
			_mask.graphics.endFill();
			_mask.x = list.x;
			_mask.y = list.y;
					
		}
		
		/**
		 * draw TextField
		 * @private
		 */
		override protected function drawTextField():void {
			
			super.drawTextField();
		
			// icon
			updateIcon(selectedIndex);
		}
		
		
		/**
		 * keyDownHandler
		 * @private
		 */
		override protected function keyDownHandler(event:KeyboardEvent):void {
			super.keyDownHandler(event);
		}
		
		/**
		 * update Icon
		 * @private
		 */
		protected function updateIcon(index:int = -1):void {
			
			if (_icon == null) return;
			
			if (index >= 0) {
				var item:Object = dataProvider.getItemAt(index);
				var tpd:Number = getStyleValue("textPadding") as Number;
				if (item.icon != null) {
					if (contains(_icon)) removeChild(_icon);
					_icon = item.icon.clone();
					
					_icon.x =tpd;
					_icon.y = Math.floor(height / 2 - _icon.height / 2);
									
					inputField.x = _icon.width + tpd*2;
					
					addChild(_icon);
				}else {
					if (contains(_icon)) removeChild(_icon);
					inputField.x = tpd;
					_icon = null;
				}
			}
		}
		
		/**
		 * highlight cell
		 * @private
		 */
		override protected function highlightCell(index:int = -1):void {
			
			super.highlightCell(index);

			// update icon
			updateIcon(index);
		
		}
		
		/**
		 * initialize the liquid component
		 * @private
		 */
		protected function initializeLiquid():void {
			
			// skin
			setStyle("focusRectSkin", LiquidManager.getFocusSkinBox());
			setStyle("skin", SkinAsset.getBitmapData("LiquidList_back"));
			
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
			
			var cmp:LiquidComboBox = e.target as LiquidComboBox;
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
			if (_pSkinBack != "" && _pLoadBack == "") {
				setSkin("back",SkinAsset.getBitmapData(_pSkinBack));
			}
			if (_pLoadBack != "") loadSkin("back", _pLoadBack);
			
			// listBack
			if (_pSkinListBack != "" && _pLoadListBack == "") {
				setSkin("listBack", SkinAsset.getBitmapData(_pSkinListBack));
			}
			if (_pLoadListBack != "") loadSkin("listBack", _pLoadListBack);
			
			// upArrow
			if (_pSkinUpArrow != "" && _pLoadUpArrow == "") {
				setSkin("upArrow",SkinAsset.getBitmapData(_pSkinUpArrow));
			}
			if (_pLoadUpArrow != "") loadSkin("upArrow", _pLoadUpArrow);
					
			// downArrow
			if (_pSkinDownArrow != "" && _pLoadDownArrow == "") {
				setSkin("downArrow",SkinAsset.getBitmapData(_pSkinDownArrow));
			}
			if (_pLoadDownArrow != "") loadSkin("downArrow", _pLoadDownArrow);
			
			// thumb
			if (_pSkinThumb != "" && _pLoadThumb == "") {
				setSkin("thumb", SkinAsset.getBitmapData(_pSkinThumb));
			}
			if (_pLoadThumb != "") loadSkin("thumb", _pLoadThumb);
		
			// track
			if (_pSkinTrack != "" && _pLoadTrack == "") {
				setSkin("track", SkinAsset.getBitmapData(_pSkinTrack));
			}
			if (_pLoadTrack != "") loadSkin("track", _pLoadTrack);
			
			// icons
			if (_pSkinIcons != "" && _pLoadIcons == "") {
				setSkin("icons", SkinAsset.getBitmapData(_pSkinIcons));
			}
			if (_pLoadIcons != "") loadSkin("icons", _pLoadIcons);
			
			// cell
			if (_pSkinCell != "" && _pLoadCell == "") {
				setSkin("cell", SkinAsset.getBitmapData(_pSkinCell));
			}
			if (_pLoadCell != "") loadSkin("cell", _pLoadCell);
			
		}
		
		/**
		 * stateChangedHandler
		 * @private
		 */
		protected function stateChangedHandler(e:LiquidEvent):void {
			if (e.target.parent == background) {
				drawTextFormat();
			}
		}
		
		/**
		 * skin loaded
		 * @private
		 */
		protected function skinLoadedHandler(e:LiquidEvent):void {
			drawTextFormat();
		}
		
	}
}
		