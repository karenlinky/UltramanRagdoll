package ca.uwaterloo.cs349;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class LeftLowerLeg extends LowerLeg{
    final int width = 68;
    final int height = 197;
    final int pivotX = width/2;
    final int pivotY = 0;
    static Affine defaultMatrix = new Affine();
    public LeftLowerLeg() {
        super(new Image("Images/LeftLowerLeg.png"), LeftLowerLeg.defaultMatrix, 90);
        defaultMatrix.prependTranslation(-1 * pivotX, -1 * pivotY);
        super.width = this.width;
        super.height = this.height;
    }

    @Override
    public void initPos() {
        super.initPos();
    }

    @Override
    public Point2D getEndPoint() {
        Point2D EndPoint = new Point2D(this.width/2 + 5, this.height - 10);
        Affine transformation = this.getFullMatrix();
        return transformation.transform(EndPoint);
    }
}
