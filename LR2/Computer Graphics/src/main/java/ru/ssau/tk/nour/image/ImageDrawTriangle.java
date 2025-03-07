package ru.ssau.tk.nour.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Vector;

import static java.lang.Math.*;
import static java.lang.Math.ceil;

public class ImageDrawTriangle implements ImageDrawer {
    private ArrayList<Polygon> polygons;

    public ImageDrawTriangle(ArrayList<Polygon> polygons) {
        this.polygons = polygons;
    }

    @Override
    public BufferedImage draw(int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        double[][] zBuffer = new double[1000][1000];
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                zBuffer[i][j] = Double.MAX_VALUE;
            }
        }

        for (Polygon plg: polygons)
            drawTriangle(img,zBuffer,plg);
        
        return img;
    }

    private void drawTriangle(BufferedImage img, double[][] zBuffer, Polygon plg){
        Point3D p1 = plg.getOnePoint();
        Point3D p2 = plg.getTwoPoint();
        Point3D p3 = plg.getThreePoint();
        
        double xmin = min(min(p1.getX(), p2.getX()), p3.getX()) < 0 
                ? 0 : min(min(p1.getX(), p2.getX()), p3.getX());
        double xmax = max(max(p1.getX(), p2.getX()), p3.getX()) > img.getWidth() 
                ? img.getWidth(): max(max(p1.getX(), p2.getX()), p3.getX());
        double ymin = min(min(p1.getY(), p2.getY()), p3.getY()) < 0 
                ? 0 : min(min(p1.getY(), p2.getY()), p3.getY());
        double ymax = max(max(p1.getY(), p2.getY()), p3.getY()) > img.getHeight() 
                ? img.getHeight(): max(max(p1.getY(), p2.getY()), p3.getY());

        Vector<Double> norm = new Vector<>();

        norm.add((p2.getZ()-p3.getZ())*p1.getY() +(-p1.getZ()+p3.getZ())*p2.getY()+p3.getY()*(p1.getZ()-p2.getZ()));
        norm.add((-p2.getZ()+p3.getZ())*p1.getX()+(p1.getZ()-p3.getZ())*p2.getX()-p3.getX()*(p1.getZ()-p2.getZ()));
        norm.add((p2.getY()-p3.getY())*p1.getX()+(-p1.getY()+p3.getY())*p2.getX()+p3.getX()*(p1.getY()-p2.getY()));

        double nl = getVectorMult(norm.getFirst(),norm.get(1),norm.getLast(),0,0,1);
        double nsm = nl/(getNorm(norm.getFirst(),norm.get(1),norm.getLast()) * getNorm(0,0,1));

        if(Double.compare(nsm,0.0)>=0)
            return;

        Color color = new Color(abs((int)(255*nsm)), abs((int)(255*nsm)), abs((int)(255*nsm)));

        for (int i = (int)floor(xmin); i < (int)ceil(xmax); i++) {
            for (int j = (int)floor(ymin); j < (int)ceil(ymax); j++) {
                ArrayList<Double> barycentric = evaluateBarycentricCoordinates(i,j,p1.getX(),p1.getY(),p2.getX(),p2.getY(),p3.getX(),p3.getY());
                boolean paint = true;
                for (Double l: barycentric){
                    if (Double.compare(l, 0.0) < 0) {
                        paint = false;
                        break;
                    }
                }
                if(paint){
                    double zb = p1.getZ()*barycentric.getFirst() + p2.getZ()*barycentric.get(1)+p3.getZ()*barycentric.getLast();
                    if(zBuffer[i][j]<zb)
                        continue;

                    img.setRGB(i, j, color.getRGB());
                    zBuffer[i][j]=zb;
                }
            }
        }
    }

    private static ArrayList<Double> evaluateBarycentricCoordinates(int x, int y, double x0, double y0, double x1, double y1, double x2, double y2){
        double v = (x0 - x2) * (y1 - y2) - (x1 - x2) * (y0 - y2);

        double lambda0 = ((x-x2)*(y1-y2)-(x1-x2)*(y-y2))/ v;
        double lambda1 = ((x0 - x2) * (y - y2) - (x - x2) * (y0 - y2)) / v;
        double lambda2 = 1.0 - lambda0 - lambda1;

        ArrayList<Double> result = new ArrayList<>();
        result.add(lambda0);
        result.add(lambda1);
        result.add(lambda2);

        return result;
    }

    private double getVectorMult(double x0, double y0, double z0, double x1, double y1, double z1){
        return x0*x1+y0*y1+z0*z1;
    }

    private double getNorm(double x0, double y0, double z0){
        return Math.sqrt(Math.pow(x0,2)+Math.pow(y0,2)+Math.pow(z0,2));
    }
}
