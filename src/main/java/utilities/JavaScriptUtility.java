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
		File f = new File("target/Node"+node.getId()+"Report.html");
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
   

        bw.write("<html>\r\n"
        		+ "    <head>\r\n"
        		+ "        <script src=\"https://www.desmos.com/api/v1.6/calculator.js?apiKey=dcb31709b452b1cf9dc26972add0fda6\"></script>\r\n"
        		+ "        <script src = \"../resources/script.js\" defer></script>\r\n"
        		+ "    </head>\r\n"
        		+ "    <body>\r\n"
        		+ "        <h1>Proposed reductions for datasets retained into node "+ node.getId() + "</h1>\r\n");
        
        for (Metadata dataset : node.getDatasets()) {
        	Integer day = (int) Duration.between(dataset.getLastUsage(),today).toDays();
        	bw.write("        <p > Proposed reductions for dataset <b>"+dataset.getId()+"</b></p>\r\n");
        	bw.write("        <div id=dataset" +dataset.getId()+ " g=" + dataset.getAgingDecayFactor().toString() +" k=" + dataset.getDisuseThreshold().toString() +" interestCoeff = "+new Value().getInterest() +" style='width: 80%; height: 400px;'> \r\n");
        	Integer i = 0;
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

        bw.close();
	}
}
