from flask import Flask, request, Blueprint, jsonify
from server.config import db, app
from server.models.Hotels import HotelModel, RatesHotelModel, ServicesHotel

hotel = Blueprint('hotel_router', __name__)


@hotel.route('/hotels', methods=['POST', 'GET'])
def handle_hotels():
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
                email=data['email'],
                href=data['href']
            )

            db.session.add(newHotel)
            db.session.commit()

            for i in data['rates']:
                newRatesHotel = RatesHotelModel(
                    hotel=newHotel,
                    room_type=i['room_type'],
                    count=i['count'],
                    price=i['price'],
                    description=i['description'],
                    bed_count=i['bed_count']
                )
                db.session.add(newRatesHotel)

            for i in data['services']:
                ServicesHotel(
                    hotel=newHotel,
                    service=i['service'],
                    price=i['price'],
                    description=i['description']
                )

            db.session.commit()
            return {"message": f"News {newHotel.hostel} has been created successfully."}
        else:
            return {"error": "The request payload is not in JSON format"}

    if request.method == 'GET':
        hotels = HotelModel.query.filter(HotelModel.id is not None).all()

        all_hotels = list()

        for h in hotels:
            rates = dict()
            services = dict()
            hotel = {
                "id": h.id,
                "href": h.href,
                "hostel": h.hostel,
                "university": h.university,
                "website": h.website,
                "picture_url": h.picture_url,
                "region": h.region,
                "city": h.city,
                "nutrition": h.nutrition,
                "address": h.address,
                "period": h.period,
                "conditions_for_organizations": h.conditions_for_organizations,
                "conditions_for_students": h.conditions_for_students,
                "organization": h.organization,
                "phone": h.phone,
                "email": h.email,
                "rates": list(),
                "services": list()
            }
            hotelRates = RatesHotelModel.query.filter(RatesHotelModel.hotel_id == h.id).all()

            allrates = list()
            for hr in hotelRates:
                rates = {
                    "room_type": hr.room_type,
                    "count": hr.count,
                    "price": hr.price,
                    "description": hr.description,
                    "bed_count": hr.bed_count,
                }
                allrates.append(rates)

            hotelServices = ServicesHotel.query.filter(RatesHotelModel.hotel_id == h.id).all()

            allservices = list()
            for hs in hotelServices:
                services = {
                    "service": hs.service,
                    "price": hs.price,
                    "description": hs.description
                }
                allservices.append(services)

            hotel['rates'] = allrates
            hotel['services'] = allservices

            all_hotels.append(hotel)

        return all_hotels

@hotel.route('/hotel/<id>', methods=['GET'])
def handle_hotel(id):
    if request.method == 'GET':
        hotels = HotelModel.query.filter_by(id=id).first()

        rates = dict()
        services = dict()
        hotel = {
            "id": hotels.id,
            "hostel": hotels.hostel,
            "university": hotels.university,
            "website": hotels.website,
            "picture_url": hotels.picture_url,
            "region": hotels.region,
            "city": hotels.city,
            "nutrition": hotels.nutrition,
            "address": hotels.address,
            "period": hotels.period,
            "conditions_for_organizations": hotels.conditions_for_organizations,
            "conditions_for_students": hotels.conditions_for_students,
            "organization": hotels.organization,
            "phone": hotels.phone,
            "email": hotels.email,
            "rates": list(),
            "services": list(),
            "href": hotels.href,
        }
        hotelRates = RatesHotelModel.query.filter(RatesHotelModel.hotel_id == hotels.id).all()

        allrates = list()
        for hr in hotelRates:
            rates = {
                "room_type": hr.room_type,
                "count": hr.count,
                "price": hr.price,
                "description": hr.description,
                "bed_count": hr.bed_count
            }
            allrates.append(rates)

        hotelServices = ServicesHotel.query.filter(RatesHotelModel.hotel_id == hotels.id).all()

        allservices = list()
        for hs in hotelServices:
            services = {
                "service": hs.service,
                "price": hs.price,
                "description": hs.description
            }
            allservices.append(services)

        hotel['rates'] = allrates
        hotel['services'] = allservices


        return jsonify(hotel)


@hotel.route('/hotel_filter', methods=['POST'])
def hotel_filter():
    if request.method == 'POST':
        data = request.get_json()

        valid_hotels_by_rate = list()
        valid_hotels_by_service = list()

        if data['flatType'] is not None:
            valid_obj = RatesHotelModel.query.filter(RatesHotelModel.room_type == data.get('flatType')).all()
            for obj in valid_obj:
                valid_hotels_by_rate.append(obj.hotel_id)
        else:
            pass

        if data['bedCount'] is not None:
            valid_obj = RatesHotelModel.query.filter(RatesHotelModel.bed_count == data.get('bedCount')).all()
            for obj in valid_obj:
                valid_hotels_by_service.append(obj.hotel_id)
        else:
            pass

        valid_hotels = list(set(valid_hotels_by_rate) | set(valid_hotels_by_service))

        output = list()

        for i in valid_hotels:
            hotels = HotelModel.query.filter_by(id=i)\
                .filter(HotelModel.region == data.get('region') if data['region'] is not None else HotelModel.region == HotelModel.region) \
                .filter(HotelModel.city == data.get('city') if data['city'] is not None else HotelModel.city == HotelModel.city) \
                .filter(HotelModel.nutrition == data.get('food') if data['food'] is not None else HotelModel.nutrition == HotelModel.nutrition) \
                .first()

            if hotels is None:
                continue

            hotel = {
                "id": hotels.id,
                "hostel": hotels.hostel,
                "university": hotels.university,
                "website": hotels.website,
                "picture_url": hotels.picture_url,
                "region": hotels.region,
                "city": hotels.city,
                "nutrition": hotels.nutrition,
                "address": hotels.address,
                "period": hotels.period,
                "conditions_for_organizations": hotels.conditions_for_organizations,
                "conditions_for_students": hotels.conditions_for_students,
                "organization": hotels.organization,
                "phone": hotels.phone,
                "email": hotels.email,
                "rates": list(),
                "services": list(),
                "href": hotels.href,
            }
            hotelRates = RatesHotelModel.query.filter(RatesHotelModel.hotel_id == hotels.id).all()

            allrates = list()
            for hr in hotelRates:
                rates = {
                    "room_type": hr.room_type,
                    "count": hr.count,
                    "price": hr.price,
                    "description": hr.description,
                    "bed_count": hr.bed_count
                }
                allrates.append(rates)

            hotelServices = ServicesHotel.query.filter(RatesHotelModel.hotel_id == hotels.id).all()

            allservices = list()
            for hs in hotelServices:
                services = {
                    "service": hs.service,
                    "price": hs.price,
                    "description": hs.description
                }
                allservices.append(services)

            hotel['rates'] = allrates
            hotel['services'] = allservices

            output.append(hotel)



        return output

