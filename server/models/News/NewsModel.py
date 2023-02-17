from server.config import db

class NewsModel(db.Model):
    __tablename__ = 'news'

    id = db.Column(db.Integer, primary_key=True)
    href = db.Column(db.String(), nullable=False)
    title = db.Column(db.String(), nullable=False)
    picture_url = db.Column(db.String(), nullable=False)
    text = db.Column(db.String(), nullable=False)

    def __init__(
            self,
            href,
            title,
            picture_url,
            text

    ):
        self.href = href
        self.title = title
        self.picture_url = picture_url
        self.text = text

    def __repr__(self):
        return f"<News {self.href}>"