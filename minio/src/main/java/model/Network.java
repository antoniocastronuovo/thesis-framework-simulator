package model;

import java.util.ArrayList;
import java.util.List;

public class Network {

	private List<Node> nodes;

	public Network() {
		this.nodes = new ArrayList<>();
	}
	
	public Network(int numOfNodes) {
		this.nodes = new ArrayList<>();
		for(Integer i = 1 ; i<= numOfNodes; i++)
			this.nodes.add(new Node(i.toString()));
	}

	public List<Node> getNodes() {
		return nodes;
	}
	
	public Node getNodeById(String id) {
		for (Node node : this.nodes) {
			if (node.getId().equals(id)) {
				return node;
			}
		}
		return null;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	
	public boolean addNode(Node node) {
		if(!nodes.contains(node)) {
			nodes.add(node);
			return true;
		}
		return false;
	}
}
