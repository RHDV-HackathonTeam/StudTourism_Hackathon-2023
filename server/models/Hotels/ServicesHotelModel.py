from server.config import db, app
from .HotelModel import HotelModel

# import os,sys
# sys.path.insert(1, os.path.join(sys.path[0], '../../'))
# from config import app, db

class ServicesHotel(db.Model):
    __tablename__ = 'hotelservices'

    id = db.Column(db.Integer, primary_key=True)
    service = db.Column(db.String(), nullable=True)
    price =  db.Column(db.String(), nullable=True)
    description = db.Column(db.String(), nullable=True)

    hotel_id = db.Column(db.Integer, db.ForeignKey('hotel.id'), nullable=False)
    hotel = db.relationship('HotelModel', backref=db.backref('hotelservices', lazy=True))

    def __init__(
            self,
            hotel,
            service,
            price,
            description
    ):
        with app.app_context():
            self.service = service
            self.price = price
            self.description = description

            hotel.hotelservices.append(self)
            current_db_sessions = db.object_session(hotel)
            current_db_sessions.add(hotel)
            current_db_sessions.commit()

    def __repr__(self):
        return f"<ServiceHotel {self.service}>"