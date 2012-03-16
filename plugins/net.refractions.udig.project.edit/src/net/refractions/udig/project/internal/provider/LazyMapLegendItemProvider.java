/* uDig - User Friendly Desktop Internet GIS client
 * http://udig.refractions.net
 * (C) 2004-2011, Refractions Research Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation;
 * version 2.1 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 */
package net.refractions.udig.project.internal.provider;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.refractions.udig.project.internal.Map;
import net.refractions.udig.project.internal.ProjectPackage;
import net.refractions.udig.project.internal.impl.SynchronizedEList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;

/**
 * TODO Purpose of 
 * <p>
 * <ul>
 * <li></li>
 * </ul>
 * </p>
 * @author nchan
 * @since 1.2.0
 */
public class LazyMapLegendItemProvider extends AbstractLazyLoadingItemProvider
        implements
            IEditingDomainItemProvider,
            IStructuredItemContentProvider,
            ITreeItemContentProvider,
            IItemLabelProvider,
            IItemPropertySource {

    protected MapItemProvider delegate;
    
    public LazyMapLegendItemProvider( AdapterFactory adapterFactory ) {
        super(adapterFactory);
        this.delegate = new MapItemProvider(adapterFactory);
    }
    
    public LazyMapLegendItemProvider( AdapterFactory adapterFactory, MapItemProvider mapItemProvider ) {
        super(adapterFactory);
        this.delegate = mapItemProvider;
    }
    
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors( Object object ) {
        return delegate.getPropertyDescriptors(object);
    }

    @Override
    public IItemPropertyDescriptor getPropertyDescriptor( Object object, Object propertyID ) {
        return delegate.getPropertyDescriptor(object, propertyID);
    }

    @Override
    public Object getEditableValue( Object object ) {
        return delegate.getEditableValue(object);
    }

    @Override
    public String getText( Object object ) {
        return delegate.getText(object);
    }

    @Override
    public Object getImage( Object object ) {
        return delegate.getImage(object);
    }

    @Override
    public Object getParent( Object object ) {
        return delegate.getParent(object);
    }

    @Override
    public Collection< ? > getNewChildDescriptors( Object object, EditingDomain editingDomain,
            Object sibling ) {
        return delegate.getNewChildDescriptors(object, editingDomain, sibling);
    }

    @Override
    public Command createCommand( Object object, EditingDomain editingDomain,
            Class< ? extends Command> commandClass, CommandParameter commandParameter ) {
        return delegate.createCommand(object, editingDomain, commandClass, commandParameter);
    }

    @Override
    protected Collection< ? extends EStructuralFeature> getChildrenFeatures( Object object ) {
        if (childrenFeatures == null) {
            childrenFeatures.add(ProjectPackage.Literals.MAP__LEGEND);
        }
        return childrenFeatures;
    }
    
    @Override
    public boolean hasChildren( Object object ) {
        return true;
    }

    @Override
    protected Collection< ? extends Object> getConcreteChildren( Object object ) {
        if (object instanceof Map) {
            return ((Map) object).getLegend();
        }
        throw new IllegalArgumentException("Object must be a Map.  Was: " + object); //$NON-NLS-1$
    }
    
    @Override
    public Object getChild( Object object, int childIndex ) {
        if (object instanceof Map) {
            final Map map = (Map) object;
            if (childIndex >= map.getLegend().size()) return null;
            return map.getLegend().get(childIndex);
        }
        throw new IllegalArgumentException("Object must be a Map.  Was: " + object); //$NON-NLS-1$
    }
    
    @Override
    protected ChildFetcher createChildFetcher() {
        return new ChildFetcher(this){
            protected void notifyChanged() {
                LazyMapLegendItemProvider.this.notifyChanged(new ENotificationImpl(
                        (InternalEObject) parent, Notification.SET, ProjectPackage.MAP__LEGEND,
                        LayerLoadingPlaceHolder.LOADING_LAYER, null));
            }         
        };
    }

    @Override
    protected LoadingPlaceHolder getLoadingItem() {
        return LayerLoadingPlaceHolder.LOADING_LAYER;
    }
    
    @Override
    public void notifyChanged( Notification notification ) {
        delegate.notifyChanged(notification);
    }
    
}
