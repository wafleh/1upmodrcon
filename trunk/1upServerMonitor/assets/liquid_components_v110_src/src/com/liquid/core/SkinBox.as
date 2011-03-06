/**
*
*
*	SkinBox
*
* 	This class is distributed under Creative Commons License.
* 	http://creativecommons.org/licenses/by/2.0/fr/
*	
*	@author		Didier Brun
*	@version	1.1
* 	@link		http://www.bytearray.org 
*
*/

package com.liquid.core{

	import com.liquid.draw.LiquidBitmap;
	import com.liquid.grid.LiquidGrid;
	import com.liquid.draw.LiquidMapper;
	import com.liquid.skin.SkinAsset;
	import com.liquid.events.LiquidEvent;
	
	import flash.display.BitmapData;
	import flash.display.Sprite;
	import flash.events.Event;
	
	/**
	* @private
	*/
	public class SkinBox extends Sprite {
				
		// ------------------------------------------------
		//
		// ---o properties
		//
		// ------------------------------------------------
		
		private var _grid:LiquidGrid;				// grid
		private var _skin:LiquidBitmap;				// skin
	
		private var _state:int = 0;					// current state
		private var _skinReady:Boolean = false;		// skin ready
		private var _colors:Array;					// colors from skin
		
		private var _width:Number;					// width
		private var _height:Number;					// height

		// ------------------------------------------------
		//
		// ---o contructor
		//
		// ------------------------------------------------
		
		function SkinBox(pW:Number,pH:Number){
			
			// size
			_width = pW;
			_height = pH;
			
		}
		
		// ------------------------------------------------
		//
		// ---o public methods
		//
		// ------------------------------------------------
		
		/**
		 * is ready
		 */
		public function get isReady():Boolean {
			return _skinReady;
		}
		
		/**
		 * get min width
		 */
		public function get minWidth():Number {
			return _grid.minWidth;
		}
		
		/**
		 * get min height
		 */
		public function get minHeight():Number {
			return _grid.minHeight;
		}
		
		/**
		 * public function set size
		 */
		public function setSize(pw:Number, ph:Number):void {
			_width = pw;
			_height = ph;
			
			if (_skinReady){
				_grid.setSize(_width, _height);
				render();
			}
		}
		
		/**
		 * public set width
		 */
		override public function set width(pw:Number):void {
			setSize(pw, _height);
		}
		
		/**
		 * publi function set height
		 */
		override public function set height(ph:Number):void {
			setSize(_width, ph);
		}
		

		/**
		 * set state
		 */
		public function set state(s:int):void {
			if (_state != s) {
				_state = s;
				dispatchEvent(new LiquidEvent(LiquidEvent.STATE_CHANGED,true));
				render();
			}		
		}
		
		/**
		 * get nb layers
		 */
		public function nbLayers():int {
			if (_skinReady){
				return _skin.getNbLayers();
			}else {
				return 0;
			}
		}
		
		/**
		* get state
		*/
		public function get state():int {
			return _state;
		}
		
		/**
		 * set skin
		 */
		public function setSkin(pBmp:BitmapData):void {
			if (pBmp != null) {
				
				var asset:SkinAsset = new SkinAsset(pBmp);
					
				_grid = asset.grid.clone();
				_skin = asset.liquidBitmap;
				_colors = asset.colors;
				_skinReady = true;
				
				render();
			}
		}
		
		/**
		 * get state color
		 */
		public function getColor():Number {
			if (_colors == null) return 0;
			if (_colors.length == 0) return 0;
			if (_state > _colors.length - 1) return _colors[0];
			return _colors[_state];
		}
		
		/**
		 * draw now
		 */
		public function drawNow():void {
			_grid.setSize(_width, _height);
			render();
		}
		
		/**
		 * reset size
		 */
		public function resetSize():void {
			setSize(_skin.width, _skin.height);
		}

		// ------------------------------------------------
		//
		// ---o protected methods
		//
		// ------------------------------------------------
			
		/**
		 * render
		 */
		private function render():void {
			if (_skinReady) {
				graphics.clear();
				LiquidMapper.drawShape(_grid, _skin, graphics, _state);
			}
		}
		
				
		
	}
	
}

