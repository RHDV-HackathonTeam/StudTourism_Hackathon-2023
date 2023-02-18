from flask import Flask, request, Blueprint
from server.config import db, app
from server.models.Events import EventModel

# import os,sys
# sys.path.insert(1, os.path.join(sys.path[0], '../'))
# from config import app, db
# from models.Events import EventModel

event = Blueprint('event_router', __name__)


@event.route('/event', methods=['POST', 'GET'])
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



