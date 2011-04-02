/**
*
*
*	LiquidManager
* 
* 	This class is distributed under Creative Commons License.
* 	http://creativecommons.org/licenses/by/2.0/fr/
*	
*	@author		Didier Brun
*	@version	1.1
* 	@link		http://www.bytearray.org
*	
*
*/

package com.liquid.core {
	
	import fl.core.UIComponent;
	import flash.display.DisplayObject;
	import flash.display.BitmapData;
	import flash.events.Event;
	import flash.events.EventPhase;
	import flash.system.LoaderContext;
	
	import com.liquid.core.ILiquidComponent;
	import com.liquid.core.SkinBox;
	import com.liquid.events.LiquidEvent;
	import com.liquid.skin.SkinAsset;
	import com.liquid.skin.SkinLoader;
	
	/**
	* 
	*/
	public class LiquidManager {
		
		// ------------------------------------------------
		//
		// ---o public static properties
		//
		// ------------------------------------------------
		
		private static var DEFAULT_FOCUS_WIDTH:int = 100;
		private static var DEFAULT_FOCUS_HEIGHT:int = 22;
		private static var DEFAULT_FOCUS_SKIN:String = "Liquid_focusRect";
		
		private static var __focusRect:Boolean = false;
		
		private static var __skinFocus:SkinBox;
				
		// ------------------------------------------------
		//
		// ---o public static methods
		//
		// ------------------------------------------------
		
		/**
		 * register component
		 * 
		 * @private
		 */
		public static function register(cp:UIComponent):void {
			
			// focusRect
			if (!__focusRect) {
				initFocusRect();
			}
		}
		
		/**
		 * Set a shared BitmapData skin for Liquid Components.
		 * 
		 * Note : By default, LiquidManager attach the default BitmapData class in the library : Liquid_focusRect
		 * 
		 * @example 	<listing version="3.0" >LiquidManager.setSkin("focusRect",myBitmapId(0,0));</listing> 
		 * 
		 * @param id	String indentifier, this property take the folowing value :
		 * <p><table class=innertable>
		 * <tr><th>id</th><th>Description</th></tr>
		 * <tr><td>focusRect</td><td>Focus rectangle of all components</td></tr>
		 * </table></p>
		 * @param bmp	BitmapData instance
		 */
		public static function setSharedSkin(id:String, bmp:BitmapData):void {
				
			switch (id) {
				case "focusRect":
					if (bmp != null) {
						__skinFocus.setSkin(bmp);
					}
				break;
			}
		}
		
		/**
		 * getFocusSkinBox
		 * 
		 * @private
		 */
		public static function getFocusSkinBox():SkinBox {
			return __skinFocus;
		}
		
		/**
		 * Load an external bitmap skin file and shared it for the Liquid Components.
		 * 
		 * Note : By default, LiquidManager attach the default BitmapData class in the library : Liquid_focusRect
		 * 
		 * @example 	<listing version="3.0" >LiquidManager.loadSharedSkin("focusRect","./skin/mySkin.png");</listing> 
		 * 
		 * @param id	String indentifier, this property take the folowing value :
		 * <p><table class=innertable>
		 * <tr><th>id</th><th>Description</th></tr>
		 * <tr><td>focusRect</td><td>Focus rectangle of all components</td></tr>
		 * </table></p>
		 * @param url		URL of the bitmap file
		 * @param context 	A LoaderContext object, which has properties that define the following :
  
  <ul>
  
  <li>Whether or not Flash Player should check for the existence of a policy file 
  upon loading the object</li>

  
  <li>The ApplicationDomain for the loaded object</li>
  
  <li>The SecurityDomain for the loaded object</li>
  
  </ul>
  
  <p>For complete details, see the description of the properties in the 
  <a target='_blank' href="http://livedocs.adobe.com/flash/9.0/ActionScriptLangRefV3/flash/system/LoaderContext.html">LoaderContext</a> class.</p>
		 */
		public static function loadSharedSkin(id:String, url:String, context:LoaderContext=null):void {
			switch (id) {
				case "focusRect":
					load_focusRect(url, context);
				break;
			}
		}
		
		// ------------------------------------------------
		//
		// ---o private static methods
		//
		// ------------------------------------------------
		
		/**
		 * initFocusRect
		 */
		private static function initFocusRect():void {
			__focusRect = true;
			__skinFocus = new SkinBox(DEFAULT_FOCUS_WIDTH, DEFAULT_FOCUS_HEIGHT);			
			__skinFocus.setSkin(SkinAsset.getBitmapData(DEFAULT_FOCUS_SKIN));			
		}
		
		/**
		 * load back
		 */
		private static function load_focusRect(url:String, context:LoaderContext = null):void {
			if (url != "") {
				
				var sl:SkinLoader = SkinAsset.loadSkin(url, context);
				
				if (sl.getState() == SkinLoader.STATE_LOADED) {
					
					__skinFocus.setSkin(sl.getBitmap());
					__skinFocus.drawNow();

					}else{
					sl.addEventListener(LiquidEvent.SKIN_LOADED, focusRectLoaded);
				}
			}
		}
		
		/**
		 * focusRect loaded
		 */
		private static function focusRectLoaded(e:Event):void {
			
			var sl:SkinLoader = e.target as SkinLoader;
			sl.removeEventListener(LiquidEvent.SKIN_LOADED, focusRectLoaded);
			
			__skinFocus.setSkin(sl.getBitmap());
			__skinFocus.drawNow();
		}
		
	
		
	}
	
	
}