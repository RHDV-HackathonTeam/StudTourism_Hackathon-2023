from server.config import db, app

class UserModel(db.Model):
    __tablename__ = 'user'

    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(), nullable=False)
    experience = db.Column(db.String(), nullable=False)
    role = db.Column(db.String(), nullable=False)
    name = db.Column(db.String(), nullable=False)
    surname = db.Column(db.String(), nullable=False)
    family_name = db.Column(db.String(), nullable=False)
    email = db.Column(db.String(), nullable=False)
    password = db.Column(db.String(), nullable=False)
    ref_link = db.Column(db.String(), nullable=False)
    notifications = db.Column(db.PickleType(), nullable=False)

    def __init__(
            self,
            name,
            role,
            username,
            experience,
            surname,
            family_name,
            email,
            password,
            ref_link,
            notifications
    ):
        with app.app_context():
            if UserModel.query.filter_by(username=username).first():
                raise Exception
            else:
                self.username = username
                self.experience = experience
                self.role = role
                self.name = name
                self.surname = surname
                self.family_name = family_name
                self.email = email
                self.password = password
                self.ref_link = ref_link
                self.notifications = notifications

                db.session.add(self)
                db.session.commit()

    def __repr__(self):
        return f"<RatesHotel {self.hotel_id}>"