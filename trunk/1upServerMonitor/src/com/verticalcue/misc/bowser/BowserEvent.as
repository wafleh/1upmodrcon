package com.verticalcue.misc.bowser 
{
	import flash.events.Event;
	
	/**
	 * ...
	 * @author John Vrbanac
	 */
	public class BowserEvent extends Event 
	{
		public static const RESPONSE:String = "response";
		public var response:String = "";
		public var responseType:String = "";
		public var sender:Object;
		public function BowserEvent(type:String, srvSender:Object, srvResponse:String = "", srvResponseType:String = "", bubbles:Boolean=false, cancelable:Boolean=false) 
		{ 
			response = srvResponse;
			responseType = srvResponseType;
			sender = srvSender;
			super(type, bubbles, cancelable);
		} 
		
		public override function clone():Event 
		{ 
			return new BowserEvent(type, sender, response, responseType, bubbles, cancelable);
		} 
		
		public override function toString():String 
		{ 
			return formatToString("BowserEvent", "type", "bubbles", "cancelable", "eventPhase"); 
		}
		
	}
	
}