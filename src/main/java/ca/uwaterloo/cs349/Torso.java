package ca.uwaterloo.cs349;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class Torso extends TranslatablePart{
    final int width = 181;
    final int height = 301;
    final int pivotX = width/2;
    final int pivotY = 15;
    static Affine defaultMatrix = new Affine();
    public Torso() {
        super(new Image("Images/Torso.png"), Torso.defaultMatrix);
        defaultMatrix.prependTranslation(-1 * pivotX, -1 * pivotY);
        super.width = this.width;
        super.height = this.height;
    }

    @Override
    public void initPos() {
        this.initMatrix.prependTranslation(Ragdoll.screen_width/2, Ragdoll.screen_height/2 - this.height);
        super.initPos();
    }
}
