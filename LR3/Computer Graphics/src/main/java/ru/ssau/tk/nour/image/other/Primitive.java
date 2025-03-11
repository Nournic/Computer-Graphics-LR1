package ru.ssau.tk.nour.image.other;

public abstract class Primitive {
    private Pivot pivot;
    private Vector3[] localVertices;
    public Vector3[] globalVertices;
    public int[] indexes;

    public void move(Vector3 v){
        pivot.move(v);

        for (int i = 0; i < localVertices.length; i++)
            globalVertices[i].add(v);
    }

    public void rotate(double angle, Axis axis){
        pivot.rotate(angle, axis);

        for (int i = 0; i < localVertices.length; i++) {
            globalVertices[i] = pivot.toGlobalCoords(localVertices[i]);
        }
    }

    public void scale(double k){
        for (int i = 0; i < localVertices.length; i++) {
            localVertices[i].mult(k);
            globalVertices[i] = pivot.toGlobalCoords(localVertices[i]);
        }

    }
}
