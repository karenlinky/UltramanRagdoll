package ca.uwaterloo.cs349;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class LeftUpperArm extends RotatablePart{
    final int width = 63;
    final int height = 161;
    final int pivotX = 50;
    final int pivotY = 10;
    final double defaultRotation = 27;
    static Affine defaultMatrix = new Affine();
    public LeftUpperArm() {
        super(new Image("Images/LeftUpperArm.png"), LeftUpperArm.defaultMatrix, 360);
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
        initMatrix.prependTranslation(-65, 15);
        super.initPos();
    }
}
