package ca.uwaterloo.cs349;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;

public class LowerLeg extends ScalablePart {
    LowerLeg(Image img, Affine defaultMatrix, double maxTheta) {
        super(img, defaultMatrix, maxTheta);
    }

    @Override
    public Affine getScalingMatrix() {
        Affine localMatrix = new Affine();
        localMatrix.prependScale(this.sx * this.getParent().getSx(), this.sy * this.getParent().getSy());
        return localMatrix;
    }

    @Override
    public Affine getRotationMatrix() {
        Affine localMatrix = new Affine();
        localMatrix.prependRotation(this.theta + this.getParent().getTheta() + this.getParent().getDefaultRotation());
        return localMatrix;
    }

    @Override
    public Affine getLocalMatrix() {
        Affine localMatrix = this.getScalingMatrix();
        localMatrix.prepend(this.getRotationMatrix());      // scaling first; then rotation
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
