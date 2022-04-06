package main;


import java.io.IOException;
import java.time.Duration;
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
		
		//Set data waste
		setDataWaste(network, today);
		
		//for each node of the network create a report for the discovered waste
		for (Node node : network.getNodes()) {
			javaScriptUtility.createReport(node, today);
		}
	}
	
	
	/**
	 * @param network is the system architecture
	 * @param today is the day of waste discovery
	 */
	static void setDataWaste(Network network, ZonedDateTime today) {
		//for each node of the network
		for (Node node : network.getNodes()) {
			//for each data set of the considered node
			node.getDatasets().forEach((d) -> {
				//Control if it is disused
				if((int)Duration.between(d.getLastUsage(),today).toDays()> d.getDisuseThreshold()) {
					d.setDisused(true);
				//Control if it is aged
				}if((int)Duration.between(d.getInsertionDate(),today).toDays()> d.getRetentionLimit()) {
					d.setAged(true);
				}
			});
		}
	}
}
