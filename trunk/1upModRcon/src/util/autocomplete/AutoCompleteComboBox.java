package util.autocomplete;

import javax.swing.*;

/**
 * This class uses AutoCompleteComboBoxEditor and AutoCompleteTextField to
 * offer and Auto Completion to typed text based on the Objects added to the
 * ComboBox.
 * ** IMPORTANT **
 * Since this is a text-based utility any Object added to this ComboBox (even
 * those that you create) MUST, I repeat MUST have a valid toString method that
 * returns the portion text representation of your
 * @author Brandon Buck
 * @version 1.0
 */
public class AutoCompleteComboBox<E> extends JComboBox {
    private AutoCompleteTextField editorComp;

    public AutoCompleteComboBox() {
        super();
        AutoCompleteComboBoxEditor cbe = new AutoCompleteComboBoxEditor();
        this.editorComp = (AutoCompleteTextField)(cbe.getEditorComponent());
        this.setEditor(cbe);
        this.setEditable(true);
    }

    @Override
    public void addItem(Object anObject) {
        super.addItem(anObject);
        editorComp.addPossibility(anObject.toString());
    }

    @Override
    public void removeAllItems() {
        super.removeAllItems();
        editorComp.removeAllPossibilities();
    }
    
    public AutoCompleteTextField getEditorComponent() {
        return editorComp;
    }

    @Override
    public void removeItemAt(int index) {
        E anObject = getItemAt(index);
        super.removeItemAt(index);
        editorComp.removePossibility(anObject.toString());
    }

    @Override
    public E getItemAt(int index) {
        return (E)super.getItemAt(index);
    }

    @Override
    public E getSelectedItem() {
        return (E)super.getSelectedItem();
    }
}
