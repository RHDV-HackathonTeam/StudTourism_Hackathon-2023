from server.config import db
from .HotelModel import HotelModel

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
            service,
            price,
            description
    ):
        self.service = service
        self.price = price
        self.description = description

    def __repr__(self):
        return f"<ServiceHotel {self.service}>"