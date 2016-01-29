# CSSE374-Final-Project

## Design

### Milestone 1
This tool has three main components in it:

1. Data API and storage - There are classes that store the information about the attributes of the classes, interfaces, methods, and fields.
2. ASM parser - The combination of a decorator pattern and a visitor pattern were used to retrieve the information from the compiled bytecode.
3. Output - A visitor pattern was used to output the class information in a format that the GraphViz tool is able to read. There are different output visitors responsible for each portion of the output. The classes in that store the data are responsible for controlling the proper traversal of the information. One visitor outputs the class information, another outputs all the extends relationships, and another outputs all the implements relationships. These visitors are set at runtime and new ones that do different things should be able to be added very easily.

### Milestone 2
Overall, no modifications were required to be made to the design to get Milestone 2 operational. The largest improvement to the design was the addition of an OutputStrategy to the DataManager that is responsible for outputting the start and the end of the digraph. This is important because the previous design was reliant upon a particular OutputVisitor being added to the DataManager first. This change removes that requirement. This design change was made in an effort to prevent future issues.

The changes to the code to get Milestone 2 added involved the addition of a MethodVisitor (not our ClassMethodVisitor) that could go through the body of methods to get uses relationships, extensions to the data API, additional parsing in the ClassFieldVisitor to allow proper handling of collections, and additional OutputVisitors for the new arrows. These were simply changes to the code.

### Milestone 3
Due to the major difference of Milestone 3 from previous milestones, we needed to change our design around a bit, but the majority of the design remained very similar. 

The following are the modifications made to existing code. The data API was modified in several places apart from the DataManager. The Method class has a new List field added that stored MethodCall information. Additionally, IMethods and IClasses are now stored in HashMaps in the Data API and are associated with their names to allow lookups for sequence diagrams. The OutputVisitors that were used on the visitor pattern for UML output were moved to the UMLOutputStrategy.

The following are new additions for the sequence diagram. The existing ASM parser simply had a new method added that added each method call in order into the data API. These method calls were stored in a MethodCall class that was added to the data API. AddStrategies were added to the DataManager that would take care of adding new entries properly as they were received as arguments. The UMLAddStrategy simply called addClass, but the SDAddStrategy recursively went through all method calls by each method starting at the root method until the depth was reached, adding whatever classes it needed along the way. A similar recursive pattern was used to output the result. To prevent code duplication, the existing CommandLineRunner was turned into a SDCommandLineRunner and a UMLCommandLinerunner. Each properly setup the add and output strategies along with properly calling the appropriate programs to process the result.

### Milestone 4
Milestone 4 fit very well into our existing design. For pattern detection, we added a PatternFinder Interface that functions as a strategy pattern. PatternFinders are added to the data manager and when findAllPatterns is called in the data manager, all the added PatternFinders are looped through to detect all design patterns that should be found.

To output design pattern information, we created the UMlModifierManager class that can be passed around to the OutputVisitors and OutputStrategy for UML without passing around with entire data manager. This class stores subtext for classes, style information for classes, and cluster information. If subtext or style information is recorded, they'll be placed in the proper node location. If cluster information is stored, it will be handled in the UML output strategy.

Beyond specific changes for Milestone 4, we added the features that would have been nice since Milestone 2: improved signature parsing for Fields and whitelisting classes for UML display

As a proof of concept for clusters, we added a pattern finder that finds classes in the same package and groups them together, labeling the group.

### Milestone 5
Milestone 5 fit perfectly into the existing design and no design modifications needed to be made. The modifications that were made were simply adding new pattern finders for Decorator and Adapter patterns and aggregating them in the data manager from the UMLCommandLineRunner.

## Usage Instructions
This tool is designed for use on Windows and to be run from Eclipse. It is possible to be run outside of Eclipse, but instructions to do so will not be provided here.

1. Download GraphViz from <http://www.graphviz.org/Download.php>
  * Make sure to download the .zip version
2. Extract the zipped GraphViz folder
  * There should be an executable named dot inside of this folder
3. Add the path of this folder to your PATH system variable
4. Import the project in this repository to Eclipse
5. Change your run configuration for UMLCommandRunner.java to have the classpaths of the classes you want to see in your UML diagram as arguments separated by spaces
6. Run the CommandRunner class with this configuration
7. There should now be a .png file in the folder that your project is in with the automatically generated UML diagram
8. Download SDEdit from <http://iweb.dl.sourceforge.net/project/sdedit/sdedit/4.0/sdedit-4.01.jar>
9. Place the downloaded .jar in the same folder as this README.md file
10. Change your run configuration for SDCommandRunner.java to have the classpath of the root class for your sequence diagram as the first argument, the method signature as the second argument, and the depth as the third (can be omitted - defaults to 5)
  * An example of arguments would be: runners.SDCommandLineRunner main(java.lang.String[]) 5
11. If the Sequence Diagram is not gigantic, it should be in milestone3AutomaticSD.png in this directory. If it was too large, you'll have to open the SDEdit jar yourself and copy the text from milestone3AutomaticSD.sd into the text box.

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

Milestone 3:
* README: Matthew Persing
* Updated UML Diagram: Tyler Whitehouse
* Design Improvements: Both
* Code: Both
* SD comparison testing: Tyler Whitehouse
* Automated tests: Matthew Persing

Milestone 4:
* README: Matthew Persing
* Updated UML Diagram: Tyler Whitehouse
* Design Improvements: Both
* Code: Both
* Comparison testing: Tyler Whitehouse
* Automated tests: Matthew Persing

Milestone 5:
* README: Matthew Persing
* Updated UML Diagram: Tyler Whitehouse
* Decorator Pattern Finder: Matthew Persing
* Adapter Pattern Finder: Tyler Whitehouse

