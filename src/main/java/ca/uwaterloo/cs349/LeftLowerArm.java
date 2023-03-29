
package ca.uwaterloo.cs349;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class LeftLowerArm extends RotatablePart{
    final int width = 55;
    final int height = 111;
    final int pivotX = width/2;
    final int pivotY = 0;
    static Affine defaultMatrix = new Affine();
    public LeftLowerArm() {
        super(new Image("Images/LeftLowerArm.png"), LeftLowerArm.defaultMatrix, 135);
        defaultMatrix.prependTranslation(-1 * pivotX, -1 * pivotY);
        super.width = this.width;
        super.height = this.height;
    }

    @Override
    public void initPos() {
        initMatrix.prependTranslation(-14, 142);
        super.initPos();
    }
}
