package ca.uwaterloo.cs349;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.geometry.Point2D;
import java.lang.Math;
import java.util.Vector;

public abstract class ScalablePart extends RotatablePart {
    double sx = 1;
    double sy = 1;
    final static double minScale = 0.5;
    final static double maxScale = 2;

    public ScalablePart(Image img, Affine defaultMatrix, double maxTheta) {
        super(img, defaultMatrix, maxTheta);
    }

    public Affine getLocalMatrix() {
        Affine localMatrix = this.getScalingMatrix();
        localMatrix.prepend(this.getRotationMatrix());      // scaling first; then rotation
        return localMatrix;
    }

    public Affine getFullMatrix() {
        Affine fullMatrix = new Affine();
        if (parent != null) {
            fullMatrix.append(parent.getFullMatrixWithoutDefault());
        }
        fullMatrix.append(getInitMatrix());
        fullMatrix.append(getLocalMatrix());
        fullMatrix.append(getDefaultMatrix());
        return fullMatrix;
    }

    public Affine getFullMatrixWithoutDefault() {
        Affine fullMatrix = new Affine();
        if (parent != null) {
            fullMatrix.append(parent.getFullMatrixWithoutDefault());
        }
        fullMatrix.append(getInitMatrix());
        fullMatrix.append(getLocalMatrix());
        return fullMatrix;
    }

    @Override
    public void react(double previous_x, double previous_y, double current_x, double current_y) throws NonInvertibleTransformException {
        super.react(previous_x, previous_y, current_x, current_y);
        Affine fullMatrix = getFullMatrixWithoutDefault();
        Affine inverse = fullMatrix.createInverse();


        Point2D origin = new Point2D(0, 0);
        Point2D prev = inverse.transform(new Point2D(previous_x, previous_y));
        Point2D curr = inverse.transform(new Point2D(current_x, current_y));

//        sx *= (curr.getX() - origin.getX()) / (prev.getX() - origin.getX());
        double factorY = (curr.getY() - origin.getY()) / (prev.getY() - origin.getY());
        sy *= factorY;
        sy = Math.max(sy, ScalablePart.minScale);
        sy = Math.min(sy, ScalablePart.maxScale);
    }

    public double getSx() {
        return this.sx;
    }

    public double getSy() {
        return this.sy;
    }

    public Affine getScalingMatrix() {
        Affine localMatrix = new Affine();
        localMatrix.prependScale(this.sx, this.sy);
        return localMatrix;
    }

    @Override
    public void reset() {
        this.sx = this.sy = 1;
        super.reset();
    }

    @Override
    public String save() {
        String data = Double.toString(this.sx) + Ragdoll.saveSeparator + Double.toString(this.sy) + Ragdoll.saveSeparator;
        data += super.save();
        return data;
    }

    @Override
    public String load(String data) {
        String sx = "";
        String sy = "";
        int separatorFound = 0;
        int lastSeparatorIndex = 0;
        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == Ragdoll.saveSeparator) {
                separatorFound++;
                if (separatorFound == 2) {
                    lastSeparatorIndex = i;
                    break;
                }
            } else {
                if (separatorFound == 0) {
                    sx += data.charAt(i);
                } else {
                    sy += data.charAt(i);
                }
            }
        }

        this.sx = Double.parseDouble(sx);
        this.sy = Double.parseDouble(sy);
        data = data.substring(lastSeparatorIndex + 1);

        data = super.load(data);
        return data;
    }
}
