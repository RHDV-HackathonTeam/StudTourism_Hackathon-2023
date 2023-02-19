from flask import Flask, request, Blueprint, jsonify
from server.config import db, app
from server.models.User import MarksModel

mark = Blueprint('mark_router', __name__)

@mark.route('/marks', methods=['POST', 'GET'])
def post_get_marks():
    if request.method == 'POST':
        if request.is_json:
            data = request.get_json()
            newMark = MarksModel(
                username=data['username'],
                text=data['text'],
                likes=data['likes'],
                imgs_refs=data['imgs_refs'],
                longitude=data['longitude'],
                latitude =data['latitude'],
            )

            db.session.add(newMark)
            db.session.commit()

            return str(newMark.id)
        else:
            return {"error": "The request payload is not in JSON format"}

    if request.method == 'GET':
        all_marks = MarksModel.query.filter(MarksModel.id is not None).all()
        output = []
        for mark in all_marks:
            l = {
                "username": mark.username,
                "text": mark.text,
                "likes": mark.likes,
                "imgs_refs": mark.imgs_refs,
                "longitude": mark.longitude,
                "latitude": mark.latitude
            }

            output.append(l)

        return output

@mark.route('/marks/<username>', methods=['GET'])
def single_review(username):
    if request.method == 'GET':
        all_marks = MarksModel.query.filter_by(username=username).all()
        output = []
        for mark in all_marks:
            l = {
                "username": mark.username,
                "text": mark.text,
                "likes": mark.likes,
                "imgs_refs": mark.imgs_refs,
                "longitude": mark.longitude,
                "latitude": mark.latitude
            }

            output.append(l)

        return output
