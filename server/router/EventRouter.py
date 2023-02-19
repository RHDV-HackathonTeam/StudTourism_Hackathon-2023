from flask import Flask, request, Blueprint
from server.config import db, app
from server.models.Events import EventModel

# import os,sys
# sys.path.insert(1, os.path.join(sys.path[0], '../'))
# from config import app, db
# from models.Events import EventModel

event = Blueprint('event_router', __name__)


@event.route('/events', methods=['POST', 'GET'])
def handle_events():
    if request.method == 'POST':
        if request.is_json:
            data = request.get_json()
            newEvent = EventModel(
                track=data['track'],
                href=data['href'],
                specialization=data['specialization'],
                price=data['price'],
                picture_url=data['picture_url'],
                date=data['date'],
                website=data['website'],
                organization=data['organization'],
                region=data['region']
            )
            db.session.add(newEvent)
            db.session.commit()
            return {"message": f"Event {newEvent.track} has been created successfully."}
        else:
            return {"error": "The request payload is not in JSON format"}

    if request.method == 'GET':
        all_events = EventModel.query.filter(EventModel.id is not None).all()
        output = list()
        for event in all_events:
            obj = {
                "track": event.track,
                "href": event.href,
                "specialization": event.specialization,
                "price": event.price,
                "picture_url": event.picture_url,
                "date": event.date,
                "website": event.website,
                "organization": event.organization,
                "region": event.region
            }

            output.append(obj)

        return output



