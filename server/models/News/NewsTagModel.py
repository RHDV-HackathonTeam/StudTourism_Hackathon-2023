from server.config import db
from .NewsModel import NewsModel

class NewsTagModel(db.Model):
    __tablename__ = 'newstag'

    id = db.Column(db.Integer, primary_key=True)
    tag = db.Column(db.String(), nullable=True)

    news_id = db.Column(db.Integer, db.ForeignKey('news.id'), nullable=False)
    news = db.relationship('NewsModel', backref=db.backref('newstag', lazy=True))

    def __init__(
            self,
            tag
    ):
        self.tag = tag

    def __repr__(self):
        return f"<NewsTag {self.tag}>"