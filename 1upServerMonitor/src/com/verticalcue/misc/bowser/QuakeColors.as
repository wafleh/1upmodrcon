package com.verticalcue.misc.bowser 
{
	/**
	 * ...
	 * @author John Vrbanac
	 */
	public class QuakeColors
	{
		public static const BLACK:int = 0;
		public static const RED:int = 1;
		public static const GREEN:int = 2;
		public static const YELLOW:int = 3;
		public static const BLUE:int = 4;
		public static const CYAN:int = 5;
		public static const PINK:int = 6;
		public static const WHITE:int = 7;
		public static const BLACK8:int = 8;
		public static const colorArray:Array = ["000000", "EB3928", "66FF00", "FFFF00", "307FC4", "33FFFF", "FF33CC", "FFFFFF", "000000"];
		
		public function QuakeColors() 
		{
			
		}
		public static function convertName(name:String):String
		{
			var convertName:String = name;
			var sArray:Array = name.split("^");
			if (sArray.length > 1) {
				convertName = "";
				for each (var str:String in sArray) {
					if (str != "") {
						convertName += "<font color=\"#" + QuakeColors.colorArray[parseInt(str.substring(0,1))] + "\">" + str.substring(1) + "</font>";
					}
				}
			}
			return convertName;
		}
		private static function getColor(number:int):String {
			return QuakeColors.colorArray[number];
		}
	}

}