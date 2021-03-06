/* Copyright 2010,2014 Bank Of Italy
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 *
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */
package it.bancaditalia.oss.sdmx.helper;

import java.awt.Component;
import java.util.logging.Logger;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import it.bancaditalia.oss.sdmx.util.Configuration;

public class FlowSelectionListener implements ListSelectionListener{
	private Component parent = null;
	protected static Logger logger = Configuration.getSdmxLogger();

	public FlowSelectionListener(Component parent) {
		super();
		this.parent = parent;
	}


	public void valueChanged(ListSelectionEvent e) {
		
		if(!e.getValueIsAdjusting()){
			JTable flowsTable = (JTable)QueryPanel.flowsPane.getViewport().getComponent(0);
			
			int rowSelected =flowsTable.getSelectedRow();
			QueryPanel.clearDimensions();
			QueryPanel.clearCodes();
			QueryPanel.sdmxQuery.setText("");
			//if this is not a provider switch
			if(rowSelected != -1){
				rowSelected =flowsTable.convertRowIndexToModel(rowSelected);
				final String dataflow = flowsTable.getModel().getValueAt(rowSelected, 0).toString();
				QueryPanel.selectedDataflow = dataflow;
		    	final ProgressViewer progress = new ProgressViewer(parent);
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
			        public void run() {
			        	GetStructureTask task = new GetStructureTask(progress, parent);
			        	task.execute();
			        }
			    });  
		    	progress.setVisible(true);
		    	progress.setAlwaysOnTop(true);
			}
		}
	}
}
