/**
*
*
*	LiquidError
*
* 	This class is distributed under Creative Commons License.
* 	http://creativecommons.org/licenses/by/2.0/fr/
*	
*	@author		Didier Brun
*	@version	1.1
* 	@link		http://www.bytearray.org
*
*/

package com.liquid.errors {
	
	/**
	* @private
	*/
	public class LiquidError extends Error { 
		
		// ------------------------------------------------
		//
		// ---o static 
		//
		// ------------------------------------------------
		
		private static var __init__:Boolean = false;
		private static var errors:Object={};
		
		public static const ERROR_DEFAULT:int = 0;
		public static const IO_ERROR:int = 1;
		
		
				
		// ------------------------------------------------
		//
		// ---o constructor 
		//
		// ------------------------------------------------
		
		function LiquidError(id:int = 0,arg:String="") {
			
			if (!__init__) init();
			
			var mess:String = errors[id];
			if (arg != "") mess = mess.split("#arg#").join(arg);
			
			super (mess, id);
			
		}
		
		// ------------------------------------------------
		//
		// ---o static methods 
		//
		// ------------------------------------------------
		
		/**
		 * static initialization
		 */
		private static function init():void {
			
			errors[ERROR_DEFAULT] = "#ERROR 00 - Liquid default error.";
			errors[IO_ERROR] = "#ERROR 01 -  Unable to load the file : #arg#";
					
			__init__ = true;
		}
		
	}
}
