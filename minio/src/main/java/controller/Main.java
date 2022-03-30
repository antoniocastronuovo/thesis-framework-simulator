package controller;


import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import io.minio.errors.MinioException;

import model.Network;

import utilities.JavaScriptUtility;
import utilities.JsonImporter;


public class Main {
	private static final String NODES_JSON_PATH = "resources/Nodes.json";
	private static final String CATALOG_JSON_PATH = "resources/Catalog.json";
	
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeyException, MinioException {
		
		Network network = new Network();
		
		JsonImporter jSonImporter = new JsonImporter();
		
		network.setNodes(jSonImporter.parseNetwork(NODES_JSON_PATH));
		jSonImporter.parseDataCatalog(CATALOG_JSON_PATH,network);
		
		JavaScriptUtility javaScriptUtility = new JavaScriptUtility();
		
		ZonedDateTime today = ZonedDateTime.of(2022, 03, 20, 0, 0, 1, 0, ZoneId.systemDefault());
		

		javaScriptUtility.createReport(network.getNodeById("1"), today);
		javaScriptUtility.createReport(network.getNodeById("2"), today);
		javaScriptUtility.createReport(network.getNodeById("3"), today);
	}
}
