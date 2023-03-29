package ca.uwaterloo.cs349;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class Feet extends RotatablePart{
    public Feet(Image img, Affine defaultMatrix, double maxTheta) {
        super(img, defaultMatrix, maxTheta);
    }

    @Override
    public Affine getRotationMatrix() {
        Affine localMatrix = new Affine();
        localMatrix.prependRotation(
                this.theta + this.getDefaultRotation() + this.getParent().getTheta() + this.getParent().getDefaultRotation() + this.getParent().getParent().getTheta() + this.getParent().getParent().getDefaultRotation()
        );
        return localMatrix;
    }

    @Override
    public Affine getFullMatrix() {
        Affine fullMatrix = getFullMatrixWithoutDefault();
        fullMatrix.append(getDefaultMatrix());
        return fullMatrix;
    }

    @Override
    public Affine getFullMatrixWithoutDefault() {
        Part UpperLeg = this.getParent();
        Point2D targetPos = UpperLeg.getEndPoint();

        Affine fullMatrix = new Affine();

        fullMatrix.appendTranslation(targetPos.getX(), targetPos.getY());
        fullMatrix.append(getLocalMatrix());
        return fullMatrix;
    }
}
