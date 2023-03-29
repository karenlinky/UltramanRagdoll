package ca.uwaterloo.cs349;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class Head extends RotatablePart{
    final int width = 117;
    final int height = 169;
    final int pivotX = width/2;
    final int pivotY = 150;
    static Affine defaultMatrix = new Affine();
    public Head() {
        super(new Image("Images/Head.png"), Head.defaultMatrix, 50);
        defaultMatrix.prependTranslation(-1 * pivotX, -1 * pivotY);
        super.width = this.width;
        super.height = this.height;
    }

    @Override
    public void initPos() {
        initMatrix.prependTranslation(0, -25);
        super.initPos();
    }
}
