from server.config import db, app


import sys
import os

# sys.path.insert(1, os.path.join(sys.path[0], '../../'))
# from config import db, app

class EventModel(db.Model):
    __tablename__ = 'event'

    id = db.Column(db.Integer, primary_key=True)
    track = db.Column(db.String(), nullable=False)
    href = db.Column(db.String(), nullable=False)
    specialization = db.Column(db.String(), nullable=False)
    price = db.Column(db.String(), nullable=False)
    picture_url = db.Column(db.String(), nullable=False)
    date = db.Column(db.String(), nullable=False)
    website = db.Column(db.String(), nullable=False)
    organization = db.Column(db.String(), nullable=False)
    region = db.Column(db.String(), nullable=False)

    def __init__(
            self,
            track,
            href,
            specialization,
            price,
            picture_url,
            date,
            website,
            organization,
            region

    ):
        with app.app_context():
            self.track = track
            self.href = href
            self.specialization = specialization
            self.price = price
            self.picture_url = picture_url
            self.date = date
            self.website = website
            self.organization = organization
            self.region = region

            db.session.add(self)
            db.session.commit()

    def __repr__(self):
        return f"<Event {self.track}>"


# e = EventModel(
#     track="track",
#     href="href",
#     specialization="specialization",
#     price="price",
#     picture_url="picture_url",
#     date="date",
#     website="website",
#     organization="organization",
#     region="region"
# )
