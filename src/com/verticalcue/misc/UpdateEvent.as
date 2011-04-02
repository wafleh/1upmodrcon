package com.verticalcue.misc 
{
	import flash.events.Event;
	
	/**
	 * ...
	 * @author John Vrbanac
	 */
	public class UpdateEvent extends Event 
	{
		public static const COMPLETE:String = "complete";
		public static const PROGRESS:String = "progress";
		public static const UPDATE_AVAILABLE:String = "available";
		public static const NO_UPDATE:String = "no update";
		
		public function UpdateEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) 
		{ 
			super(type, bubbles, cancelable);
			
		} 
		
		public override function clone():Event 
		{ 
			return new UpdateEvent(type, bubbles, cancelable);
		} 
		
		public override function toString():String 
		{ 
			return formatToString("UpdateEvents", "type", "bubbles", "cancelable", "eventPhase"); 
		}
		
	}
	
}