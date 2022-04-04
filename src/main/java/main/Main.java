package main;


import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import model.Network;
import model.Node;
import utilities.JavaScriptUtility;
import utilities.JsonImporter;


public class Main {
	private static final String NODES_JSON_PATH = "resources/Nodes.json";
	private static final String CATALOG_JSON_PATH = "resources/Catalog.json";
	
	public static void main(String[] args) throws IOException{
		
		// Initialize a new system network
		Network network = new Network();
		// Initialize a JSON importer to set-up nodes and data sets
		JsonImporter jSonImporter = new JsonImporter();
		
		// Import nodes information from a JSON file
		network.setNodes(jSonImporter.parseNetwork(NODES_JSON_PATH));
		
		// Import data sets information from data catalog
		jSonImporter.parseDataCatalog(CATALOG_JSON_PATH,network);
		
		//Initialize the JavaScript utility used to build the HTML data sets report 
		JavaScriptUtility javaScriptUtility = new JavaScriptUtility();
		
		//Set-up day of the simulation
		ZonedDateTime today = ZonedDateTime.of(2022, 03, 20, 0, 0, 1, 0, ZoneId.systemDefault());
		
		//for each node of the network create a report
		for (Node node : network.getNodes()) {
			javaScriptUtility.createReport(node, today);
		}
	}
}
