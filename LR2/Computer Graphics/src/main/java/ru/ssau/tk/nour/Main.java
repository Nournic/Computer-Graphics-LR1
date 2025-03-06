package ru.ssau.tk.nour;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.*;

public class Main {
    public static void main(String[] args) throws IOException {
        File obj = new File("C:/Games/LR1/model_1.obj");

        ArrayList<ArrayList<Double>> verticies = readVertices(obj);
        ArrayList<ArrayList<Integer>> polygons = readPolygons(obj);

        BufferedImage img = drawModel(polygons, verticies, 1000, 1000);
        BufferedImage img2 = createRotated(img);

        File out = new File("C:/Games/image.jpg");
        ImageIO.write(img2, "jpg", out);
    }

    private static BufferedImage createRotated(BufferedImage image)
    {
        AffineTransform at = AffineTransform.getRotateInstance(
                -Math.PI/2, image.getWidth()/2, image.getHeight()/2.0);
        return createTransformed(image, at);
    }

    private static BufferedImage createTransformed(
            BufferedImage image, AffineTransform at)
    {
        BufferedImage newImage = new BufferedImage(
                image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImage.createGraphics();
        g.transform(at);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    static BufferedImage drawModel(ArrayList<ArrayList<Integer>> polygons, ArrayList<ArrayList<Double>> verticies, int width, int heigth){
        BufferedImage img = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);

        for (ArrayList<Integer> polygon: polygons){
            for (int i = 0; i < polygon.size(); i++) {
                ArrayList<Double> v1 = verticies.get(polygon.get(i) - 1);
                ArrayList<Double> v2 = verticies.get(polygon.get((i+1)%polygon.size())-1);
                int x1 = (int) round(v1.getFirst());
                int y1 = (int) round(v1.getLast());

                int x2 = (int) round(v2.getFirst());
                int y2 = (int) round(v2.getLast());
                drawLine(img, x1,y1,x2,y2,255);
            }
        }

        return img;
    }

    static void drawLine(BufferedImage img, int x0, int y0, int x1, int y1, int color){
        boolean xchange = false;
        if(abs(x0-x1)<abs(y0-y1)){
            int temp = x0;
            x0=y0;
            y0=temp;

            temp = x1;
            x1=y1;
            y1=temp;
            xchange = true;
        }

        if(x0>x1){
            int temp = x0;
            x0 = x1;
            x1=temp;

            temp = y0;
            y0=y1;
            y1=temp;
        }

        int y = y0;
        int dy = 2*abs(y1-y0);
        int derror = 0;
        int y_update = y1>y0 ? 1 : -1;

        for (int x = x0; x < x1; x++) {
            if(xchange)
                img.setRGB(x,y,color);
            else
                img.setRGB(y,x,color);

            derror += dy;
            if(derror > (x1-x0)){
                derror -= 2*(x1-x0);
                y+=y_update;
            }
        }
    }

    static ArrayList<ArrayList<Double>> readVertices(File obj){
        ArrayList<ArrayList<Double>> verticies = new ArrayList<>();
        try{
            FileReader reader = new FileReader(obj);
            BufferedReader br = new BufferedReader(reader);
            String line;
            while((line = br.readLine())!=null){
                if(line.startsWith("v")){
                    String[] parts = line.split(" ");
                    double x = Double.parseDouble(parts[1]),y = Double.parseDouble(parts[2]);

                    ArrayList<Double> xy = new ArrayList<>();
                    xy.add(9000*x+500); xy.add(9000*y);
                    verticies.add(xy);
                }
            }
            br.close();
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return verticies;
    }

    static ArrayList<ArrayList<Integer>> readPolygons(File obj) {
        ArrayList<ArrayList<Integer>> polygons = new ArrayList<>();
        try {
            FileReader reader = new FileReader(obj);
            BufferedReader br = new BufferedReader(reader);
            String line;
            while((line = br.readLine())!=null){
                if(line.startsWith("f")){
                    ArrayList<Integer> verticies = new ArrayList<>();
                    String[] parts = line.split(" ");
                    for (String part: Arrays.stream(parts).skip(1).toList()) {
                        verticies.add(Integer.valueOf(part.split("/")[0]));
                    }
                    polygons.add(verticies);
                }
            }
            br.close();
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return polygons;

    }
}