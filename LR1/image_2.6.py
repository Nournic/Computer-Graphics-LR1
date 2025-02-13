import numpy as np
from PIL import Image, ImageOps

def dotted_line(image, x0, y0, x1, y1, color):
    xchange = False
    if(abs(x0-x1)<abs(y0-y1)):
        x0,y0=y0,x0
        x1,y1=y1,x1
        xchange=True
    
    if (x0 > x1):
        x0, x1 = x1, x0
        y0, y1 = y1, y0
    
    y = y0
    dy = 2*abs(y1 - y0)
    derror = 0
    y_update = 1 if y1 > y0 else -1

    for x in range(x0, x1):
        if (xchange):
            image[x, y] = color
        else:
            image[y, x] = color

        derror += dy
        if (derror > (x1 - x0)):
            derror -=  2*(x1 - x0)
            y += y_update

            

img_mat = np.full((500,500,3),255,dtype=np.uint8)
for i in range(13):
    x0,y0=250,250
    x1,y1=round(np.cos((i*2.0*np.pi)/13.0)*250+250),round(np.sin((i*2.0*np.pi)/13.0)*250+250)
    dotted_line(img_mat, x0, y0, x1, y1, 0)

image = ImageOps.flip(Image.fromarray(img_mat,mode="RGB"))

image.save("image.png")
image.show()