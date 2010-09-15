package util.autocomplete;

import javax.swing.plaf.basic.BasicComboBoxEditor;

/**
 *
 * @author Brandon Buck
 * @version 1.0
 */
public class AutoCompleteComboBoxEditor extends BasicComboBoxEditor {
    public AutoCompleteComboBoxEditor() {
        editor = createEditorComponent();
    }

    @Override
    protected AutoCompleteTextField createEditorComponent() {
        AutoCompleteTextField acEditor = new AutoCompleteTextField(9);
        acEditor.setBorder(null);
        return acEditor;
    }
}
