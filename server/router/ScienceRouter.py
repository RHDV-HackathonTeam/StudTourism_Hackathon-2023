from flask import Flask, request, Blueprint
from server.config import db, app
from server.models.Science import ScienceModel

science = Blueprint('science_router', __name__)

@science.route('/science', methods=['POST', 'GET'])
def handle_science():
    if request.method == 'POST':
        if request.is_json:
            data = request.get_json()
            newScienceEvent = ScienceModel(
                href=data['href'],
                lab=data['lab'],
                website=data['website'],
                city=data['city'],
                year=data['year'],
                description=data['description'],
                description_of_organization=data['description_of_organization'],
                phone=data['phone'],
                email=data['email'],
                picture_url=data['picture_url']
            )
            db.session.add(newScienceEvent)
            db.session.commit()
            return {"message": f"ScienceEvent {newScienceEvent.href} has been created successfully."}
        else:
            return {"error": "The request payload is not in JSON format"}

    if request.method == 'GET':
        all_science = ScienceModel.query.filter(ScienceModel.id is not None).all()
        output = list()
        for science in all_science:
            obj = {
                "href": science.href,
                "lab": science.lab,
                "website": science.website,
                "city": science.city,
                "year": science.year,
                "description": science.description,
                "description_of_organization": science.description_of_organization,
                "phone": science.phone,
                "email": science.email,
                "picture_url": science.picture_url
            }

            output.append(obj)

        return output


