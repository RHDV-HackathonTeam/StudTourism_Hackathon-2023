from server.config import db

class ScienceModel(db.Model):
    __tablename__ = 'science'

    id = db.Column(db.Integer, primary_key=True)
    href = db.Column(db.String(), nullable=False)
    lab = db.Column(db.String(), nullable=False)
    website = db.Column(db.String(), nullable=False)
    city = db.Column(db.String(), nullable=True)
    year = db.Column(db.String(), nullable=True)
    description = db.Column(db.String(), nullable=True)
    description_of_organization = db.Column(db.String(), nullable=True)
    phone = db.Column(db.String(), nullable=True)
    email = db.Column(db.String(), nullable=True)
    picture_url = db.Column(db.String(), nullable=True)

    def __init__(
            self,
            href,
            lab,
            website,
            city,
            year,
            description,
            description_of_organization,
            phone,
            email,
            picture_url
    ):
        self.href = href
        self.lab = lab
        self.website = website
        self.city = city
        self.year = year
        self.description = description
        self.description_of_organization = description_of_organization
        self.phone = phone
        self.email = email
        self.picture_url = picture_url

    def __repr__(self):
        return f"<Science {self.lab}>"