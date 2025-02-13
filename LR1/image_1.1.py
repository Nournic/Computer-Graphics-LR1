import numpy as np
from PIL import Image

H = 500
W = 500

image_array = np.full((H,W),0,dtype=np.uint8)

image = Image.fromarray(image_array, mode="L")

image.save("image.png")
image.show()
