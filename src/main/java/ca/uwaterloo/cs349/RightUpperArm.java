package ca.uwaterloo.cs349;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class RightUpperArm extends RotatablePart{
    final int width = 66;
    final int height = 168;
    final int pivotX = 16;
    final int pivotY = 10;
    final double defaultRotation = -32;
    static Affine defaultMatrix = new Affine();
    public RightUpperArm() {
        super(new Image("Images/RightUpperArm.png"), RightUpperArm.defaultMatrix, 360);
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
        initMatrix.prependTranslation(63, 15);
        super.initPos();
    }
}
