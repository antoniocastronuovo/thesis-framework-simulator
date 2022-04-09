# Data Value Driven Framework Simulator
This repository contains the documents, results and resources of the validation phase performed in the thesis "A Data Value Driven Framework to Reduce 
the Data Storage Energy Consumption" by Antonio Castronuovo, Advisor : Pierluigi Plebani, Co-Advisor : Mattia Salnitri.
In particular, the repository contains a java application that simulates the application of the proposed framework and the data value model proposed in the thesis. This java application has been developed using Eclipse and the following repository can be cloned to start a fully-working Eclipse project.
The Eclipse project has been developed using Maven to manage the dependencies like GSon and to facilitate the deployment through a jar file that contains all the dependencies, all this specificities can be found in the [pom.xml](https://github.com/antoniocastronuovo/thesis-framework-simulator/blob/master/pom.xml) configuration file.

The java application constructs, through the usage of a [script](https://github.com/antoniocastronuovo/thesis-framework-simulator/blob/master/resources/script.js), a set of reports including the data set value simulation for each applicable Data Reduction Strategy.

## Included Example of Scenario 
The proposed java application has been utilized to simulate a the usage of the framework in a distributed scenario composed by three nodes and drive the redcution of the contained data waste.
The proposed scenario has been constructed using mainly two source JSON files located in the [resource](https://github.com/antoniocastronuovo/thesis-framework-simulator/tree/master/resources) folder:
- [Nodes.json](https://github.com/antoniocastronuovo/thesis-framework-simulator/blob/master/resources/Nodes.json) contains the JSON description of the distributed system architechture.
- [Catalog.json](https://github.com/antoniocastronuovo/thesis-framework-simulator/blob/master/resources/Catalog.json) contains the metadata of the data sets inserted in the various nodes.

After the analysis of nodes and the contained data sets, three HTML reports has been created (one for each node) in [target](https://github.com/antoniocastronuovo/thesis-framework-simulator/blob/master/target) folder:
- [Node1Report.html](https://github.com/antoniocastronuovo/thesis-framework-simulator/blob/master/target/Node1Report.html) contains the value simulation for all the data sets contained in Node 1.
- [Node2Report.html](https://github.com/antoniocastronuovo/thesis-framework-simulator/blob/master/target/Node2Report.html) contains the value simulation for all the data sets contained in Node 2.
- [Node3Report.html](https://github.com/antoniocastronuovo/thesis-framework-simulator/blob/master/target/Node3Report.html) contains the value simulation for all the data sets contained in Node 3.

## Usage of the Application for Custom Scenarios
The proposed application can be used for customs scenarios descripted by the user through the usage of JSON resource files that have the same structure of the ones proposed in the included example [Nodes.json](https://github.com/antoniocastronuovo/thesis-framework-simulator/blob/master/resources/Nodes.json), [Catalog.json](https://github.com/antoniocastronuovo/thesis-framework-simulator/blob/master/resources/Catalog.json).

To use the application it is sufficient that the provided [jar](https://github.com/antoniocastronuovo/thesis-framework-simulator/blob/master/target/framework-1.0-with-dependencies.jar) is positioned in the same folder where there are other two directories:
- `resources`: containing the two JSON files.
- `target` : an empty folder that will contain the created reports.

To execute the jar file on a system that have a Java JDK installed, it is sufficient to double click on it or execute it by writing in your terminal:
`java -jar <nameOfJar>`