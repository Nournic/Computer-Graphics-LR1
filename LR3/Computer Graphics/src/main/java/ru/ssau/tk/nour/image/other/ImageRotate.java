package ru.ssau.tk.nour.image.other;

import ru.ssau.tk.nour.image.data.Point3D;
import ru.ssau.tk.nour.image.data.Polygon;

import java.util.ArrayList;

public class ImageRotate implements ImageTransform{
    private final double alpha;
    private final double beta;
    private final double gamma;

    private final double shiftX;
    private final double shiftY;
    private final double shiftZ;

    /**
     * Строитель класса <code>ImageRotate</code>, принимающий в себя углы поворота
     * модели в пространстве, а также смещение по каждой координате.
     * */
    public static class Builder{
        private double alpha  = 0;
        private double beta   = 0;
        private double gamma  = 0;

        private double shiftX = 0;
        private double shiftY = 0;
        private double shiftZ = 0;

        public Builder(){}

        public Builder alpha(double val) {
            this.alpha = val;
            return this;
        }

        public Builder beta(double val) {
            this.beta = val;
            return this;
        }

        public Builder gamma(double val) {
            this.gamma = val;
            return this;
        }

        public Builder shiftX(double val) {
            this.shiftX = val;
            return this;
        }

        public Builder shiftY(double val) {
            this.shiftY = val;
            return this;
        }

        public Builder shiftZ(double val) {
            this.shiftZ = val;
            return this;
        }

        public ImageRotate build(){
            return new ImageRotate(this);
        }
    }

    private ImageRotate(Builder builder){
        this.alpha = builder.alpha;
        this.beta = builder.beta;
        this.gamma = builder.gamma;

        this.shiftX = builder.shiftX;
        this.shiftY = builder.shiftY;
        this.shiftZ = builder.shiftZ;
    }

    /**
     * Возвращает новый массив полигонов после трансформации поворота
     * @param polygons массив объектов <code>Polygon</code>
     * */
    @Override
    public ArrayList<Polygon> transform(ArrayList<Polygon> polygons) {
        ArrayList<Polygon> newPolygons = new ArrayList<>();

        for (Polygon plg: polygons)
            newPolygons.add(transformPolygon(plg));

        return newPolygons;
    }

    private Polygon transformPolygon(Polygon plg){
        Point3D p1 = new Point3D(
                plg.getOnePoint().getX() * Math.cos(beta) * Math.cos(gamma),
                plg.getOnePoint().getY() * Math.cos(alpha) * Math.cos(gamma),
                plg.getOnePoint().getZ() * Math.cos(alpha) * Math.cos(beta)
        );
        Point3D p2 = new Point3D(
                plg.getTwoPoint().getX() * Math.cos(beta) * Math.cos(gamma),
                plg.getTwoPoint().getY() * Math.cos(alpha) * Math.cos(gamma),
                plg.getTwoPoint().getZ() * Math.cos(alpha) * Math.cos(beta)
        );
        Point3D p3 = new Point3D(
                plg.getThreePoint().getX() * Math.cos(beta) * Math.cos(gamma),
                plg.getThreePoint().getY() * Math.cos(alpha) * Math.cos(gamma),
                plg.getThreePoint().getZ() * Math.cos(alpha) * Math.cos(beta)
        );

        return new Polygon(p1,p2,p3);
    }
}
