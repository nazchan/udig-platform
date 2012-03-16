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

import java.io.File;
import java.util.Collection;

import net.refractions.udig.project.internal.Map;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * This wraps the auto generated MapItemProvider. This implementation returns no children and only displays map details.  
 * <p>
 * <ul>
 * <li></li>
 * </ul>
 * </p>
 * @author Naz Chan (LISAsoft)
 * @since 1.3.1
 */
public class MapItemNoProvider extends MapItemProvider {

    public MapItemNoProvider( AdapterFactory adapterFactory ) {
        super(adapterFactory);
    }

    @Override
    public Collection< ? extends EStructuralFeature> getChildrenFeatures( Object object ) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            // Adds no children features
        }
        return childrenFeatures;
    }
    
    @Override
    public String getText( Object object ) {
        Map map = ((Map) object);
        String label = map.getName();
        if (label == null) {
            Resource resource = map.eResource();
            if (resource != null) {
                String toString = resource.toString();
                int lastSlash = toString.lastIndexOf(File.pathSeparator);
                if (lastSlash == -1) lastSlash = 0;
                label = toString.substring(lastSlash);
            }
        }
        return label == null || label.length() == 0 ? "Unable to load map" : label;
    }    
    
}
