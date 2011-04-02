/**
*
*
*	LiquidListCellRenderer
*
* 	This class is distributed under Creative Commons License.
* 	http://creativecommons.org/licenses/by/2.0/fr/
*	
*	@author		Didier Brun
*	@version	1.1
* 	@link		http://www.bytearray.org
*
*/

package com.liquid.listClasses{

	import com.liquid.controls.LiquidButton;
	import com.liquid.display.LiquidIcon;
	import com.liquid.skin.SkinAsset;
	import com.liquid.events.LiquidEvent;
		
	import fl.controls.listClasses.ICellRenderer;
    import fl.controls.listClasses.ListData;
	import fl.controls.ButtonLabelPlacement;
	
	import flash.events.MouseEvent;
	import flash.display.DisplayObject;
	
	/**
	 *  @private
	 */
	public class LiquidListCellRenderer extends LiquidButton implements ICellRenderer {
		
		// -----------------------------------------------
		//
		// ---o properties
		//
		// -----------------------------------------------
		
		private var _listData:ListData;
        private var _data:Object;
		
		// -----------------------------------------------
		//
		// ---o constructor
		//
		// -----------------------------------------------
		
		function LiquidListCellRenderer() {
			
			super();
			
			toggle = true;
			focusEnabled = false;
		}
		
		// -----------------------------------------------
		//
		// ---o public methods
		//
		// -----------------------------------------------
		
		/**
		 * listData
		 */
		public function set listData(newListData:ListData):void {
		    _listData = newListData;
			
			label = newListData.label;
			
			if (_data.icon != null) {
				
				if (icon!=null){
					removeChild(icon);
					icon = null;
				}
				
				if (icon!=_data.icon){
					icon = _data.icon;
					icon.addEventListener(LiquidEvent.SKIN_LOADED, iconLoadedHandler);
				}
			}
		}

		/**
		 * get listData
		 */
		public function get listData():ListData {
            return _listData;
        }
	
		/**
		 * set data
		 */
        public function set data(newData:Object):void {
            _data = newData;
        }

		/**
		 * get data
		 */
        public function get data():Object {
            return _data;
        }
		
		// -----------------------------------------------
		//
		// ---o protected methods
		//
		// -----------------------------------------------
		
		/**
		 * toggle selected
		 */
		override protected function toggleSelected(event:MouseEvent):void {
			selected = !selected;
		}
		
		/**
		 * draw layout
		 */
		override protected function drawLayout():void {
			
			var textPadding:Number = Number(getStyleValue("textPadding"));
			var textFieldX:Number = 0;
			
			// Align icon
			if (icon != null) {
				icon.x = textPadding;
				icon.y = Math.round((height-icon.height)>>1);
				textFieldX = icon.width + textPadding;
			}
			
			// Align text
			if (label.length > 0) {
				textField.visible = true;
				var textWidth:Number =  Math.max(0, width - textFieldX - textPadding*2);
				textField.width = textWidth;
				textField.height = textField.textHeight + 4;
				textField.x = textFieldX + textPadding
				textField.y = Math.round((height-textField.height)>>1);
			} else {
				textField.visible = false;
			}
			
			// Size background
			background.width = width;
			background.height = height;
		}
		
		/**
		 * icon loaded
		 * @private
		 */
		override protected function iconLoadedHandler(e:LiquidEvent):void {
			e.target.removeEventListener(LiquidEvent.SKIN_LOADED, iconLoadedHandler);
			drawLayout();		
		}
		
	}
}


