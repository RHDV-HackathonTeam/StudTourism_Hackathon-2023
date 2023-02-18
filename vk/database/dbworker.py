import os
from flask import Flask, jsonify
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)
app.app_context().push()

basedir = os.path.abspath(os.path.dirname(__file__))

app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///' + os.path.join(basedir, 'db.sqlite')

db = SQLAlchemy(app)

class Users(db.Model):
    __tablename__ = 'Users'

    id = db.Column(db.Integer(), primary_key=True)
    uuid = db.Column(db.Integer(), unique=True)
    chat_id = db.Column(db.Integer(), unique=True)
    username = db.Column(db.String(64), default ="N/A")
    notification = db.Column(db.Boolean, default = False)
    date_of_join = db.Column(db.String(64), default ="N/A")

    def __repr__(self):
        return f"<User {self.chat_id}>"

def init_user(uuid: int, chat_id: int, username: str, notification: bool, date_of_join: str):
    try:
        with app.app_context():
            if Users.query.filter_by(chat_id=chat_id).first():
                return {"message": 'User already added'}
            else:
                user = Users(uuid=uuid, chat_id=chat_id, username = username, notification = notification, date_of_join = date_of_join)
                db.session.add(user)
                db.session.commit()

            return {"message": 'User added', "name": user.chat_id}

    except Exception as e:
        return jsonify(message=e, status="DB error")

def get_users():
    try:
        with app.app_context():
            return Users.query.filter(Users.id != None).all()

    except Exception as e:
        return jsonify(message=e, status="get all user")

def get_by_id(user_id: int):
    try:
        with app.app_context():
            return Users.query.filter(Users.chat_id == user_id).first()

    except Exception as e:
        return jsonify(message=e, status="Get user by id")

def change_user_notice(user_id: int, notification: bool):
    try:
        with app.app_context():
            Users.query.filter(Users.chat_id == user_id).first().notification = notification
            db.session().commit()

    except Exception as e:
        return jsonify(message=e, status="Get user by id")