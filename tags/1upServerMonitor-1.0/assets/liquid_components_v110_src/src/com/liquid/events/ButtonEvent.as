/**
*
*
*	ButtonEvent
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
	public class ButtonEvent extends Event{
		
		// ------------------------------------------------
		//
		// ---o events 
		//
		// ------------------------------------------------
		
		public static const ROLL_OVER:String = "ButtonRollOverEvent";
		public static const ROLL_OUT:String = "ButtonRollOutEvent";
		public static const RELEASE:String = "ButtonReleaseEvent";
		public static const CLICK:String = "ButtonClickEvent";
		public static const RELEASE_OUTSIDE:String = "ButtonReleaseOutsideEvent";
		public static const DOWN:String = "ButtonDownEvent";
		
		// ------------------------------------------------
		//
		// ---o constructor 
		//
		// ------------------------------------------------
		
		public function ButtonEvent(type:String,
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
			return new ButtonEvent(type, bubbles,cancelable);
		}
		
	}
}