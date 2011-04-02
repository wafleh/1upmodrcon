/**
*
*
*	DataEvent
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
	public class DataEvent extends Event{
		
		// ------------------------------------------------
		//
		// ---o events 
		//
		// ------------------------------------------------
		
		public static const DATA_CHANGE:String = "DataChangeEvent";
		public static const ITEM_ADDED:String = "DataItemAddedEvent";
		public static const ITEM_REMOVED:String = "DataItemRemovedEvent";
		
		public var item:*;
		
		// ------------------------------------------------
		//
		// ---o constructor 
		//
		// ------------------------------------------------
		
		public function DataEvent(type:String,
									bubbles:Boolean = false,
							   		cancelable:Boolean = false,
									pItem:*=null) {
																	   
			super(type, bubbles, cancelable);
			item = pItem;
		}
		
		// ------------------------------------------------
		//
		// ---o methods 
		//
		// ------------------------------------------------
		
		override public function clone():Event{
			return new DataEvent(type, bubbles,cancelable,item);
		}
		
	}
}