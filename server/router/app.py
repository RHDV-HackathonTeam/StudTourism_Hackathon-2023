from flask import Flask, request
# from server.config import db, app
# from server.models.Events import EventModel

import os,sys
sys.path.insert(1, os.path.join(sys.path[0], '../'))
from config import app, db
from models.Events import EventModel

@app.route('/event', methods=['POST', 'GET'])
def handle_cars():
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

if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0")

    # elif request.method == 'GET':
    #     cars = CarsModel.query.all()
    #     results = [
    #         {
    #             "name": car.name,
    #             "model": car.model,
    #             "doors": car.doors
    #         } for car in cars]
    #
    #     return {"count": len(results), "cars": results}
