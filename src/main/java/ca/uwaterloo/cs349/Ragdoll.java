package ca.uwaterloo.cs349;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.stage.Stage;
import javafx.scene.input.KeyCombination;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

public class Ragdoll extends Application {

    static final int screen_width = 1280;
    static final int screen_height = 960;
    double previous_x, previous_y;
    Part selectedPart;

    static Part root;
    static Canvas canvas;
    static final char saveSeparator = '|';
    static final String fileExtension = "ultraman";
    static final String fileExtensionDescription = "Ultraman File (*." + Ragdoll.fileExtension + ")";

    @Override
    public void start(Stage stage) {
        MenuBar menuBar = new MenuBar();

        Menu menuFile = new Menu("File");
        menuBar.getMenus().addAll(menuFile);

        // Reset
        MenuItem Reset = new MenuItem("Reset");
        Reset.setAccelerator(KeyCombination.keyCombination("Ctrl+R"));
        Reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Ragdoll.root.reset();
                Ragdoll.draw(Ragdoll.canvas, Ragdoll.root);
            }
        });

        // Save
        MenuItem Save = new MenuItem("Save");
        Save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        Save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String data = Ragdoll.root.save();
                File file;
                FileWriter fileWriter;
                String filePath = "";

                JFrame Frame = new JFrame();
                JFileChooser FileChooser = new JFileChooser();
                FileChooser.setDialogTitle("Save Ultraman");

                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        Ragdoll.fileExtensionDescription,
                        Ragdoll.fileExtension);
                FileChooser.setFileFilter(filter);

                int userSelection = FileChooser.showSaveDialog(Frame);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    file = FileChooser.getSelectedFile();
                    filePath = file.toString();
                    if(!filePath.endsWith("." + Ragdoll.fileExtension)) {
                        file = new File(filePath + "." + Ragdoll.fileExtension);
                    }
                    try {
                        fileWriter = new FileWriter(file);
                        fileWriter.write(data);
                        fileWriter.close();
                    } catch (IOException fileException) {
                        fileException.printStackTrace();
                    }
                }
            }
        });

        // Load
        MenuItem Load = new MenuItem("Load");
        Load.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
        Load.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String data = "";
                File file;
                BufferedReader br;

                JFrame Frame = new JFrame();
                JFileChooser FileChooser = new JFileChooser();
                FileChooser.setDialogTitle("Load Ultraman");

                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        Ragdoll.fileExtensionDescription,
                        Ragdoll.fileExtension);
                FileChooser.setFileFilter(filter);

                int userSelection = FileChooser.showOpenDialog(Frame);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    file = FileChooser.getSelectedFile();
                    try {
                        br = new BufferedReader(new FileReader(file));
                        data = br.readLine();

                        Ragdoll.root.load(data);
                        Ragdoll.draw(Ragdoll.canvas, Ragdoll.root);
                        br.close();
                    } catch (IOException fileException) {
                        fileException.printStackTrace();
                    }
                }
            }
        });

        SeparatorMenuItem Separator = new SeparatorMenuItem();

        // Quit
        MenuItem Quit = new MenuItem("Quit");
        Quit.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        Quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.exit(0);
            }
        });

        menuFile.getItems().addAll(Reset, Save, Load, Separator, Quit);

        // setup a canvas to use as a drawing surface
        Canvas canvas = new Canvas(screen_width, screen_height);
        Ragdoll.canvas = canvas;
        Scene scene = new Scene(new VBox(menuBar, canvas), screen_width, screen_height);

        // create hierarchy of sprites
        Part root = createUltraman();
        Ragdoll.root = root;

        // add listeners
        // click selects the shape under the cursor
        // we have sprites do it since they track their own locations
        canvas.setOnMousePressed(mouseEvent -> {
            Part hit = root.getSpriteHit(mouseEvent.getX(), mouseEvent.getY());
            if (hit != null) {
                selectedPart = hit;
                previous_x = mouseEvent.getX();
                previous_y = mouseEvent.getY();
//                System.out.println("Selected");
            }
        });

        // un-selects any selected shape
        canvas.setOnMouseReleased( mouseEvent -> {
            selectedPart = null;
//            System.out.println("Deselected");
        });

        // dragged translates the shape based on change in mouse position
        // since shapes are defined relative to one another, they will follow their parent
        canvas.setOnMouseDragged(mouseEvent -> {
            if (selectedPart != null) {
                double current_x = mouseEvent.getX();
                double current_y = mouseEvent.getY();
                try {
                    selectedPart.react(previous_x, previous_y, current_x, current_y);
                } catch (NonInvertibleTransformException e) {
                    e.printStackTrace();
                }

                // draw tree in new position
                draw(canvas, root);

                // save coordinates for next event
                previous_x = mouseEvent.getX();
                previous_y = mouseEvent.getY();
            }
        });

        // draw the sprites on the canvas
        draw(canvas, root);

        // show the scene including the canvas
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private static void draw(Canvas canvas, Part root) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        root.draw(gc);
    }

    Part createUltraman() {
        Part Torso = new Torso();
        Part Head = new Head();
        Part LUArm = new LeftUpperArm();
        Part LLArm = new LeftLowerArm();
        Part LHand = new LeftHand();
        Part LULeg = new LeftUpperLeg();
        Part LLLeg = new LeftLowerLeg();
        Part LFeet = new LeftFeet();

        Part RUArm = new RightUpperArm();
        Part RLArm = new RightLowerArm();
        Part RHand = new RightHand();
        Part RULeg = new RightUpperLeg();
        Part RLLeg = new RightLowerLeg();
        Part RFeet = new RightFeet();

        Torso.addChild(LUArm);
        Torso.addChild(RUArm);
        Torso.addChild(LULeg);
        Torso.addChild(RULeg);
        Torso.addChild(Head);

        LUArm.addChild(LLArm);
        RUArm.addChild(RLArm);

        LLArm.addChild(LHand);
        RLArm.addChild(RHand);

        LULeg.addChild(LLLeg);
        RULeg.addChild(RLLeg);

        LLLeg.addChild(LFeet);
        RLLeg.addChild(RFeet);

        Torso.initPos();
        return Torso;
    }
}
