package ca.uwaterloo.cs349;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class RightFeet extends Feet {
    final int width = 79;
    final int height = 97;
    final int pivotX = 25;
    final int pivotY = 3;
    static Affine defaultMatrix = new Affine();
    final double defaultRotation = 3;
    public RightFeet() {
        super(new Image("Images/RightFeet.png"), RightFeet.defaultMatrix, 35);
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
        initMatrix.prependTranslation(12, 197);
        super.initPos();
    }
}
