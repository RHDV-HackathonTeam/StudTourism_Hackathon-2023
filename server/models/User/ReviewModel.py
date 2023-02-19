from server.config import db, app

class ReviewModel(db.Model):
    __tablename__ = 'review'

    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(), nullable=False)
    text = db.Column(db.String(), nullable=False)
    likes = db.Column(db.Integer(), nullable=False)
    imgs_refs = db.Column(db.PickleType(), nullable=False)

    def __init__(
            self,
            username,
            text,
            likes,
            imgs_refs
    ):
        with app.app_context():
            self.username = username
            self.text = text
            self.likes = likes
            self.imgs_refs = imgs_refs

            db.session.add(self)
            db.session.commit()

    def __repr__(self):
        return f"<RatesHotel {self.id}>"