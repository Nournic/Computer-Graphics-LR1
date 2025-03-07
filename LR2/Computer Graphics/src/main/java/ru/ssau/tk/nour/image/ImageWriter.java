package ru.ssau.tk.nour.image;

import java.awt.image.BufferedImage;
import java.io.File;

public class ImageWriter {
    private ModelObjectReader objectReader;
    private ImageDrawer methodDraw;

    public ImageWriter(File pathToObject) {
        objectReader = new ModelObjectReader(pathToObject);
        methodDraw = new ImageDrawTriangle(objectReader.getPolygons());
    }

    public BufferedImage getImage(int width, int height){
        return methodDraw.draw(width, height);
    }
}
