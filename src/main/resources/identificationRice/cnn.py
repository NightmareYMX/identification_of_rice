from torch import nn
from torch.nn import Sequential, Conv2d, ReLU, Flatten, Linear, MaxPool2d


class cnn(nn.Module):
    def __init__(self):
        super(cnn, self).__init__()
        self.model = Sequential(
            Conv2d(3, 32, kernel_size=(5, 5), stride=(1, 1), padding=(1, 1)),
            ReLU(inplace=True),
            MaxPool2d(kernel_size=2, stride=2, padding=(1, 1), ceil_mode=False),
            Conv2d(32, 64, kernel_size=(5, 5), stride=(1, 1), padding=(1, 1)),
            ReLU(inplace=True),
            MaxPool2d(kernel_size=2, stride=2, padding=(1, 1), ceil_mode=False),
            Conv2d(64, 128, kernel_size=(3, 3), stride=(1, 1), padding=(1, 1)),
            ReLU(inplace=True),
            MaxPool2d(kernel_size=2, stride=2, padding=(1, 1), ceil_mode=False),
            Conv2d(128, 128, kernel_size=(3, 3), stride=(1, 1), padding=(1, 1)),
            ReLU(inplace=True),
            MaxPool2d(kernel_size=2, stride=2, padding=(1, 1), ceil_mode=False),
            Flatten(),
            Linear(in_features=107648, out_features=1024, bias=True),
            ReLU(inplace=True),
            Linear(in_features=1024, out_features=512, bias=True),
            ReLU(inplace=True),
            Linear(in_features=512, out_features=4, bias=True)
        )
    def forward(self, x):
        x = self.model(x)
        return x