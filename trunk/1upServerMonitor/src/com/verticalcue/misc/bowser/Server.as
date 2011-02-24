package com.verticalcue.misc.bowser
{
	/**
	 * ...
	 * @author John Vrbanac
	 */
	public class Server
	{
		private var _name:String;
		private var _ip:String;
		private var _link:String;
		private var _maxClients:int;
		private var _clients:Vector.<Client>;
		public function Server() 
		{
			_clients = new Vector.<Client>();
		}
		
		public function get name():String { return _name; }
		public function set name(value:String):void 
		{
			_name = value;
			_name = _name.replace(/\*/g, "");
		}
		
		public function get ip():String { return _ip; }
		public function set ip(value:String):void 
		{
			_ip = value;
		}
		
		public function get link():String { return _link; }
		public function set link(value:String):void 
		{
			_link = value;
		}
		
		public function get clients():Vector.<Client> { return _clients; }
		public function set clients(value:Vector.<Client>):void 
		{
			_clients = value;
		}
		
		public function get maxClients():int { return _maxClients; }
		public function set maxClients(value:int):void 
		{
			_maxClients = value;
		}
		
	}

}