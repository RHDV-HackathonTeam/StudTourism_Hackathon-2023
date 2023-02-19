from server.config import db, app
from .HotelModel import HotelModel

# import os,sys
# sys.path.insert(1, os.path.join(sys.path[0], '../../'))
# from config import app, db

class RatesHotelModel(db.Model):
    __tablename__ = 'hotelrates'

    id = db.Column(db.Integer, primary_key=True)
    room_type = db.Column(db.String(), nullable=True)
    bed_count = db.Column(db.String(), nullable=True)
    count = db.Column(db.String(), nullable=True)
    price = db.Column(db.String(), nullable=True)
    description = db.Column(db.String(), nullable=True)

    hotel_id = db.Column(db.Integer, db.ForeignKey('hotel.id'), nullable=False)
    hotel = db.relationship('HotelModel', backref=db.backref('hotelrates', lazy=True))

    def __init__(
            self,
            hotel,
            room_type,
            bed_count,
            count,
            price,
            description
    ):
        with app.app_context():
            print(hotel)
            self.room_type = room_type
            self.count = count
            self.price = price
            self.description = description
            self.bed_count = bed_count

            hotel.hotelrates.append(self)
            current_db_sessions = db.object_session(hotel)
            current_db_sessions.add(hotel)
            current_db_sessions.commit()

    def __repr__(self):
        return f"<RatesHotel {self.hotel_id}>"