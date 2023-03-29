
package ca.uwaterloo.cs349;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class LeftHand extends RotatablePart{
    final int width = 47;
    final int height = 63;
    final int pivotX = width/2;
    final int pivotY = 0;
    final double defaultRotation = -3;
    static Affine defaultMatrix = new Affine();
    public LeftHand() {
        super(new Image("Images/LeftHand.png"), LeftHand.defaultMatrix, 35);
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
        initMatrix.prependTranslation(-2, 110);
        super.initPos();
    }
}
