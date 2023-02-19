from flask import Flask, request, Blueprint, jsonify
from server.config import db, app
from server.models.User import UserModel

user = Blueprint('user_router', __name__)

@user.route('/users', methods=['POST'])
def register_user():
    if request.method == 'POST':
        try:
            if request.is_json:

                data = request.get_json()
                newUser = UserModel(
                    username=data['username'],
                    experience=data['experience'],
                    role=data['role'],
                    name=data['name'],
                    surname=data['surname'],
                    family_name=data['family_name'],
                    email=data['email'],
                    password=data['password'],
                    ref_link=data['ref_link'],
                    notifications=data['notifications'],
                )

                db.session.add(newUser)
                db.session.commit()

                return newUser.username
            else:
                return {"error": "The request payload is not in JSON format"}

        except Exception as e:
            return "username taken"

@user.route('/user/<username>', methods=['GET', 'PATCH'])
def user_by_id(username):
    if request.method == 'GET':
        user = UserModel.query.filter_by(username=username).first()
        return {
            "username": user.username,
            "experience": user.experience,
            "role": user.role,
            "name": user.name,
            "surname": user.surname,
            "family_name": user.family_name,
            "email": user.email,
            "password": user.password,
            "ref_link": user.ref_link,
            "notifications": user.notifications
        }

    if request.method == 'PATCH':
        data = request.get_json()
        user = UserModel.query.filter_by(username=username).first()

        user.username = data.get('username') if data['username'] is not None else user.username
        user.experience = data.get('experience') if data['experience'] is not None else user.experience
        user.role = data.get('role') if data['role'] is not None else user.role
        user.name = data.get('name') if data['name'] is not None else user.username
        user.surname = data.get('surname') if data['surname'] is not None else user.surname
        user.family_name = data.get('family_name') if data['family_name'] is not None else user.family_name
        user.email = data.get('email') if data['email'] is not None else user.email
        user.password = data.get('password') if data['password'] is not None else user.password
        user.ref_link = data.get('ref_link') if data['ref_link'] is not None else user.ref_link
        user.notifications = data.get('notifications') if data['notifications'] is not None else user.notifications

        db.session.commit()

        return {
            "username": user.username,
            "experience": user.experience,
            "role": user.role,
            "name": user.name,
            "surname": user.surname,
            "family_name": user.family_name,
            "email": user.email,
            "password": user.password,
            "ref_link": user.ref_link,
            "notifications": user.notifications,
        }