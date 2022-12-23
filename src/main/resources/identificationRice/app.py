import os

import torch
from flask import Flask, jsonify, request

from cnn import cnn
from utils import get_image, get_result

app = Flask(__name__)


@app.route('/test')
def hello_world():
    return 'Hello World!'

@app.route('/predict')
def predict():
    path = os.path.split(os.path.abspath(__file__))[0]
    file_name = request.values.get('fileName')
    image = get_image(file_name)
    model = cnn()
    model.load_state_dict(torch.load(path + "\static\cnn.pth"))
    result = get_result(model(image).argmax(1).item())
    return jsonify({'result': result, 'msg': '200'})

if __name__ == '__main__':
    app.run(host='127.0.0.1', port=80)
