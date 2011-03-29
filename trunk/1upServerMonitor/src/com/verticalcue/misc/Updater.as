package com.verticalcue.misc 
{
	import flash.desktop.NativeProcess;
	import flash.desktop.NativeProcessStartupInfo;
	import flash.display.MovieClip;
	import flash.display.NativeWindow;
	import flash.display.NativeWindowInitOptions;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.IEventDispatcher;
	import flash.events.MouseEvent;
	import flash.events.ProgressEvent;
	import flash.net.URLLoader;
	import flash.net.URLLoaderDataFormat;
	import flash.net.URLRequest;
	import flash.display.NativeWindowSystemChrome;
	import flash.display.NativeWindowType;
	import flash.display.StageScaleMode;
	import flash.filesystem.*;
	import flash.system.Capabilities;
	import flash.system.System;
	import flash.utils.ByteArray;
	import flash.geom.Rectangle;
	import flash.desktop.NativeApplication;
	
	/**
	 * ...
	 * @author John Vrbanac
	 */
	public class Updater extends EventDispatcher
	{
		[Embed(source = '../../../../assets/ServerMonitorGui.swf', symbol = 'UpdateAvailableWindow')]
		private var UpdateWindow:Class;
		private var _loader:URLLoader;
		private var _updateXml:XML;
		private var _curVersion:String;
		private var _window:NativeWindow;
		private var _updateGui:*;
		private var _downloadUrl:String = "";
		public var linuxEffects:Boolean = true;
		
		
		public function Updater(target:IEventDispatcher = null) 
		{
			super(target);
			_loader = new URLLoader();
			_loader.addEventListener(Event.COMPLETE, updateXmlFileComplete, false, 0, true);
			
		}
		
		public function activateWindow():void
		{
			_updateGui = new UpdateWindow();
			var winOptions:NativeWindowInitOptions = new NativeWindowInitOptions();
			winOptions.systemChrome = NativeWindowSystemChrome.NONE;
			winOptions.transparent = true;
			winOptions.type = NativeWindowType.UTILITY;
			_window = new NativeWindow(winOptions);
			_window.stage.addChild(_updateGui);
			_window.stage.scaleMode = StageScaleMode.NO_SCALE;
			
			_window.width = _updateGui.width + 10;
			_window.height = _updateGui.height + 10;
			_updateGui.x = -146;
			_updateGui.y = -41;
			_window.activate();
			_window.orderToFront();
			_window.alwaysInFront = true;
			_updateGui.addEventListener(MouseEvent.MOUSE_DOWN, mouseDownOnBackground, false, 0, true);
			_updateGui.getChildByName("status").text = "Update Available: " + _updateXml.version.text();
			_updateGui.getChildByName("cancelButton").addEventListener(MouseEvent.MOUSE_DOWN, cancelMouseDown, false, 0, true);
			_updateGui.getChildByName("updateButton").addEventListener(MouseEvent.MOUSE_DOWN, updateMouseDown, false, 0, true);

			if (!linuxEffects) {
				_window.bounds = new Rectangle(0, 0, 365, 155);
			}
		}
		
		private function updateMouseDown(e:MouseEvent):void 
		{
			if (Capabilities.os.indexOf("Win") != -1 || Capabilities.os.indexOf("Lin") != -1 || Capabilities.os.indexOf("Mac") != -1){
				_loader = new URLLoader();
				_loader.dataFormat = URLLoaderDataFormat.BINARY;
				_loader.addEventListener(Event.COMPLETE, updateFileDownloadComplete);
				_loader.addEventListener(ProgressEvent.PROGRESS, updateFileProgressEvent);
				
				if (Capabilities.os.indexOf("Win") != -1)
					_downloadUrl = _updateXml.windowsUrl.text();
				else if (Capabilities.os.indexOf("Lin") != -1)
					_downloadUrl = _updateXml.ubuntuUrl.text();
				else if (Capabilities.os.indexOf("Mac") != -1)
					_downloadUrl = _updateXml.macUrl.text();
					
				_loader.load(new URLRequest(_downloadUrl));
			} else {
				_updateGui.getChildByName("status").text = "Error! Updater is currently not supported on this operating system.";
				_window.close();
			}
		}
		
		private function updateFileProgressEvent(e:ProgressEvent):void 
		{
			_updateGui.getChildByName("status").text = "Downloading Update: " + Number(e.bytesTotal / e.bytesLoaded) * 100 + "%";
		}
		
		private function saveNewUpdateXml():void
		{
			var file:File = File.applicationStorageDirectory.resolvePath("update.xml");
			var fs:FileStream = new FileStream();
			fs.open(file, FileMode.WRITE);
			fs.writeUTFBytes(_updateXml.toXMLString());
			fs.close();
		}
		
		private function updateFileDownloadComplete(e:Event):void 
		{
			var url:String = String(_downloadUrl);
			var file:File = File.applicationStorageDirectory.resolvePath(url.substring(url.lastIndexOf("/") + 1));
			var fs:FileStream = new FileStream();
			fs.open(file, FileMode.WRITE);
			fs.writeBytes(ByteArray(e.target.data));
			fs.close();
			saveNewUpdateXml();
			_window.close();
						
			NativeApplication.nativeApplication.exit();
			
			var proc:NativeProcess = new NativeProcess();
			var procInfo:NativeProcessStartupInfo = new NativeProcessStartupInfo();
			var scFile:File;
			procInfo.workingDirectory = File.applicationStorageDirectory;
			
			if (Capabilities.os.indexOf("Win") != -1) {
				procInfo.executable = file;
			} else if (Capabilities.os.indexOf("Lin") != -1) {
				scFile = File.applicationStorageDirectory.resolvePath("/usr/bin/software-center");
				procInfo.arguments.push(file.nativePath);
				procInfo.executable = scFile;
			} else if (Capabilities.os.indexOf("Mac") != -1) {
				scFile = File.applicationStorageDirectory.resolvePath("/usr/bin/open");
				procInfo.arguments.push(file.nativePath);
				procInfo.executable = scFile;
			}
			
			proc.start(procInfo);
		}
		
		private function cancelMouseDown(e:MouseEvent):void 
		{
			_window.close();
		}
		
		private function mouseDownOnBackground(e:MouseEvent):void 
		{
			_window.startMove();
		}
		
		public function checkForUpdate(currentVersion:String):void
		{
			_curVersion = currentVersion;
			_loader.load(new URLRequest("http://1upmodrcon.googlecode.com/svn/trunk/1upServerMonitor/update/update.xml"));
			trace("Checking for update...");
		}
		
		private function updateXmlFileComplete(e:Event):void 
		{
			_loader.removeEventListener(Event.COMPLETE, updateXmlFileComplete);
			_updateXml = XML(e.currentTarget.data);
			if ( checkVersion(_curVersion, _updateXml.version.text()) ) {
				dispatchEvent(new UpdateEvent(UpdateEvent.UPDATE_AVAILABLE));
				activateWindow();
				trace("New Update");
			}
			else {
				dispatchEvent(new UpdateEvent(UpdateEvent.NO_UPDATE));
				trace("No Update");
			}
		}
		
		private function checkVersion(currentVersion:String, newVersion:String):Boolean
		{
			var arr:Array = equalizeVersions(currentVersion, newVersion);
			var va:int = parseInt(arr[0]);
			var vb:int = parseInt(arr[1]);
			
			if (va < vb)
				return true;
				
			return false;
		}
		
		private function equalizeVersions(str1:String, str2:String):Array
		{
			var array1:Array = str1.split(".");
			var array2:Array = str2.split(".");
			var diff:int = 0;
			var j:int;
			var add:String;
			
			for (var i:int = 0; i < array1.length; i++) {
				if (array1[i].length > array2[i].length) {
					diff = array1[i].length - array2[i].length;
					add = "";
					for (j = 0; j < diff; j++)
						add += "0";
					array2[i] = add + array2[i];
				} else if (array1[i].length < array2[i].length) {
					diff = array2[i].length - array1[i].length;
					add = "";
					for (j = 0; j < diff; j++)
						add += "0";
					array1[i] = add + array1[i];
				}
			}
			return [array1.join(""), array2.join("")];
		}
		
	}

}
