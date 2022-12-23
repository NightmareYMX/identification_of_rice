import os
import urllib
import torchvision
from PIL import Image

label_dirs = ["BrownSpot", "Healthy", "Hispa", "LeafBlast"]
transforms = torchvision.transforms.Compose([
    torchvision.transforms.Resize(448),
    torchvision.transforms.ToTensor()
])
def get_result(i):
    return label_dirs[i]

def get_image(file_name):
    url = "http://www.noobyao.top/deep_learning/" + str(file_name)
    urllib.request.urlretrieve(url, filename='deep_learning_image/identification.jpg')
    path = os.path.split(os.path.abspath(__file__))[0]
    image = Image.open(path + r"\deep_learning_image\identification.jpg")
    return transforms(image).unsqueeze(0)