package ru.ssau.tk.nour.image.data;

import ru.ssau.tk.nour.image.other.ImageScale;

public class Point3D {
    private final double x;
    private final double y;
    private final double z;

    /**
     * Конструктор <code>Point3D</code>, принимающий значения трёх координат точек в пространстве.
     * @param x координата x в пространстве
     * @param y координата y в пространстве
     * @param z координата z в пространстве
     */
    public Point3D(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Конструктор Point3D, который копирует значение переданной точки <code>Point3D</code>
     * и изменяет её в соответствии с заданными параметрами в <code>ImageScale</code>.
     * @param point копируемая точка
     * @param scale объект типа <code>ImageScale</code>, задающий смещение и масштабирование точки
     */
    public Point3D(Point3D point, ImageScale scale){
        this.x = scale.getScaleX() * (point.x + scale.getShiftPointX()) + scale.getShiftX();
        this.y = scale.getScaleY() * (point.y + scale.getShiftPointY()) + scale.getShiftY();
        this.z = scale.getScaleZ() * (point.z + scale.getShiftPointZ()) + scale.getShiftZ();
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getZ() {
        return z;
    }
}
