package com.verticalcue.misc.bowser
{
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	
	/**
	 * ...
	 * @author John Vrbanac
	 */
	public class ServerList extends EventDispatcher
	{
		private static var _instance:ServerList;
		private static var _allowInstantiation:Boolean;
		private var _list:Vector.<Server>;
		private var _asyncProcComplete:int = 0;
		private var _asyncProcTotal:int = 0;
		
		public function ServerList() 
		{
			if (!_allowInstantiation) 
				throw new Error("Error! Use getInstance() on this Singleton");
			else 
				_list = new Vector.<Server>();
		}
		
		public static function getInstance():ServerList
		{
			if (!_instance) {
				_allowInstantiation = true;
				_instance = new ServerList();
				_allowInstantiation = false;
			}
			return _instance;
		}
	
		public function getRemoteServerData():void
		{
			var urlReq:URLRequest = new URLRequest("http://1upclan.info/servers.xml");
			var urlLoader:URLLoader = new URLLoader();
			urlLoader.addEventListener(Event.COMPLETE, remoteServerDataLoaded, false, 0, true);
			urlLoader.load(urlReq);
		}
		private function remoteServerDataLoaded(e:Event):void 
		{
			var xml:XML = new XML(String(e.target.data));
			var servers:XMLList = xml.channel.item;
			_asyncProcTotal = servers.length();
			for each (var srv:XML in servers) {
				var srvObj:Server = new Server();
				srvObj.name = srv.title.toString();
				srvObj.ip = srv.description.toString();
				srvObj.link = srv.link.toString();
				
				var bowser:BowserQuery = new BowserQuery(srvObj.ip, 27960);
				bowser.sender = srvObj;
				bowser.addEventListener(BowserEvent.RESPONSE, bowserEventReceived, false, 0, true);
				bowser.send("getstatus");
				
				_list.push(srvObj);
			}
		}
		public function reloadServerData():void {
			_asyncProcComplete = 0;
			for each (var srv:Server in _list) {
				var bowser:BowserQuery = new BowserQuery(srv.ip, 27960);
				bowser.sender = srv;
				bowser.addEventListener(BowserEvent.RESPONSE, bowserEventReceived, false, 0, true);
				bowser.send("getstatus");
			}
		}
		
		private function bowserEventReceived(e:BowserEvent):void 
		{
			for each (var srv:Server in _list) {
				if (srv == e.sender) {
					var data:Array = e.response.split("\n");
					var privateClients:int = parseInt(String(data[1]).match(/\\sv_privateClients\\(.*?)\\/)[1]);
					srv.maxClients = parseInt(String(data[1]).match(/\\sv_maxclients\\(.*?)\\/)[1])/* - (isNaN(privateClients) ? 0 : privateClients)*/;
					if (data.length > 2) {
						srv.clients = new Vector.<Client>();
						for (var i:int = 2; i < data.length; i++) {
							var clientData:Array = String(data[i]).match(/(\d*)\s(\d*)\s\"(.*)\"/);
							if (clientData)
								srv.clients.push(new Client(clientData[3], clientData[2], clientData[1]));
						}
					}
				}
			}
			_asyncProcComplete++;
			if (_asyncProcComplete == _asyncProcTotal ) {
				dispatchEvent(new Event(Event.COMPLETE));
			}
		}
		public function getServerByName(name:String):Server
		{
			for each (var srv:Server in _list)
				if (srv.name == name)
					return srv;
			return null;
		}
		public function get list():Vector.<Server> { return _list; }
		
	}

}