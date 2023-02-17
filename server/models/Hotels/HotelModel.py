from server.config import db

class HotelModel(db.Model):
    __tablename__ = 'hotel'

    id = db.Column(db.Integer, primary_key=True)
    hostel = db.Column(db.String(), nullable=False)
    university = db.Column(db.String(), nullable=False)
    website = db.Column(db.String(), nullable=False)
    picture_url = db.Column(db.String(), nullable=False)
    region = db.Column(db.String(), nullable=False)
    city = db.Column(db.String(), nullable=False)
    nutrition = db.Column(db.String(), nullable=False)
    address = db.Column(db.String(), nullable=False)
    period = db.Column(db.String(), nullable=False)
    conditions_for_organizations = db.Column(db.String(), nullable=False)
    conditions_for_students = db.Column(db.String(), nullable=False)
    organization = db.Column(db.String(), nullable=False)
    phone = db.Column(db.String(), nullable=False)
    email = db.Column(db.String(), nullable=False)

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
            email
    ):
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

    def __repr__(self):
        return f"<Hotel {self.hostel}>"