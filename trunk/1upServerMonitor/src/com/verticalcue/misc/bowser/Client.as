package com.verticalcue.misc.bowser 
{
	/**
	 * ...
	 * @author John Vrbanac
	 */
	public class Client
	{
		private var _name:String;
		private var _ping:int;
		private var _points:int;
		public function Client(clientName:String, clientPing:int, clientPoints:int) 
		{
			name = clientName;
			_ping = clientPing;
			_points = clientPoints;
		}
		
		public function get name():String { return _name; }
		public function set name(value:String):void 
		{
			var cleanStr:String = value.replace("<", "&#60;");
			cleanStr = cleanStr.replace(">", "&#62;");
			
			if (cleanStr == "")
				cleanStr = "Unnamed Player";
			_name = QuakeColors.convertName(cleanStr);
		}
		
		public function get ping():int { return _ping; }
		public function set ping(value:int):void 
		{
			_ping = value;
		}
		
		public function get points():int { return _points; }
		public function set points(value:int):void 
		{
			_points = value;
		}
		
	}

}