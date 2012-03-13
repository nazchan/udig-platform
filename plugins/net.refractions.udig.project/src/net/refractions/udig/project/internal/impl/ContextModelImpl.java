/**
 * <copyright></copyright> $Id$
 */
package net.refractions.udig.project.internal.impl;

import java.util.Collection;
import java.util.List;

import net.refractions.udig.project.internal.ContextModel;
import net.refractions.udig.project.internal.Layer;
import net.refractions.udig.project.internal.Map;
import net.refractions.udig.project.internal.ProjectPackage;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.opengis.filter.Filter;

import com.vividsolutions.jts.geom.Envelope;

/**
 * ContextModel responsible for holding on to layers for an IMap.
 * <p>
 * This class has several deprecated methods for working with the layers list
 * but they have all been moved to Map.
 * </p>
 * @author Jesse
 * @since 1.0.0
 * @generated
 */
@SuppressWarnings("deprecation")
public class ContextModelImpl extends EObjectImpl implements ContextModel {

    private LayersList2 layers = new LayersList2(Layer.class, this,
            ProjectPackage.CONTEXT_MODEL__LAYERS, ProjectPackage.LAYER__CONTEXT_MODEL);

    @SuppressWarnings("unchecked")
    protected ContextModelImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ProjectPackage.Literals.CONTEXT_MODEL;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated not
     */
    @SuppressWarnings("unchecked")
    public List<Layer> getLayers() {
        return layers;

    }

    /**
     * Typesafe Layer access as a workaround for EMF generation bug.
     * 
     * @return
     * @deprecated
     */
    public List<Layer> layers() {
        return getLayers();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Map getMap() {
        if (eContainerFeatureID() != ProjectPackage.CONTEXT_MODEL__MAP) return null;
        return (Map) eContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetMap( Map newMap, NotificationChain msgs ) {
        msgs = eBasicSetContainer((InternalEObject) newMap, ProjectPackage.CONTEXT_MODEL__MAP, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setMap( Map newMap ) {
        if (newMap != eInternalContainer()
                || (eContainerFeatureID() != ProjectPackage.CONTEXT_MODEL__MAP && newMap != null)) {
            if (EcoreUtil.isAncestor(this, newMap))
                throw new IllegalArgumentException(
                        "Recursive containment not allowed for " + toString()); //$NON-NLS-1$
            NotificationChain msgs = null;
            if (eInternalContainer() != null) msgs = eBasicRemoveFromContainer(msgs);
            if (newMap != null)
                msgs = ((InternalEObject) newMap).eInverseAdd(this,
                        ProjectPackage.MAP__CONTEXT_MODEL, Map.class, msgs);
            msgs = basicSetMap(newMap, msgs);
            if (msgs != null) msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    ProjectPackage.CONTEXT_MODEL__MAP, newMap, newMap));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd( InternalEObject otherEnd, int featureID,
            NotificationChain msgs ) {
        switch( featureID ) {
        case ProjectPackage.CONTEXT_MODEL__LAYERS:
            return ((InternalEList<InternalEObject>) (InternalEList< ? >) getLayers()).basicAdd(
                    otherEnd, msgs);
        case ProjectPackage.CONTEXT_MODEL__MAP:
            if (eInternalContainer() != null) msgs = eBasicRemoveFromContainer(msgs);
            return basicSetMap((Map) otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove( InternalEObject otherEnd, int featureID,
            NotificationChain msgs ) {
        switch( featureID ) {
        case ProjectPackage.CONTEXT_MODEL__LAYERS:
            return ((InternalEList< ? >) getLayers()).basicRemove(otherEnd, msgs);
        case ProjectPackage.CONTEXT_MODEL__MAP:
            return basicSetMap(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eBasicRemoveFromContainerFeature( NotificationChain msgs ) {
        switch( eContainerFeatureID() ) {
        case ProjectPackage.CONTEXT_MODEL__MAP:
            return eInternalContainer().eInverseRemove(this, ProjectPackage.MAP__CONTEXT_MODEL,
                    Map.class, msgs);
        }
        return super.eBasicRemoveFromContainerFeature(msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet( int featureID, boolean resolve, boolean coreType ) {
        switch( featureID ) {
        case ProjectPackage.CONTEXT_MODEL__LAYERS:
            return getLayers();
        case ProjectPackage.CONTEXT_MODEL__MAP:
            return getMap();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet( int featureID, Object newValue ) {
        switch( featureID ) {
        case ProjectPackage.CONTEXT_MODEL__LAYERS:
            getLayers().clear();
            getLayers().addAll((Collection< ? extends Layer>) newValue);
            return;
        case ProjectPackage.CONTEXT_MODEL__MAP:
            setMap((Map) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset( int featureID ) {
        switch( featureID ) {
        case ProjectPackage.CONTEXT_MODEL__LAYERS:
            getLayers().clear();
            return;
        case ProjectPackage.CONTEXT_MODEL__MAP:
            setMap((Map) null);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet( int featureID ) {
        switch( featureID ) {
        case ProjectPackage.CONTEXT_MODEL__LAYERS:
            return layers != null && !layers.isEmpty();
        case ProjectPackage.CONTEXT_MODEL__MAP:
            return getMap() != null;
        }
        return super.eIsSet(featureID);
    }

    public void addDeepAdapter( Adapter adapter ) {
        getMap().addDeepAdapter(adapter);
    }

    public void removeDeepAdapter( Adapter adapter ) {
        getMap().removeDeepAdapter(adapter);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public void lowerLayer( Layer layer ) {
        getMap().lowerLayer(layer);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public void raiseLayer( Layer layer ) {
        getMap().raiseLayer(layer);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated NOT
     */
    public boolean eIsSet( EStructuralFeature eFeature ) {
        switch( eDerivedStructuralFeatureID(eFeature) ) {
        case ProjectPackage.CONTEXT_MODEL__LAYERS:
            return getLayers() != null && !getLayers().isEmpty();
        case ProjectPackage.CONTEXT_MODEL__MAP:
            return getMap() != null;
        }
        return eDynamicIsSet(eFeature);
    }

    /**
     * Turns off emf notification
     * 
     * @param notify true if notifications should be used.
     */
    public void setNotification( boolean notify ) {
        if (notify)
            eFlags = eFlags | (EDELIVER);
        else
            eFlags = eFlags & (~EDELIVER);
    }

    public void select( Envelope boundingBox ) {
        getMap().select(boundingBox);
    }

    public void select( Envelope boundingBox, boolean and ) {
        getMap().select(boundingBox, and);
    }

    public void select( Filter filter ) {
        getMap().select(filter);
    }

    public void select( Filter filter, boolean and ) {
        getMap().select(filter, and);
    }

    private volatile EList eAdapters;

    @Override
    public EList eAdapters() {
        if (eAdapters == null) {
            synchronized (this) {
                if (eAdapters == null) {
                    eAdapters = new SynchronizedEList(super.eAdapters());
                }
            }
        }
        return eAdapters;
    }

}
