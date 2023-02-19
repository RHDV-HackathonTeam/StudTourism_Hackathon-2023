from flask import Flask, request, Blueprint, jsonify
from server.config import db, app
from server.models.User import TravelModel

booking = Blueprint('booking_router', __name__)

@booking.route('/booking', methods=['POST'])
def booking_post():
    if request.method == 'POST':
        if request.is_json:

            data = request.get_json()
            newBooking = TravelModel(
                fio=data['fio'],
                guest_count=data['guest_count'],
                entry=data['entry'],
                depart=data['depart'],
                phone_number=data['phone_number'],
                email=data['email'],
                username=data['username'],
                status=data['status'],
                hostel_id = data['hostel_id']
            )

            db.session.add(newBooking)
            db.session.commit()

            return newBooking.username
        else:
            return {"error": "The request payload is not in JSON format"}

@booking.route('/booking/<username>', methods=['GET'])
def booking_by_username(username):
    bookings = TravelModel.query.filter(TravelModel.username == username).all()
    output = []
    for booking in bookings:
        l = {
            "fio": booking.fio,
            "guest_count": booking.guest_count,
            "entry": booking.entry,
            "depart": booking.depart,
            "phone_number": booking.phone_number,
            "email": booking.email,
            "username": booking.username,
            "status": booking.status,
            "hostel_id": booking.hostel_id,
        }

        output.append(l)

    return output

