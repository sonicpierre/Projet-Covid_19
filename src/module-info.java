module vue.main.Main {
	requires transitive javafx.graphics;
	requires transitive java.desktop;
	requires jxmapviewer2;
	requires transitive javafx.swing;
	requires transitive javafx.controls;
	requires jdk.compiler;
	requires java.sql;
	requires transitive javafx.base;
	requires transitive javafx.fxml;
	requires javafx.web;
	
	opens controll.main to javafx.fxml;
	exports controll.main;

}
