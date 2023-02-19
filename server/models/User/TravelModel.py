from server.config import db, app

class TravelModel(db.Model):
    __tablename__ = 'travel'

    id = db.Column(db.Integer, primary_key=True)
    fio = db.Column(db.String(), nullable=True)
    guest_count = db.Column(db.String(), nullable=True)
    entry = db.Column(db.String(), nullable=True)
    depart = db.Column(db.String(), nullable=True)
    phone_number = db.Column(db.String(), nullable=True)
    email = db.Column(db.String(), nullable=True)
    username = db.Column(db.String(), nullable=True)
    hostel_id = db.Column(db.String(), nullable=True)
    status = db.Column(db.String(), nullable=True)

    def __init__(
            self,
            fio,
            guest_count,
            entry,
            depart,
            phone_number,
            email,
            username,
            status,
            hostel_id
    ):
        with app.app_context():
            self.fio = fio
            self.guest_count = guest_count
            self.entry = entry
            self.depart = depart
            self.phone_number = phone_number
            self.email = email
            self.username = username
            self.status = status
            self.hostel_id = hostel_id

            db.session.add(self)
            db.session.commit()

    def __repr__(self):
        return f"<RatesHotel {self.hotel_id}>"