package com.verticalcue.misc 
{
	import fl.controls.listClasses.CellRenderer;
	import flash.text.AntiAliasType;
	/**
	 * ...
	 * @author John Vrbanac
	 */
	public class AntiAliasCellRenderer extends CellRenderer
	{
		
		public function AntiAliasCellRenderer() 
		{
			super();
			
		}
		override protected function drawLayout():void {
            textField.antiAliasType = AntiAliasType.ADVANCED
			textField.htmlText = textField.text;
            super.drawLayout();
        }
	}

}