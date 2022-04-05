package utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;


import model.Metadata;
import model.Node;
import model.Value;
import reductionstrategies.Reduction;


/**
 * @author Antonio Castronuovo
 *
 */
public class JavaScriptUtility {

	/**
	 * Auto-generated stub constructor
	 */
	public JavaScriptUtility() {
		super();
	}	

	/**
	 * @param node is the considered node to construct the report
	 * @param today is the reference day to which the report is constructed
	 * @throws IOException
	 */
	public void createReport(Node node, ZonedDateTime today) throws IOException{
		
		//Create a new HTML file having the node name
		File f = new File("target/Node"+node.getId()+"Report.html");
		
		//Create a new writer buffer from the created file
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
   
        //Write the file head and the first part of the body 
        bw.write("<html>\r\n"
        		+ "    <head>\r\n"
        		+ "        <script src=\"https://www.desmos.com/api/v1.6/calculator.js?apiKey=dcb31709b452b1cf9dc26972add0fda6\"></script>\r\n"
        		+ "        <script src = \"../resources/script.js\" defer></script>\r\n"
        		+ "    </head>\r\n"
        		+ "    <body>\r\n"
        		+ "        <h1>Proposed reductions for datasets retained into node "+ node.getId() + "</h1>\r\n");
        
        // for each data set in the considered node 
        for (Metadata dataset : node.getDatasets()) {
        	//Calculate time duration from last usage to today 
        	Integer day = (int) Duration.between(dataset.getLastUsage(),today).toDays();
        	//insert a <p> element containing the name of the data set 
        	bw.write("        <p > Proposed reductions for dataset <b>"+dataset.getId()+"</b></p>\r\n");
        	//Create a <div> to insert standard data set value trend filling it with fundamental quantities to construct the graph (k,g, δ interest coefficient) 
        	bw.write("        <div id=dataset" +dataset.getId()+ " g=" + dataset.getAgingDecayFactor().toString() +" k=" + dataset.getDisuseThreshold().toString() +" interestCoeff = "+new Value().getInterest() +" style='width: 80%; height: 400px;'> \r\n");
        	Integer i = 0;
        	//for each data set's admissed reduction create a new internal <div> describing the resulting trend
	        for (Reduction reduction : dataset.getAdmissedReductions().keySet()) {
	        	
	        	bw.write("        	<div id='calculator"+i+"' " +
	        						" red="+reduction.getName().replace(" ", "")+" v="+ dataset.getAdmissedReductions().get(reduction).getTotalValue().toString() +" d="+day+" interestCoeff = "+new Value().getInterest() +" ></div>\r\n");	        	
	        	i++;
			}
	        bw.write("        </div>\r\n");
	        bw.write("        <p></p>\r\n");
        }	
        bw.write("    </body>\r\n"
        		+ "</html>");
        //close the writer
        bw.close();
	}
}
