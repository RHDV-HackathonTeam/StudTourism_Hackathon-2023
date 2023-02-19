from server.config import db, app

class MarksModel(db.Model):
    __tablename__ = 'marks'

    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(), nullable=False)
    text = db.Column(db.String(), nullable=False)
    likes = db.Column(db.Integer(), nullable=False)
    imgs_refs = db.Column(db.PickleType(), nullable=False)
    longitude = db.Column(db.String(), nullable=False)
    latitude = db.Column(db.String(), nullable=False)

    def __init__(
            self,
            username,
            text,
            likes,
            imgs_refs,
            longitude,
            latitude
    ):
        with app.app_context():
            self.username = username
            self.text = text
            self.likes = likes
            self.imgs_refs = imgs_refs
            self.longitude = longitude
            self.latitude = latitude

            db.session.add(self)
            db.session.commit()

    def __repr__(self):
        return f"<RatesHotel {self.id}>"