# CSSE374-Final-Project

## Design

### Milestone 1
This tool has three main components in it:

1. Data API and storage - There are classes that store the information about the attributes of the classes, interfaces, methods, and fields.
2. ASM parser - The combination of a decorator pattern and a visitor pattern were used to retrieve the information from the compiled bytecode.
3. Output - A visitor pattern was used to output the class information in a format that the GraphViz tool is able to read. There are different output visitors responsible for each portion of the output. The classes in that store the data are responsible for controlling the proper traversal of the information. One visitor outputs the class information, another outputs all the extends relationships, and another outputs all the implements relationships. These visitors are set at runtime and new ones that do different things should be able to be added very easily.

### Milestone 2
Overall, no modifications were required to be made to the design to get Milestone 2 operational. The largest improvement to the design was the addition of an OutputStrategy to the DataManager that is responsible for outputting the start and the end of the digraph. This is important because the previous design was reliant upon a particular OutputVisitor being added to the DataManager first. This change removes that requirement. This design change was made in an effort to prevent future issues.

The changes to the code to get Milestone 2 added involved the addition of a MethodVisitor (not our ClassMethodVisitor) that could go through the body of methods to get uses relationships, extensions to the data API, additional parsing in the ClassFieldVisitor to allow proper handling of collections, and additional OutputVisitors for the new arrows. These were simply changes to the code 

## Usage Instructions
This tool is designed for use on Windows and to be run from Eclipse. It is possible to be run outside of Eclipse, but instructions to do so will not be provided here.

1. Download GraphViz from <http://www.graphviz.org/Download.php>
  * Make sure to download the .zip version
2. Extract the zipped GraphViz folder
  * There should be an executable named dot inside of this folder
3. Add the path of this folder to your PATH system variable
4. Import the project in this repository to Eclipse
5. Change your run configuration for CommandRunner.java to have the classpaths of the classes you want to see in your UML diagram as arguments separated by spaces
6. Run the CommandRunner class with this configuration
7. There should now be a .png file in the folder that your project is in with the automatically generated UML diagram

## Work Distribution
Milestone 1:
* README: Matthew Persing
* UML Diagram: Tyler Whitehouse
* Design: Both
* Code: Both
* UML comparison testing: Tyler Whitehouse
* Automated tests: Matthew Persing
Milestone 2:
* README: Matthew Persing
* Updated UML Diagram: Tyler Whitehouse
* Design Improvements: Both
* Code: Both
* UML comparison testing: Tyler Whitehouse
* Automated tests: Matthew Persing

