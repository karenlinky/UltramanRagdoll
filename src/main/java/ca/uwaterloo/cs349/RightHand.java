
package ca.uwaterloo.cs349;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class RightHand extends RotatablePart{
    final int width = 45;
    final int height = 53;
    final int pivotX = width/2;
    final int pivotY = 0;
    final double defaultRotation = 0;
    static Affine defaultMatrix = new Affine();
    public RightHand() {
        super(new Image("Images/RightHand.png"), RightHand.defaultMatrix, 35);
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
        initMatrix.prependTranslation(-9, 109);
        super.initPos();
    }
}
