package ca.uwaterloo.cs349;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;
import java.util.Vector;
import javafx.scene.image.Image;

public abstract class Part
{
    protected Image img;
    protected Part parent = null;
    //    protected Affine matrix = new Affine();                    // needed for both draw inverse
    protected Affine defaultMatrix;                            // needed for draw; not needed for inverse
    protected Affine initMatrix = new Affine();                            // needed for draw; not needed for inverse

    protected Vector<Part> children = new Vector<Part>();

    protected int width = 0;
    protected int height = 0;

    public Part(Image img, Affine defaultMatrix) {
        this.img = img;
        this.defaultMatrix = defaultMatrix;
    }

    // maintain hierarchy
    public void addChild(Part p) {
        children.add(p);
        p.setParent(this);
    }

    public Part getParent() {
        return parent;
    }
    private void setParent(Part s) {
        this.parent = s;
    }

    Affine getDefaultMatrix() {return defaultMatrix; }
    Affine getInitMatrix() { return initMatrix; }

    public abstract Affine getLocalMatrix();
    public abstract Affine getFullMatrix();
    public abstract Affine getFullMatrixWithoutDefault();


    // hit tests
    // these cannot be handled in the base class, since the actual hit tests are dependend on the type of shape
    // protected abstract boolean contains(Point2D p);
    protected boolean contains(double x, double y) {
        return contains(new Point2D(x, y));
    }

    // we can walk the tree from the base class, since we rely on the specific sprites to check containment
    protected Part getSpriteHit(double x, double y) {
        // if no match above, recurse through children and return the first hit
        // assumes no overlapping shapes
        for (Part sprite : children) {
            Part hit = sprite.getSpriteHit(x, y);
            if (hit != null) return hit;
        }

        // check me next...
        if (this.contains(x, y)) {
            return this;
        }

        return null;
    }

    public void draw(GraphicsContext gc) {
        // save the current graphics context so that we can restore later
        Affine oldMatrix = gc.getTransform();

        // make sure we have the correct transformations for this shape
        gc.setTransform(getFullMatrix());
        gc.drawImage(this.img, 0, 0);

        // draw children
        for (int i = children.size() - 1; i >= 0; i--) {
            children.get(i).draw(gc);
        }

        // set back to original value since we're done with this branch of the scene graph
        gc.setTransform(oldMatrix);
    }

//    // Draw on the supplied canvas
//    protected void draw(GraphicsContext gc) {
//        // draw children
//        for (Part child : children) {
//            child.draw(gc);
//        }
//    }

    public void initPos(){
        for (Part child : children) {
            child.initPos();
        }
    };



    // Check if the point is contained by this shape
    // This cannot be abstract, since it relies on knowledge of the
    // specific type of shape for the hit test.
    protected boolean contains(javafx.geometry.Point2D p) {
        try {
            // Use inverted matrix to move the mouse click so that it's
            // relative to the shape model at the origin.
            Point2D pointAtOrigin = getFullMatrix().createInverse().transform(p);

            // Perform the hit test relative to the shape model's
            // untranslated coordinates at the origin
            return new Rectangle(0, 0, width, height).contains(pointAtOrigin);

        } catch (NonInvertibleTransformException e) {
            e.printStackTrace();
        }
        return false;
    }

    public abstract void react(double previous_x, double previous_y, double current_x, double current_y) throws NonInvertibleTransformException;

    public double getSx() {
        return 0;
    }

    public double getSy() {
        return 0;
    }

    public Affine getRotationMatrix() {
        return null;
    }

    public Affine getScalingMatrix() {
        return null;
    }

    public double getTheta() {
        return 0;
    }

    public Point2D getEndPoint() {
        return null;
    }

    public double getDefaultRotation() {
        return 0;
    }

    public void reset() {
        for (Part child : children) {
            child.reset();
        }
    }

    public String save() {
        String data = "";
        for (Part child : children) {
            data += child.save();
        }
        return data;
    }

    public String load(String data) {
        for (Part child : children) {
            data = child.load(data);
        }
        return data;
    }
}
