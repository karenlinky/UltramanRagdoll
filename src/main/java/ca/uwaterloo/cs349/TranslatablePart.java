package ca.uwaterloo.cs349;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public abstract class TranslatablePart extends Part {
    double dx = 0;
    double dy = 0;

    public TranslatablePart(Image img, Affine defaultMatrix) {
        super(img, defaultMatrix);
    }

    public Affine getLocalMatrix() {
        Affine localMatrix = new Affine();
        localMatrix.prependTranslation(this.dx, this.dy);
        return localMatrix;
    }

    public Affine getFullMatrix() {
        Affine fullMatrix = new Affine();
        fullMatrix.append(getLocalMatrix());
        if (parent != null) {
            fullMatrix.append(parent.getFullMatrixWithoutDefault());
        }
        fullMatrix.append(getInitMatrix());
        fullMatrix.append(getDefaultMatrix());
        return fullMatrix;
    }

    public Affine getFullMatrixWithoutDefault() {
        Affine fullMatrix = new Affine();
        fullMatrix.append(getLocalMatrix());
        if (parent != null) {
            fullMatrix.append(parent.getFullMatrixWithoutDefault());
        }
        fullMatrix.append(getInitMatrix());
        return fullMatrix;
    }

    @Override
    public void react(double previous_x, double previous_y, double current_x, double current_y) throws NonInvertibleTransformException {
        this.dx += current_x - previous_x;
        this.dy += current_y - previous_y;
    }

    @Override
    public void reset() {
        this.dx = this.dy = 0;
        super.reset();
    }

    @Override
    public String save() {
        String data = Double.toString(this.dx) + Ragdoll.saveSeparator + Double.toString(this.dy) + Ragdoll.saveSeparator;
        data += super.save();
        return data;
    }

    @Override
    public String load(String data) {
        String dx = "";
        String dy = "";
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
                    dx += data.charAt(i);
                } else {
                    dy += data.charAt(i);
                }
            }
        }

        this.dx = Double.parseDouble(dx);
        this.dy = Double.parseDouble(dy);
        data = data.substring(lastSeparatorIndex + 1);

        data = super.load(data);
        return data;
    }
}
