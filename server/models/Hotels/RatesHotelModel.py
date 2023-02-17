from server.config import db
from .HotelModel import HotelModel

class RatesHotelModel(db.Model):
    __tablename__ = 'hotelrates'

    id = db.Column(db.Integer, primary_key=True)
    room_type = db.Column(db.String(), nullable=False)
    count =  db.Column(db.String(), nullable=False)
    price = db.Column(db.String(), nullable=False)
    description = db.Column(db.String(), nullable=False)

    hotel_id = db.Column(db.Integer, db.ForeignKey('hotel.id'), nullable=False)
    hotel = db.relationship('HotelModel', backref=db.backref('hotelrates', lazy=True))

    def __init__(
            self,
            room_type,
            count,
            price,
            description
    ):
        self.room_type = room_type
        self.count = count
        self.price = price
        self.description = description

    def __repr__(self):
        return f"<RatesHotel {self.hotel_id}>"