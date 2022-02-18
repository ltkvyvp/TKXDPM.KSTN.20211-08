import os


class Config:
    """
        # SECRET_KEY: giúp thư viện Flask-wtf tránh các vấn đề tấn công CSRF (lấy dữ liệu từ client gửi lên)
        # os.environ.get('SECRET_KEY'): Key từ os
        # 'APP_SECRET_KEY': Key tự định nghĩa
        # or: concat
    """
    SECRET_KEY = os.environ.get('SECRET_KEY') or 'APP_SECRET_KEY'
    """
        # SQLALCHEMY_DATABASE_URI: mysql+pymysql://<username>:<password>@<host>/<db_name>
        # SQLALCHEMY_TRACK_MODIFICATIONS: Theo dõi và thông báo cho Flask khi dữ liệu thay đổi
    """
    SQLALCHEMY_DATABASE_URI = "mysql://root:@localhost:3306/appapp"
    SQLALCHEMY_TRACK_MODIFICATIONS = False


print(Config.SECRET_KEY)
