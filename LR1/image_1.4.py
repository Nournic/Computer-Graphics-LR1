import numpy as np
from PIL import Image

H = 500
W = 500

red_color = [255,0,0]
image_array = np.random.randint(0,255,size=(H,W,3),dtype=np.uint8)

image = Image.fromarray(image_array, mode="RGB")

image.save("image.png")
image.show()
