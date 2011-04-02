/**
*
*
*	ScrollerEvent
*
* 	This class is distributed under Creative Commons License.
* 	http://creativecommons.org/licenses/by/2.0/fr/
*	
*	@author		Didier Brun
*	@version	1.1
* 	@link		http://www.bytearray.org
*
*/

package com.liquid.events{
	
	import flash.events.Event;

	/**
	* @private
	*/
	public class ScrollerEvent extends Event{
		
		// ------------------------------------------------
		//
		// ---o events 
		//
		// ------------------------------------------------
		
		public static const SCROLL_START:String = "LiquidScrollStartEvent";
		public static const SCROLL_STOP:String = "LiquidScrollStopEvent";
		public static const SCROLL_CHANGE:String = "LiquidScrollChangeEvent";
		public static const PAGE_UP:String = "LiquidScrollPageUpEvent";
		public static const PAGE_DOWN:String = "LiquidScrollPageDownEvent";
		public static const INVALID_RANGE:String = "LiquidInvalidRangeEvent";
		public static const VALID_RANGE:String = "LiquidValidRangeEvent";
	
		// ------------------------------------------------
		//
		// ---o constructor 
		//
		// ------------------------------------------------
		
		public function ScrollerEvent(type:String,
									bubbles:Boolean = false,
							   		cancelable:Boolean = false){
																	   
			super(type, bubbles, cancelable);
		}
		
		// ------------------------------------------------
		//
		// ---o methods 
		//
		// ------------------------------------------------
		
		override public function clone():Event{
			return new ScrollerEvent(type, bubbles,cancelable);
		}
		
	}
}