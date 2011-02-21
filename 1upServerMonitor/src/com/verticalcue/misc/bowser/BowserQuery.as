package com.verticalcue.misc.bowser
{
	import flash.events.DatagramSocketDataEvent;
	import flash.events.EventDispatcher;
	import flash.net.DatagramSocket;
	import flash.net.NetworkInfo;
	import flash.net.NetworkInterface;
	import flash.utils.ByteArray;
	/**
	 * ...
	 * @author John Vrbanac
	 */
	public class BowserQuery extends EventDispatcher
	{
		
		private var _ds:DatagramSocket;
		private var _targetIp:String;
		private var _targetPort:int;
		private var _sender:Object;
		
		public function BowserQuery(ip:String, port:int) 
		{
			_targetIp = ip;
			_targetPort = port;
			_ds = new DatagramSocket();
			_ds.addEventListener(DatagramSocketDataEvent.DATA, dataReceived);			
		}
		
		private function dataReceived(e:DatagramSocketDataEvent):void 
		{
			var out:String = e.data.readUTFBytes(e.data.bytesAvailable).substring(4);
			trace("Bowser: " + out);		
			dispatchEvent(new BowserEvent(BowserEvent.RESPONSE, _sender, out, out.substring(0, out.indexOf("\n")))); 
		}
		override public function addEventListener(type:String, listener:Function, useCapture:Boolean = false, priority:int = 0, useWeakReference:Boolean = false):void 
		{
			super.addEventListener(type, listener, useCapture, priority, useWeakReference);
		}
		public function send(data:String):void {
			var bytes:ByteArray = new ByteArray();
			
			bytes.writeUTFBytes(String("xxxx" + data));
			bytes[0] = 0xff;
			bytes[1] = 0xff;
			bytes[2] = 0xff;
			bytes[3] = 0xff;
			_ds.send(bytes, 0, 0, _targetIp, _targetPort);
			_ds.receive();
		}
		
		public function get sender():Object { return _sender; }
		public function set sender(value:Object):void 
		{
			_sender = value;
		}
		
	}

}