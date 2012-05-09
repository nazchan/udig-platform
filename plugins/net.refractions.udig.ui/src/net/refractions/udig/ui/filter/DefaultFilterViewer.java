/*
 *    uDig - User Friendly Desktop Internet GIS client
 *    http://udig.refractions.net
 *    (C) 2004, Refractions Research Inc.
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 *
 */
package net.refractions.udig.ui.filter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import net.miginfocom.swt.MigLayout;

import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.geotools.filter.text.cql2.CQL;
import org.geotools.filter.text.cql2.CQLException;
import org.geotools.filter.text.ecql.ECQL;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.feature.type.AttributeType;
import org.opengis.filter.Filter;
import org.opengis.filter.expression.Expression;

/**
 * A very simple {@link IFilterViewer} using a text with Constraint Query Language and a few combo
 * boxes to help with suggestions.
 * <p>
 * Remember that although Viewers are a wrapper around some SWT Control or Composite you still have
 * direct access using the getControl() method so that you can do your layout data thing.
 * </p>
 * 
 * @author Jody Garnett
 * @since 1.2.0
 */
public class DefaultFilterViewer extends CQLFilterViewer {
    /**
     * Factory hooked into eclipse extension point.
     * 
     * @author Jody Garnett
     * @since 1.3.2
     */
    public static class Factory extends FilterViewerFactory {
        @Override
        public int appropriate(SimpleFeatureType schema, Filter filter) {
            return COMPLETE;
        }
        public IFilterViewer createViewer(Composite parent, int style) {
            return new DefaultFilterViewer(parent, style);
        }
    }

    /**
     * This is the expression we are working on here.
     * <p>
     * We are never going to be "null"; Expression.EXCLUDE is used to indicate an intentionally
     * empty expression.
     */
    protected Filter filter = Filter.EXCLUDE;

    protected Composite control;

    /**
     * Combo box to allow the user to select an attribute to base a filter on
     */
    protected Combo attribute;

    /**
     * Combo box to allow the user to select an operation to apply to an attribute
     */
    protected Combo operation;

    /**
     * Text box to allow the user to enter a value to base a filter on
     */
    protected Combo value;

    protected Button insert;

    private SelectionAdapter insertButtonListener = new SelectionAdapter() {
        @Override
        public void widgetSelected(SelectionEvent e) {
            if (attribute.isFocusControl() && attribute.getSelectionIndex() != -1) {
                String selectedAttribute = attribute.getText();
                text.insert(selectedAttribute);

                attribute.clearSelection();
                changed();
                text.setFocus();
                return;
            }

            if (operation.isFocusControl() && operation.getSelectionIndex() != -1) {
                String selectedOperation = operation.getText();
                text.insert(selectedOperation);

                operation.clearSelection();

                changed();
                text.setFocus();
                return;
            }

            if (value.isFocusControl() && value.getSelectionIndex() != -1) {
                String selectedValue = value.getText();
                text.insert(selectedValue);

                value.clearSelection();

                changed();
                text.setFocus();
                return;
            }
        }
    };

