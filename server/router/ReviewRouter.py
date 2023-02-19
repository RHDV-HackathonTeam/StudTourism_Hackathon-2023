from flask import Flask, request, Blueprint, jsonify
from server.config import db, app
from server.models.User import ReviewModel

review = Blueprint('review_router', __name__)

@review.route('/review', methods=['POST', 'GET'])
def post_get_review():
    if request.method == 'POST':
        if request.is_json:
            data = request.get_json()
            newReview = ReviewModel(
                username=data['username'],
                text=data['text'],
                likes=data['likes'],
                imgs_refs=data['imgs_refs']
            )

            db.session.add(newReview)
            db.session.commit()

            return str(newReview.id)
        else:
            return {"error": "The request payload is not in JSON format"}

    if request.method == 'GET':
        all_review = ReviewModel.query.filter(ReviewModel.id is not None).all()
        output = []
        for review in all_review:
            l = {
                "username": review.username,
                "text": review.text,
                "likes": review.likes,
                "imgs_refs": review.imgs_refs,
            }

            output.append(l)

        return output

@review.route('/review/<username>', methods=['GET'])
def single_review(username):
    if request.method == 'GET':
        all_review = ReviewModel.query.filter_by(username=username).all()
        output = []
        for review in all_review:
            l = {
                "username": review.username,
                "text": review.text,
                "likes": review.likes,
                "imgs_refs": review.imgs_refs,
            }

            output.append(l)

        return output
