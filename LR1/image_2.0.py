import numpy as np
from PIL import Image

def dotted_line(image, x0, y0, x1, y1, count, color):
    step = 1.0/count
    for t in np.arange(0,1,step):
        x = round ((1.0 - t)*x0 + t*x1)
        y = round ((1.0 - t)*y0 + t*y1)
        image[y, x] = color

img_mat = np.full((500,500,3),255,dtype=np.uint8)
for i in range(13):
    x0,y0=250,250
    x1,y1=np.cos((i*2.0*np.pi)/13.0)*250+250,np.sin((i*2.0*np.pi)/13.0)*250+250
    dotted_line(img_mat, x0, y0, x1, y1, 100, 0)

image = Image.fromarray(img_mat,mode="RGB")

image.save("image.png")
image.show()