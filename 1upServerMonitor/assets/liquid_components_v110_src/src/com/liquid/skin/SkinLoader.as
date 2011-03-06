/**
*
*
*	SkinLoader
*
* 	This class is distributed under Creative Commons License.
* 	http://creativecommons.org/licenses/by/2.0/fr/
*	
*	@author		Didier Brun
*	@version	1.1
* 	@link		http://www.bytearray.org
*
*/

package com.liquid.skin{

	import com.liquid.events.LiquidEvent;
	import com.liquid.errors.LiquidError;
	
	import flash.display.BitmapData;
	import flash.display.Loader;
	import flash.display.Bitmap;
	import flash.events.Event;
	import flash.events.IOErrorEvent;
	import flash.net.URLRequest;
	import flash.system.LoaderContext;
	
	
	/**
	* @private
	*/
	public class SkinLoader extends Loader{

		// -----------------------------------------------
		//
		// ---o properties
		//
		// -----------------------------------------------

		public static const STATE_INIT:int = 0;
		public static const STATE_LOADING:int = 1;
		public static const STATE_LOADED:int = 2;
		public static const STATE_ERROR:int = 3;
		
		private var _state:int = STATE_INIT;
		private var _bmp:BitmapData;
		private var _request:URLRequest;
	
		// -----------------------------------------------
		//
		// ---o contructor
		//
		// -----------------------------------------------
		
		function SkinLoader() {
			super();
		}
		
		// -----------------------------------------------
		//
		// ---o public methods
		//
		// -----------------------------------------------
		
		/**
		 * load
		 */
		override public function load(request:URLRequest, context:LoaderContext = null):void{
			super.load(request, context);
			
			_request = request;
			contentLoaderInfo.addEventListener(Event.COMPLETE, completeHandler);
			contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, errorHandler);
			_state == STATE_LOADING;
		}
		
		/**
		 * get state
		 */
		public function getState():int {
			return _state;
		}
		
		/**
		 * 	get bitmap
		 */
		public function getBitmap():BitmapData {
			return _bmp;
		}
		
		// -----------------------------------------------
		//
		// ---o private methods
		//
		// -----------------------------------------------
		
		/**
		 * 	complete handler
		 */
		private function completeHandler(e:Event):void {
			_bmp = (content as Bitmap).bitmapData;
			_state = STATE_LOADED;
			dispatchEvent(new LiquidEvent(LiquidEvent.SKIN_LOADED));
		}
		
		/**
		 * error handler
		 */
		private function errorHandler(e:IOErrorEvent):void {
			removeEventListener(IOErrorEvent.IO_ERROR, errorHandler);
			_state = STATE_ERROR;
			throw(new LiquidError(LiquidError.IO_ERROR, _request.url));
		}
		
		
	}
}