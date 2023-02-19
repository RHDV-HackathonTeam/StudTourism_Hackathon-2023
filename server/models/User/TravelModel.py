from server.config import db, app

class TravelModel(db.Model):
    __tablename__ = 'travel'

    id = db.Column(db.Integer, primary_key=True)
    fio = db.Column(db.String(), nullable=False)
    guest_count = db.Column(db.String(), nullable=False)
    entry = db.Column(db.String(), nullable=False)
    depart = db.Column(db.String(), nullable=False)
    phone_number = db.Column(db.String(), nullable=False)
    email = db.Column(db.String(), nullable=False)
    username = db.Column(db.String(), nullable=False)
    status = db.Column(db.String(), nullable=False)

    def __init__(
            self,
            fio,
            guest_count,
            entry,
            depart,
            phone_number,
            email,
            username,
            status
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

            db.session.add(self)
            db.session.commit()

    def __repr__(self):
        return f"<RatesHotel {self.hotel_id}>"