package ru.ssau.tk.nour.image.other;

public class Camera {
    private Pivot pivot;
    public double screenDistance;

    public Camera(Vector3 center, double screenDistance) {
        this.pivot = new Pivot(center);
        this.screenDistance = screenDistance;
    }
}
