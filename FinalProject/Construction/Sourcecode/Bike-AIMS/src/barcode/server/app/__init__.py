from flask import Flask
from config import Config

app = Flask(__name__)           # __name__: biến được định nghĩa sẵn và có giá trị tuỳ vào module sử dụng nó
app.config.from_object(Config)  # Set up config cho app

from app import routes      # Đặt ở cuối để tránh import vòng (circular import)