package ca.uwaterloo.cs349;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class RightUpperLeg extends ScalablePart{
    final int width = 89;
    final int height = 216;
    final int pivotX = width/2;
    final int pivotY = 0;
    final double defaultRotation = -13;
    static Affine defaultMatrix = new Affine();
    public RightUpperLeg() {
        super(new Image("Images/RightUpperLeg.png"), RightUpperLeg.defaultMatrix, 90);
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
        initMatrix.prependTranslation(39, 255);
        super.initPos();
    }

    @Override
    public Point2D getEndPoint() {
        Point2D EndPoint = new Point2D(this.width/2 - 6, this.height - 6);
        Affine transformation = this.getFullMatrix();
        return transformation.transform(EndPoint);
    }
}
