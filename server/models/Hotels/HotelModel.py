from server.config import db, app

# import os,sys
# sys.path.insert(1, os.path.join(sys.path[0], '../../'))
# from config import app, db

class HotelModel(db.Model):
    __tablename__ = 'hotel'

    id = db.Column(db.Integer, primary_key=True)
    hostel = db.Column(db.String(), nullable=True)
    university = db.Column(db.String(), nullable=True)
    website = db.Column(db.String(), nullable=True)
    picture_url = db.Column(db.String(), nullable=True)
    region = db.Column(db.String(), nullable=True)
    city = db.Column(db.String(), nullable=True)
    nutrition = db.Column(db.String(), nullable=True)
    address = db.Column(db.String(), nullable=True)
    period = db.Column(db.String(), nullable=True)
    conditions_for_organizations = db.Column(db.String(), nullable=True)
    conditions_for_students = db.Column(db.String(), nullable=True)
    organization = db.Column(db.String(), nullable=True)
    phone = db.Column(db.String(), nullable=True)
    email = db.Column(db.String(), nullable=True)
    href = db.Column(db.String(), nullable=True)


    def __init__(
            self,
            hostel,
            university,
            website,
            picture_url,
            region,
            city,
            nutrition,
            address,
            period,
            conditions_for_organizations,
            conditions_for_students,
            organization,
            phone,
            email,
            href
    ):
        with app.app_context():
            self.hostel = hostel
            self.university = university
            self.website = website
            self.picture_url = picture_url
            self.region = region
            self.city = city
            self.nutrition = nutrition
            self.address = address
            self.period = period
            self.conditions_for_organizations = conditions_for_organizations
            self.conditions_for_students = conditions_for_students
            self.organization = organization
            self.phone = phone
            self.email = email
            self.href = href

            db.session.add(self)
            db.session.commit()

    def __repr__(self):
        return f"<Hotel {self.hostel}>"