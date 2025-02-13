import numpy as np
from PIL import Image

H = 500
W = 500

red_color = [255,0,0]
image_array = np.full((H,W,3),red_color,dtype=np.uint8)

image = Image.fromarray(image_array, mode="RGB")

image.save("image.png")
image.show()
