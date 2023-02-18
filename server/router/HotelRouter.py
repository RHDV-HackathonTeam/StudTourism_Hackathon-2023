from flask import Flask, request, Blueprint
from server.config import db, app
from server.models.Hotels import HotelModel, RatesHotelModel, ServicesHotel

hotel = Blueprint('hotel_router', __name__)

@hotel.route('/hotel', methods=['POST', 'GET'])
def handle_hotel():
    if request.method == 'POST':
        if request.is_json:

            data = request.get_json()
            newHotel = HotelModel(
                hostel=data['hostel'],
                university=data['university'],
                website=data['website'],
                picture_url=data['picture_url'],
                region=data['region'],
                city=data['city'],
                nutrition=data['nutrition'],
                address=data['address'],
                period=data['period'],
                conditions_for_organizations=data['conditions_for_organizations'],
                conditions_for_students=data['conditions_for_students'],
                organization=data['organization'],
                phone=data['phone'],
                email=data['email']
            )

            db.session.add(newHotel)
            db.session.commit()

            for i in data['rates']:
                newRatesHotel = RatesHotelModel(
                    hotel=newHotel,
                    room_type=i['room_type'],
                    count=i['count'],
                    price=i['price'],
                    description=i['description']
                )
                db.session.add(newRatesHotel)
            db.session.commit()

            for i in data['services']:
                ServicesHotel(
                    hotel = newHotel,
                    service=i['service'],
                    price=i['price'],
                    description=i['description']
                )

            return {"message": f"News {newHotel.hostel} has been created successfully."}
        else:
            return {"error": "The request payload is not in JSON format"}



