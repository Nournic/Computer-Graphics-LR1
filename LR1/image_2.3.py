import numpy as np
from PIL import Image

def dotted_line(image, x0, y0, x1, y1, color):
    if(x0>x1):
        x0,x1=x1,x0
        y0,y1=y1,y0
    for x in range(x0, x1):
        t = (x-x0)/(x1 - x0)
        y = round ((1.0 - t)*y0 + t*y1)
        image[y, x] = color

img_mat = np.full((500,500,3),255,dtype=np.uint8)
for i in range(13):
    x0,y0=250,250
    x1,y1=round(np.cos((i*2.0*np.pi)/13.0)*250+250),round(np.sin((i*2.0*np.pi)/13.0)*250+250)
    dotted_line(img_mat, x0, y0, x1, y1, 0)

image = Image.fromarray(img_mat,mode="RGB")

image.save("image.png")
image.show()