    /**
     * Creates ExpressionViewer using the provided style.
     * <ul>
     * <li>SWT.SINGLE - viewer is restricted to a single line</li>
     * <li>SWT.MULTI - viewer is able to assume additional vertical space is available</li>
     * <li>SWT.READ_ONLY - read only</li>
     * </ul>
     * 
     * @param parent composite viewer is being added to
     * @param style used to layout the viewer
     */
    public DefaultFilterViewer(Composite parent, int style) {
        super(new Composite(parent, SWT.NONE), style);
        
        control = text.getParent();
        
        boolean multiLine = (SWT.MULTI & style) != 0;
        
        // ATTRIBUTE
        Label lblAttribute = null;
        if( multiLine ){
            lblAttribute = new Label(control, SWT.NONE);
            lblAttribute.setText("Attribute");
        }
        attribute = new Combo(control, SWT.SIMPLE | SWT.READ_ONLY );
        attribute.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String attributeName = attribute.getText();

                // populate values based on this attribute
                if (attributeName != null && getInput() != null && getInput().getSchema() != null) {
                    SimpleFeatureType schema = getInput().getSchema();
                    AttributeDescriptor descriptor = schema.getDescriptor(attributeName);
                    
                    SortedSet<String> suggestedValues = generateSuggestedValues( descriptor );

                    value.removeAll();
                    if (suggestedValues.isEmpty()) {
                        value.setEnabled(false);
                    } else {
                        value.setEnabled(true);
                        for (String item : suggestedValues) {
                            value.add(item);
                        }
                    }
                } else {
                    value.removeAll();
                    value.setEnabled(false);
                }
            }
        });
        
        // OPPERATIONS
        Label lblOperation = null;
        if( multiLine){
            lblOperation = new Label(control, SWT.NONE);
            lblOperation.setText("Operation:");
        }
        operation = new Combo(control, SWT.SIMPLE | SWT.READ_ONLY );
        operation.add("=");
        operation.add("<");
        operation.add(">");
        operation.add("LIKE");
        
        // VALUE COMBO
        Label lblValue = null;
        if( multiLine){
            lblValue = new Label(control, SWT.NONE);
            lblValue.setText("Value");
        }        
        value = new Combo(control, SWT.SIMPLE | SWT.READ_ONLY );
        value.setEnabled(false); // need to select an attribute before we can suggest values
        
        // INSERT BUTTON
        insert = new Button(control, SWT.NONE);
        insert.setText("Insert");
        insert.addSelectionListener(insertButtonListener);
        
        if( multiLine ){
            MigLayout layout = new MigLayout("insets 0", "[][][][][][][grow]", "[grow][]");
            control.setLayout( layout);
            
            text.setLayoutData("span,grow,width 200:100%:100%,height 60:100%:100%");
            setPreferredTextSize(40,5);
            
            lblAttribute.setLayoutData("cell 0 1,alignx trailing,gapx related");
            attribute.setLayoutData("cell 1 1,wmin 60,alignx left,gapx rel");
            
            lblOperation.setLayoutData("cell 2 1,alignx trailing,gapx related");
            operation.setLayoutData("cell 3 1,wmin 60,alignx left,gapx rel");            

            lblValue.setLayoutData( "cell 4 1,alignx trailing,gapx related");
            value.setLayoutData("cell 5 1,wmin 60,alignx left,gapx related");
            
            insert.setLayoutData("cell 6 1,alignx left,gapx unrel");
        }
        else {
            control.setLayout( new MigLayout("insets 0, flowx", "", ""));

            text.setLayoutData("grow,width 200:70%:100%, gap unrelated");
            attribute.setLayoutData("width 90:20%:100%, gap related");
            operation.setLayoutData("width 60:10%:100%, gap related");
            value.setLayoutData("width 60:10%:100%, gap related");
            insert.setLayoutData("gap related");
        }
    }
    /**
     * Used to supply a list of suggested values; given the provided attribtue descriptor.
     * 
     * @param descriptor
     * @return list of suggested values (may be empty if no values are suggested)
     */
    protected SortedSet<String> generateSuggestedValues(AttributeDescriptor descriptor) {
        SortedSet<String> options = new TreeSet<String>();

        Object defaultValue = descriptor.getDefaultValue();
        if (defaultValue != null) {
            options.add(String.valueOf(defaultValue));
        }
        AttributeType type = descriptor.getType();
        if (Number.class.isAssignableFrom(type.getBinding())) {
            options.add("0");
            options.add("1");
            options.add("2");
            options.add("3");
            options.add("4");
            options.add("5");
        }
        return options;
    }

    /**
     * This is the widget used to display the Filter; its parent has been provided in the
     * ExpressionViewer's constructor; but you may need direct access to it in order to set layout
     * data etc.
     * 
     * @return control used to display the filter
     */
    public Control getControl() {
        return control;
    }
    
    @Override
    public void refresh() {
        super.refresh(); // update text field suggestions
        
        if (input != null) {
            SortedSet<String> names = new TreeSet<String>(input.toPropertyList());
            attribute.setItems(names.toArray(new String[names.size()]));
        }
    }

}