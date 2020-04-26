# ateam-142

1. Course: cs400
2. Semester: Spring 2020
3. Project name: Milk Weights

    Milk Weights processes information about the production of milk products from various farms over time, and the program is able to display reports about targeted areas of the data that the user may select. 
    
4. Group: a142

    Team Members:
    John Li (zli769@wisc.edu), CS400-001, x-142
    Duncan Broadie (dbroadie@wisc.edu), CS400-1
    Shruti Sharma (sharma224@wisc.edu), CS400-1
    Samuel Bahr (sdbahr@wisc.edu), CS400-001, x-142
    Hao Kui Ma (hma99@wisc.edu), CS400-001
    
    
5. Other notes or comments to the grader:
    2020-04-24 Deb helped Shruti restructure project to match required structure.
    Run application.Main to start the statistic analysis of milk weights.
    (a) for jdk 11 running, user need JavaFX as library and set up VM path (for jdk 8, 9, or 10, JavaFX is embeded )
    (b) can load one or multiple csv files as source file (see sample format)
    (c) can choose generate annul report, monthly report, farm specific report or date range report
    (d) can choose show report in screen or save the generated statistics files to a location.

For the milk statistical analysis, go to folder with executable.jar file:

(you need to change the to path of your javafx lib)

java --module-path "your\path\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml -jar executable.jar
 

tip: running in eclipse, set up VM argument as: (change path to your javafx lib path)
--module-path "your\path\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml

6.  [place any comments or notes that will help the grader here]
