package com.verticalcue.misc
{
	import com.liquid.controls.LiquidButton;
	import com.liquid.controls.LiquidComboBox;
	import com.liquid.controls.LiquidList;
	import com.liquid.controls.LiquidTextInput;
	import com.verticalcue.fonts.df3.Impact;
	import com.verticalcue.fonts.df3.Verdana;
	import fl.controls.DataGrid;
	import fl.controls.dataGridClasses.DataGridColumn;
	import flash.desktop.NativeApplication;
	import flash.desktop.NativeProcess;
	import flash.desktop.NativeProcessStartupInfo;
	import flash.desktop.SystemTrayIcon;
	import flash.display.*;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import fl.controls.List;
	import flash.events.NativeWindowDisplayStateEvent;
	import flash.events.ScreenMouseEvent;
	import flash.events.TimerEvent;
	import flash.filesystem.*;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	import flash.net.FileFilter;
	import flash.system.Capabilities;
	import flash.system.System;
	import flash.text.TextFormat;
	import com.verticalcue.misc.bowser.*;
	import flash.utils.Timer;
	import fl.events.ListEvent;
	import flash.xml.XMLDocument;
	import flash.xml.XMLNode;
	
	/**
	 * ...
	 * @author John Vrbanac
	 */
	public class Main extends Sprite 
	{
		[Embed(source = '../../../../assets/ServerMonitorGui.swf', symbol = 'SettingsWindow')]
		private var SettingsWindow:Class;
		[Embed(source = '../../../../assets/ServerMonitorGui.swf', symbol = 'ServerInfoWindow')]
		private var ServerInfoWindow:Class;
		[Embed(source = '../../../../assets/icon/mushroom32.png')]
		private var MushroomIcon:Class;
		[Embed(source = '../../../../assets/ServerMonitorGui.swf', symbol = 'GUIBox')]
		private var GUIBox:Class;
		private var _window:NativeWindow;
		private var _bg:Sprite;
		private var _serverList:ServerList;
		private var _sList:DataGrid;
		private var _clientList:DataGrid;
		private var _timer:Timer;
		private var _sysTrayIcon:SystemTrayIcon;
		private var _srvWindow:*; 
		private var _settingsWindow:*;
		private var _selectedServer:Server;
		private var _file:File = File.desktopDirectory;
		private var _setPath:String = "";
		private var _setRefreshRate:int = 300000;
		private var _windowFilters:Array;
		private var _linuxEffects:Boolean = true;
		private var _updater:Updater = new Updater();
		private var _version:String = "0.6.2";

		public function Main():void 
		{			
			stage.scaleMode = StageScaleMode.NO_SCALE;
			
			loadSettings();			
			
			// Setup Tray Icon
			NativeApplication.nativeApplication.autoExit = false;
			if (NativeApplication.supportsSystemTrayIcon) { 
				var menu:NativeMenu = new NativeMenu();
				var mush:Bitmap = new MushroomIcon(); 
				var exitItem:NativeMenuItem = new NativeMenuItem("Exit");
				NativeApplication.nativeApplication.icon.bitmaps = [mush.bitmapData];
				_sysTrayIcon = NativeApplication.nativeApplication.icon as SystemTrayIcon;
				_sysTrayIcon.addEventListener(ScreenMouseEvent.CLICK, sysTrayIconClicked); 
				exitItem.addEventListener(Event.SELECT, exitItemSelected);
				menu.addItem(exitItem);
				_sysTrayIcon.menu = menu; 
			}
			
			// Force close initial window
			stage.nativeWindow.close();
			
			// Create New Window
			var windowOptions:NativeWindowInitOptions = new NativeWindowInitOptions();
			windowOptions.systemChrome = NativeWindowSystemChrome.NONE;
			windowOptions.transparent = true;
			windowOptions.type = NativeWindowType.UTILITY;
			_window = new NativeWindow(windowOptions);
			_window.stage.scaleMode = StageScaleMode.NO_SCALE;
			
			// Add Background
			_bg = new GUIBox();
			_bg.x = -78;
			_bg.y = -125;
			_windowFilters = _bg.getChildByName("bg").filters;
			if (!_linuxEffects) {
				_bg.getChildByName("bg").filters = [];
				_bg.x += 1;
			}
			_bg.addEventListener(MouseEvent.MOUSE_DOWN, bgMouseDown);

			// Settings Bounds
			if (!_linuxEffects)
				_window.bounds = new Rectangle(0, 0, 206, 294);
			else
				_window.bounds = new Rectangle(0, 0, 238, 345);

			
			var settingsButton:Sprite = Sprite(_bg.getChildByName("settingsButton"));
			settingsButton.alpha = 0;
			settingsButton.addEventListener(MouseEvent.CLICK, settingsButtonClicked);
			var minButton:Sprite = Sprite(_bg.getChildByName("minAppButton"));
			minButton.alpha = 0;
			minButton.addEventListener(MouseEvent.CLICK, minButtonClicked);
			var closeButton:Sprite = Sprite(_bg.getChildByName("closeAppButton"));
			closeButton.alpha = 0;
			closeButton.addEventListener(MouseEvent.CLICK, closeButtonClicked);
			
			// Setup Default TextFormat
			var font:Verdana = new Verdana();
			var tf:TextFormat = new TextFormat();
			tf.font = "Verdana";
			tf.size = 9.5;
			tf.color = 0xFFFFFF;
			tf.kerning += 2;
			
			// Setup List
			_sList = new DataGrid();
			_sList.x = -70;
			_sList.y = -50;
			_sList.width = 206.0;
			_sList.height = 220.0;
			_sList.setRendererStyle( "embedFonts", true );
			_sList.setRendererStyle("textFormat", tf);
			_sList.setStyle("cellRenderer", AntiAliasCellRenderer);
			_sList.headerHeight = 0;
			_sList.addEventListener(ListEvent.ITEM_CLICK, sListItemClick);
			_window.stage.addChild(_bg);
			_window.stage.addChild(_sList);
			_window.activate();
			
			// Get Server List
			_serverList = ServerList.getInstance();
			_serverList.addEventListener(Event.COMPLETE, serverListLoaded);
			_serverList.getRemoteServerData();
			
			// Update List every 1min
			_timer = new Timer(_setRefreshRate);
			//_timer = new Timer(1000);
			_timer.addEventListener(TimerEvent.TIMER, timerTick);
			_timer.start();

			_updater.linuxEffects = _linuxEffects;
			_updater.checkForUpdate(loadCurrentVersion());
		}
		
		private function setApplicationVersion():void {
            var appXML:XML = NativeApplication.nativeApplication.applicationDescriptor;
            var ns:Namespace = appXML.namespace();
        }
		private function settingsButtonClicked(e:MouseEvent):void 
		{
			if (_settingsWindow == null) {
				createSettingsWindow();
			}
			else {
				_window.stage.removeChild(_settingsWindow);
				_settingsWindow = null;
			}
				
		}
		
		private function createSettingsWindow():void
		{
			_settingsWindow = new SettingsWindow();
			_settingsWindow.x = -64;
			_settingsWindow.y = -52;		
			_settingsWindow.getChildByName("backArrow").addEventListener(MouseEvent.MOUSE_OVER, mouseOverBackArrow);
			_settingsWindow.getChildByName("backArrow").addEventListener(MouseEvent.MOUSE_OUT, mouseOutBackArrow);
			_settingsWindow.getChildByName("backArrow").addEventListener(MouseEvent.CLICK, mouseClickBackArrow);
			
			if (Capabilities.os.indexOf("Linux") == -1)
				_settingsWindow.getChildByName("linuxEffectsLabel").visible = false;
			else 
				_settingsWindow.getChildByName("linuxEffectsLabel").visible = true;
			
			if (Capabilities.os.indexOf("Mac") == -1)
				_settingsWindow.getChildByName("mac_note").visible = false;
			else 
				_settingsWindow.getChildByName("mac_note").visible = true;	
				
			// Setup Default TextFormat
			var tf:TextFormat = new TextFormat();
			tf.font = "Verdana";
			tf.size = 9.5;
			tf.color = 0x000000;
			tf.kerning += 2;
			
			var refreshCbx:LiquidComboBox = new LiquidComboBox();
			refreshCbx.x = 69;
			refreshCbx.y = 31;
			refreshCbx.width = 74;
			refreshCbx.height = 22;
			refreshCbx.loadSkin("back", "./skin/subWindowComboBox_back.png");
			refreshCbx.loadSkin("cell", "./skin/subWindowList_cell.png");
			refreshCbx.addItem( {data:10000, label:"10sec"} );
			refreshCbx.addItem( {data:30000, label:"30sec"} );
			refreshCbx.addItem( {data:60000, label:"1min"} );
			refreshCbx.addItem( {data:300000, label:"5min"} );
			refreshCbx.addItem( { data:600000, label:"10min" } );
			refreshCbx.addEventListener(ListEvent.ITEM_CLICK, refreshRateSelected);
			refreshCbx.name = "refresh";
			for (var i:int = 0; i < 5; i++)
				if (refreshCbx.getItemAt(i).data == _setRefreshRate)
					refreshCbx.selectedIndex = i;
					
			var linuxEffects:LiquidComboBox = new LiquidComboBox();
			linuxEffects.y = 155;
			linuxEffects.x = 95;
			linuxEffects.width = 70;
			linuxEffects.loadSkin("back", "./skin/subWindowComboBox_back.png");
			linuxEffects.loadSkin("cell", "./skin/subWindowList_cell.png");
			linuxEffects.addItem( {data:"disable", label:"Disable"} );
			linuxEffects.addItem( {data:"enable", label:"Enable" } );
			linuxEffects.addEventListener(ListEvent.ITEM_CLICK, linuxEffectSelected);
			linuxEffects.name = "linuxEffects";		
			if (_linuxEffects)
				linuxEffects.selectedIndex = 1;
			else 
				linuxEffects.selectedIndex = 0;
			
			var terrorPathInput:LiquidTextInput = new LiquidTextInput();
			terrorPathInput.y = 90;
			terrorPathInput.x = 12;
			terrorPathInput.width = 169;
			terrorPathInput.loadSkin("back", "./skin/subWindowInput_back.png");
			terrorPathInput.name = "path";
			terrorPathInput.text = _setPath;
			
			var browseButton:LiquidButton = new LiquidButton();
			browseButton.label = "Browse";
			browseButton.y = 120;
			browseButton.width = 60;
			browseButton.x = _settingsWindow.width / 2 - browseButton.width / 2;
			browseButton.loadSkin("back", "./skin/subWindowButton_back.png");
			browseButton.addEventListener(MouseEvent.MOUSE_UP, settingsBrowseButtonClicked);
			
			if (Capabilities.os.indexOf("Linux") != -1)
				_settingsWindow.addChild(linuxEffects);
			
			_settingsWindow.addChild(browseButton);
			_settingsWindow.addChild(terrorPathInput);
			_settingsWindow.addChild(refreshCbx);
			
			//_settingsWindow.getChildByName("debug").text = Capabilities.os;
			
			var rollover:Sprite = Sprite(_settingsWindow.getChildByName("backArrow").getChildByName("arrowGraphicRollover"));
			rollover.visible = false;
			_window.stage.addChild(_settingsWindow);
		}
		
		private function loadCurrentVersion():String
		{
			var file:File = File.applicationStorageDirectory.resolvePath("update.xml");
			if (file.exists) {
				var fs:FileStream = new FileStream();
				fs.open(file, FileMode.READ);
				var xml:XML = XML(fs.readUTFBytes(fs.bytesAvailable));
				fs.close();
				return String(xml.version.text());
			}
			return _version;
		}
		
		private function linuxEffectSelected(e:ListEvent):void 
		{
			_linuxEffects = LiquidComboBox(e.currentTarget).getItemAt(parseInt(e.rowIndex.toString())).data == "enable" ? true : false;
			if (_linuxEffects)
				_bg.getChildByName("bg").filters = _windowFilters;
			else
				_bg.getChildByName("bg").filters = [];
			saveSettings();
		}
		
		private function refreshRateSelected(e:ListEvent):void 
		{
			_setRefreshRate = LiquidComboBox(e.currentTarget).getItemAt(parseInt(e.rowIndex.toString())).data;
			_timer.delay = _setRefreshRate;
			saveSettings();
		}
		
		private function settingsBrowseButtonClicked(e:MouseEvent):void 
		{
			_file.addEventListener(Event.SELECT, urbanTerrorPathSelected);
			if (Capabilities.os == "Linux")
				_file.browseForOpen("Urban Terror Application", [new FileFilter("Urban Terror", "*.x86_64; *.i386")]);
			else
				_file.browseForOpen("Urban Terror Application", [new FileFilter("Urban Terror", "*.exe", "APP")]);
		}
		
		private function urbanTerrorPathSelected(e:Event):void 
		{
			_setPath = _file.nativePath;
			if (Capabilities.os.indexOf("Mac") != -1) 
				_setPath = _setPath.replace(".exe", ".app");
			LiquidTextInput(_settingsWindow.getChildByName("path")).text = _setPath;
			saveSettings();
		}
		private function saveSettings():void
		{			
			var outFile:File = _file.resolvePath(File.applicationStorageDirectory.nativePath + "/settings.xml");
			var fs:FileStream = new FileStream();
			fs.open(outFile, FileMode.WRITE);
			fs.writeUTFBytes(XML("<?xml version=\"1.0\"?><settings><path>" + _setPath + "</path><refresh>" + _setRefreshRate.toString() + "</refresh><linuxEffects>" + _linuxEffects.toString() + "</linuxEffects></settings>"));
			fs.close();
		}
		private function loadSettings():void
		{
			var inFile:File = _file.resolvePath(File.applicationStorageDirectory.nativePath + "/settings.xml");
			if (inFile.exists) {
				var fs:FileStream = new FileStream();
				fs.open(inFile, FileMode.READ);
				var t:XML = XML(fs.readUTFBytes(fs.bytesAvailable));
				_setPath = t.path.toString();
				_setRefreshRate = parseInt(t.refresh.toString());
				_linuxEffects = t.linuxEffects.toString() == "false" ? false : true;
				if (_setRefreshRate < 500)
					_setRefreshRate = 60000;
			}
		}
		private function launchUrbanTerror(server:Server):void
		{
			if(NativeProcess.isSupported)
			{
				var tmpPath:String = _setPath;
				var file:File = File.desktopDirectory;
				file = file.resolvePath(tmpPath);
				
				var npsi:NativeProcessStartupInfo = new NativeProcessStartupInfo();
				if (Capabilities.os.indexOf("Mac") != -1)
					tmpPath += "/Contents/MacOS/ioUrbanTerror.ub";
				npsi.executable = file.resolvePath(tmpPath);
					
				if (Capabilities.os.indexOf("Windows") != -1)
					npsi.workingDirectory = file.resolvePath(tmpPath.substr(0, tmpPath.lastIndexOf("\\")));
				else
					npsi.workingDirectory = file.resolvePath(tmpPath.substr(0, tmpPath.lastIndexOf("/")));
					
				npsi.arguments.push("+connect");
				npsi.arguments.push(server.ip);
				var process:NativeProcess = new NativeProcess();

				process.start(npsi);
			}
		}
		private function sListItemClick(e:ListEvent):void 
		{
			createServerWindow(_serverList.getServerByName(e.item.server));
		}
		private function createServerWindow(data:Server):void
		{
			if (data) {
				_selectedServer = data;
				_srvWindow = new ServerInfoWindow();
				_srvWindow.x = -64;
				_srvWindow.y = -52;
				_srvWindow.getChildByName("backArrow").addEventListener(MouseEvent.MOUSE_OVER, mouseOverBackArrow);
				_srvWindow.getChildByName("backArrow").addEventListener(MouseEvent.MOUSE_OUT, mouseOutBackArrow);
				_srvWindow.getChildByName("backArrow").addEventListener(MouseEvent.CLICK, mouseClickBackArrow);
				_srvWindow.getChildByName("joinButton").addEventListener(MouseEvent.CLICK, joinServerButtonClicked);
				_srvWindow.getChildByName("serverName").text = data.name;
				_srvWindow.getChildByName("serverMap").text = "Map: " + data.map;
				
				// Setup Default TextFormat
				var tf:TextFormat = new TextFormat();
				tf.font = "Verdana";
				tf.size = 9.5;
				tf.color = 0xFFFFFF;
				tf.kerning += 2;
				
				_clientList = new DataGrid();
				_clientList.y = 55;
				_clientList.width = 195;
				_clientList.height = 160;
				_clientList.setRendererStyle( "embedFonts", true );
				_clientList.setRendererStyle("textFormat", tf);
				_clientList.setStyle("cellRenderer", AntiAliasCellRenderer);
				_clientList.headerHeight = 0;
				_clientList.columns = ["a", "b", "c"];
				
				for each (var client:Client in data.clients) {
					_clientList.addItem( {a: client.name, b: client.points, c: client.ping} );
				}

				if (data.clients.length > 0 ) {
					_clientList.columns[1].setWidth(30);
					_clientList.columns[2].setWidth(33);
					_srvWindow.addChild(_clientList);
				}
				
				var rollover:Sprite = Sprite(_srvWindow.getChildByName("backArrow").getChildByName("arrowGraphicRollover"));
				rollover.visible = false;
				_window.stage.addChild(_srvWindow);
			}
		}
		
		private function joinServerButtonClicked(e:MouseEvent):void 
		{
			if (_setPath != "") {
				launchUrbanTerror(_selectedServer);
			} else {
				_window.stage.removeChild(_srvWindow);
				_srvWindow = null;
				_selectedServer = null;
				createSettingsWindow();
			}
		}
		
		private function mouseClickBackArrow(e:MouseEvent):void 
		{
			if (_srvWindow) {
				_window.stage.removeChild(_srvWindow);
			}
			if (_settingsWindow) {
				saveSettings();
				_window.stage.removeChild(_settingsWindow);
			}
			_settingsWindow = null;
			_srvWindow = null;
			_selectedServer = null;
		}
		
		private function mouseOutBackArrow(e:MouseEvent):void 
		{
			if (_srvWindow) {
				var rollover:Sprite = Sprite(_srvWindow.getChildByName("backArrow").getChildByName("arrowGraphicRollover"));
				var normal:Sprite = Sprite(_srvWindow.getChildByName("backArrow").getChildByName("arrowGraphic"));
				normal.visible = true;
				rollover.visible = false;
			}
		}
		
		private function mouseOverBackArrow(e:MouseEvent):void 
		{
			if (_srvWindow) {
				var rollover:Sprite = Sprite(_srvWindow.getChildByName("backArrow").getChildByName("arrowGraphicRollover"));
				var normal:Sprite = Sprite(_srvWindow.getChildByName("backArrow").getChildByName("arrowGraphic"));
				normal.visible = false;
				rollover.visible = true;
			}
		}
		
		private function sysTrayIconClicked(e:ScreenMouseEvent):void 
		{
			_window.visible = false;
			toggleHide();
		}
		
		private function toggleHide():void
		{
			if (_window.visible) {
				_window.visible = false;
			}
			else {
				_window.visible = true;
				_window.activate();
				_window.alwaysInFront = true;
				_window.alwaysInFront = false;
			}
		}
		
		private function exitItemSelected(e:Event):void 
		{
			_window.close();
			_timer.stop();
			var exitTimer:Timer = new Timer(100);
			exitTimer.addEventListener(TimerEvent.TIMER, exitApplicationTimer);
			exitTimer.start();
		}
		
		private function exitApplicationTimer(e:TimerEvent):void 
		{
			var exitingEvent:Event = new Event(Event.EXITING, false, true);
			NativeApplication.nativeApplication.dispatchEvent(exitingEvent);
			NativeApplication.nativeApplication.exit();
		}
		
		private function timerTick(e:TimerEvent):void 
		{
			//trace("Collecting garbage...");
			//gc();
			trace("Reloading Server Data");
			_serverList.reloadServerData();
		}
		
		private function serverListLoaded(e:Event):void 
		{
			trace("Server List Loaded!");
			var srvCol:DataGridColumn;
			var plyCol:DataGridColumn;
			_sList.removeAll();
			for each (var srvObj:Server in _serverList.list) {
				_sList.addItem( { server: srvObj.name, players: srvObj.clients.length + "/" + srvObj.maxClients } );
				if (_srvWindow) {
					if (srvObj.name == _srvWindow.getChildByName("serverName").text) {
						_clientList.columns = ["a", "b", "c"];
						_clientList.removeAll();
						for each (var client:Client in srvObj.clients) {
							_clientList.addItem( {a: client.name, b: client.points, c: client.ping} );
						}
						_clientList.columns[1].setWidth(30);
						_clientList.columns[2].setWidth(33);
					}					
				}
				
				trace(srvObj.name);
				
			}
			
			for each (var col:DataGridColumn in _sList.columns) {
				if (col.headerText == "server") {
					col.setWidth(135);
					srvCol = col;
				} else if (col.headerText == "players") {
					col.setWidth(37);
					plyCol = col;
				}
			}
			_sList.removeAllColumns();
			_sList.addColumn(srvCol);
			_sList.addColumn(plyCol);
		}
		
		private function minButtonClicked(e:MouseEvent):void 
		{
			toggleHide();
		}
		
		private function closeButtonClicked(e:MouseEvent):void 
		{			
			exitItemSelected(null);
		}
		
		private function bgMouseDown(e:MouseEvent):void 
		{
			_window.startMove();
		}
		
		// This seems to remove some weak listeners so do not use
		private function gc():void
		{
			System.gc();
		}
	}
	
}
