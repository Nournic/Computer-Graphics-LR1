package ru.ssau.tk.nour.image.other;

public class Pivot {
    public Vector3 center, XAxis, YAxis, ZAxis;

    public Matrix3 localCoordsMatrix(){
        return new Matrix3(new double[][]{
                {XAxis.getX(), YAxis.getX(), ZAxis.getX()},
                {XAxis.getY(), YAxis.getY(), ZAxis.getY()},
                {XAxis.getZ(), YAxis.getZ(), ZAxis.getZ()}}
        );
    }

    public Matrix3 globalCoordsMatrix(){
        return new Matrix3(new double[][]{
                {XAxis.getX(), XAxis.getY(), XAxis.getZ()},
                {YAxis.getX(), YAxis.getY(), YAxis.getZ()},
                {ZAxis.getX(), ZAxis.getY(), ZAxis.getZ()}}
        );
    }

    public Vector3 toLocalCoords(Vector3 global){
        return localCoordsMatrix().multVector(global.sub(center));
    }

    public Vector3 toGlobalCoords(Vector3 local){
        return globalCoordsMatrix().multVector(local).add(center);
    }

    public void move(Vector3 v){
        center.add(v);
    }

    public void rotate(double angle, Axis axis){
        this.XAxis = XAxis.rotate(angle, axis);
        this.YAxis = YAxis.rotate(angle, axis);
        this.ZAxis = ZAxis.rotate(angle, axis);
    }
}
