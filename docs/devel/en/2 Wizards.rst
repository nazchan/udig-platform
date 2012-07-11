2 Wizards
=========

Contributing to the ImportWizard
--------------------------------

Add a ImportWizard extension in the plugin.xml of your plugin.

::

    <extension
             point="org.eclipse.ui.importWizards">
          <wizard
                class="net.refractions.udig.catalog.internal.ui.CatalogImportWizard"
                icon="icons/obj16/repository_obj.gif"
                id="net.refractions.udig.catalog.ui.dataImportWizard"
                name="%dataImportWizard.name">
             <description>Import from a data source.</description>
          </wizard>
       </extension>

Contributing to the dataWizard
------------------------------

Add a dataWizard extension in the plugin.xml of your plugin.

::

    <extension
           point="net.refractions.udig.catalog.ui.dataWizards">
        <wizard
              banner="icons/wizban/postgis_wiz.gif"
              class="net.refractions.udig.catalog.internal.postgis.ui.MifWizardPage"
              description="%wizard.description"
              icon="icons/etool16/postgis_wiz.gif"
              id="net.refractions.udig.catalog.ui.mif"
              name="%wizard.name">
        </wizard>
     </extension>

**Links**

-  `Creating JFace
   Wizards <http://www.eclipse.org/articles/Article-JFace%20Wizards/wizardArticle.html>`_
-  `Eclipse Platform Plug-in Developer Guide: Standard Widget Toolkit
   (SWT) <http://dev.eclipse.org/help20/content/help:/org.eclipse.platform.doc.isv/guide/swt.htm>`_
-  `Eclipse Platform Plug-in Developer Guide: JFace UI
   Framework <http://dev.eclipse.org/help20/content/help:/org.eclipse.platform.doc.isv/guide/jface.htm>`_
-  `Article: Understanding Layouts in SWT (Revised for
   2.0) <http://www.eclipse.org/articles/Understanding%20Layouts/Understanding%20Layouts.htm>`_

