# Distribued Chat System 
DESPORTES Kilian

IMEKRAZ Yanis

Projet 4IR - 2019/2020 - OOC & OOP section 

---

Conceptions Diagrams are in 'conception_files' repository.

This repository is separated in 3 parts :
- UC_Diagram -> User case diagram
- Sequence_Diagrams -> Sequence diagrams for each UC, textual additional information in "textual_precision_UC.pdf" file
- Class_Diagram- > Class diagram

---

'java_project' is the whole Eclipse java project.

Sources are in 'java_project/src' and are distributed according to the use of the classes, in one of the following folders :
- controller : For controller classes (main, network).
- model : For classes related to data and data storage.
- sockets : For network related classes.
- view : For swing classes, which will be used in the GUI.

An ant build file is present in /src repository.
Ant build file usage :
- `ant build` to compile and the project and generate executables. Main will be generated in /src folder (same as build.xml location)

- `ant clean` to delete all .classes files and executables.

--- 

A complete javadoc of the project is available at 'java_project/doc'. 
Open 'index.html' file to see it.
