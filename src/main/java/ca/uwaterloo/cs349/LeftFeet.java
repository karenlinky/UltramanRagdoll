package ca.uwaterloo.cs349;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class LeftFeet extends Feet {
    final int width = 96;
    final int height = 93;
    final int pivotX = 75;
    final int pivotY = 3;
    static Affine defaultMatrix = new Affine();
    final double defaultRotation = -9;
    public LeftFeet() {
        super(new Image("Images/LeftFeet.png"), LeftFeet.defaultMatrix, 35);
        defaultMatrix.prependTranslation(-1 * pivotX, -1 * pivotY);
        initMatrix.prependRotation(defaultRotation);
        super.width = this.width;
        super.height = this.height;
    }

    public double getDefaultRotation() {
        return defaultRotation;
    }

    @Override
    public void initPos() {
        initMatrix.prependTranslation(7, 197);
        super.initPos();
    }
}
