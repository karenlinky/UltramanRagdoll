package ca.uwaterloo.cs349;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.geometry.Point2D;
import java.lang.Math;
import java.util.Vector;

public abstract class RotatablePart extends Part {
    double theta = 0;
    double maxTheta = 50;


    public RotatablePart(Image img, Affine defaultMatrix, double maxTheta) {
        super(img, defaultMatrix);
        this.maxTheta = maxTheta;
    }

    public Affine getLocalMatrix() {
        return this.getRotationMatrix();
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
        Affine fullMatrix = getFullMatrixWithoutDefault();
        Affine inverse = fullMatrix.createInverse();


        Point2D origin = new Point2D(0, 0);
        Point2D prev = inverse.transform(new Point2D(previous_x, previous_y));
        Point2D curr = inverse.transform(new Point2D(current_x, current_y));

        double theta = origin.angle(prev, curr);
        int factor = (origin.getY() - prev.getY()) * (curr.getX() - origin.getX()) -
                (curr.getY() - origin.getY()) * (origin.getX() - prev.getX()) < 0 ? -1 : 1;
        theta *= factor;

        double temp = this.theta + theta;
        if (-1 * maxTheta <= temp && temp <= maxTheta) {
            this.theta = temp;
        }
    }

    public Affine getRotationMatrix() {
        Affine localMatrix = new Affine();
        localMatrix.prependRotation(this.theta);
        return localMatrix;
    }

    public double getTheta() {
        return this.theta;
    }

    @Override
    public void reset() {
        this.theta = 0;
        super.reset();
    }

    @Override
    public String save() {
        String data = Double.toString(this.theta) + Ragdoll.saveSeparator;
        data += super.save();
        return data;
    }

    @Override
    public String load(String data) {
        String theta = "";
        int lastSeparatorIndex = 0;
        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == Ragdoll.saveSeparator) {
                lastSeparatorIndex = i;
                break;
            } else {
                theta += data.charAt(i);
            }
        }

        this.theta = Double.parseDouble(theta);
        data = data.substring(lastSeparatorIndex + 1);

        data = super.load(data);
        return data;
    }
}
