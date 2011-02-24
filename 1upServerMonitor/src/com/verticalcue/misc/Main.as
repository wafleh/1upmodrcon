package com.verticalcue.misc
{
	import com.verticalcue.fonts.df3.Impact;
	import com.verticalcue.fonts.df3.Verdana;
	import fl.controls.DataGrid;
	import fl.controls.dataGridClasses.DataGridColumn;
	import flash.desktop.NativeApplication;
	import flash.desktop.SystemTrayIcon;
	import flash.display.*;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import fl.controls.List;
	import flash.events.NativeWindowDisplayStateEvent;
	import flash.events.ScreenMouseEvent;
	import flash.events.TimerEvent;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	import flash.text.TextFormat;
	import com.verticalcue.misc.bowser.*;
	import flash.utils.Timer;
	import fl.events.ListEvent;
	
	/**
	 * ...
	 * @author John Vrbanac
	 */
	public class Main extends Sprite 
	{
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

		public function Main():void 
		{			
			stage.scaleMode = StageScaleMode.NO_SCALE;
			
			// Setup Tray Icon
			NativeApplication.nativeApplication.autoExit = false;
			if (NativeApplication.supportsSystemTrayIcon) { 
				var menu:NativeMenu = new NativeMenu()
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
			_window.bounds = new Rectangle(0, 0, 238, 345);
			
			// Add Background
			_bg = new GUIBox();
			_bg.x = -78;
			_bg.y = -125;
			_bg.addEventListener(MouseEvent.MOUSE_DOWN, bgMouseDown);
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
			_timer = new Timer(60000);
			_timer.addEventListener(TimerEvent.TIMER, timerTick);
			_timer.start();
		}
		
		private function sListItemClick(e:ListEvent):void 
		{
			createServerWindow(_serverList.getServerByName(e.item.server));
		}
		private function createServerWindow(data:Server):void
		{
			if (data) {
				_srvWindow = new ServerInfoWindow();
				_srvWindow.x = -64;
				_srvWindow.y = -52;		
				_srvWindow.getChildByName("backArrow").addEventListener(MouseEvent.MOUSE_OVER, mouseOverBackArrow);
				_srvWindow.getChildByName("backArrow").addEventListener(MouseEvent.MOUSE_OUT, mouseOutBackArrow);
				_srvWindow.getChildByName("backArrow").addEventListener(MouseEvent.CLICK, mouseClickBackArrow);
				_srvWindow.getChildByName("serverName").text = data.name;
				
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
				
				for each (var client:Client in data.clients) {
					_clientList.addItem( {a: client.name, b: client.points, c: client.ping} );
				}
				
				if (data.clients.length > 0 ) {
					_clientList.columns[1].setWidth(30);
					_clientList.columns[2].setWidth(30);
					
					_srvWindow.addChild(_clientList);
				}
				var rollover:Sprite = Sprite(_srvWindow.getChildByName("backArrow").getChildByName("arrowGraphicRollover"));
				rollover.visible = false;
				_window.stage.addChild(_srvWindow);
			}
		}
		
		private function mouseClickBackArrow(e:MouseEvent):void 
		{
			_window.stage.removeChild(_srvWindow);
			_srvWindow = null;
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
			toggleHide();
		}
		
		private function toggleHide():void
		{
			if (_window.visible) {
				_window.visible = false;
			}
			else {
				_window.activate();
				_window.alwaysInFront = true;
				_window.alwaysInFront = false;
			}
		}
		
		private function exitItemSelected(e:Event):void 
		{
			NativeApplication.nativeApplication.exit();
		}
		
		private function timerTick(e:TimerEvent):void 
		{
			_serverList.reloadServerData();
		}
		
		private function serverListLoaded(e:Event):void 
		{
			var srvCol:DataGridColumn;
			var plyCol:DataGridColumn;
			_sList.removeAll();
			for each (var srvObj:Server in _serverList.list) {
				_sList.addItem( { server: srvObj.name, players: srvObj.clients.length + "/" + srvObj.maxClients } );
				if (_srvWindow) {
					if (srvObj.name == _srvWindow.getChildByName("serverName").text) {
						_clientList.removeAll();
						for each (var client:Client in srvObj.clients) {
							_clientList.addItem( {a: client.name, b: client.points, c: client.ping} );
						}
						_clientList.columns[1].setWidth(30);
						_clientList.columns[2].setWidth(30);
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
			_window.close();
			NativeApplication.nativeApplication.exit();
		}
		
		private function bgMouseDown(e:MouseEvent):void 
		{
			_window.startMove();
		}
		
	}
	
}