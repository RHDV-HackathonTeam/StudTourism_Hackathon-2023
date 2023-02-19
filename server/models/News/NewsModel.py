from server.config import db

class NewsModel(db.Model):
    __tablename__ = 'news'

    id = db.Column(db.Integer, primary_key=True)
    href = db.Column(db.String(), nullable=True)
    title = db.Column(db.String(), nullable=True)
    picture_url = db.Column(db.String(), nullable=True)
    text = db.Column(db.String(), nullable=True)
    tags = db.Column(db.String(), nullable=True)

    def __init__(
            self,
            href,
            title,
            picture_url,
            text,
            tags

    ):
        self.href = href
        self.title = title
        self.picture_url = picture_url
        self.text = text
        self.tags = tags

    def __repr__(self):
        return f"<News {self.href}>"