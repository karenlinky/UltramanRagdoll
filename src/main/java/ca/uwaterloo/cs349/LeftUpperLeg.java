package ca.uwaterloo.cs349;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class LeftUpperLeg extends ScalablePart{
    final int width = 92;
    final int height = 212;
    final int pivotX = width/2;
    final int pivotY = 0;
    final double defaultRotation = 13;
    static Affine defaultMatrix = new Affine();
    public LeftUpperLeg() {
        super(new Image("Images/LeftUpperLeg.png"), LeftUpperLeg.defaultMatrix, 90);
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
        initMatrix.prependTranslation(-38, 255);
        super.initPos();
    }

    @Override
    public Point2D getEndPoint() {
        Point2D EndPoint = new Point2D(this.width/2 + 11, this.height - 8);
        Affine transformation = this.getFullMatrix();
        return transformation.transform(EndPoint);
    }
}
