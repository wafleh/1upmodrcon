/**
*
*
*	LiquidEvent
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
	public class LiquidEvent extends Event{
		
		// ------------------------------------------------
		//
		// ---o events 
		//
		// ------------------------------------------------
		
	
		public static const SKIN_LOADED:String = "skinLoadedEvent";
		public static const STATE_CHANGED:String = "stateChangeddEvent";
		
		
		// ------------------------------------------------
		//
		// ---o constructor 
		//
		// ------------------------------------------------
		
		public function LiquidEvent(type:String,
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
			return new LiquidEvent(type, bubbles,cancelable);
		}
		
	}
}