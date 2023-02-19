from flask import Flask, request, Blueprint
from server.config import db, app
from server.models.News import NewsModel

news = Blueprint('news_router', __name__)

@news.route('/news', methods=['POST', 'GET'])
def handle_news():
    if request.method == 'POST':
        if request.is_json:
            data = request.get_json()
            newNews = NewsModel(
                href=data['href'],
                title=data['title'],
                picture_url=data['picture_url'],
                text=data['text'],
                tags=data['tags']
            )
            db.session.add(newNews)
            db.session.commit()
            return {"message": f"News {newNews.href} has been created successfully."}
        else:
            return {"error": "The request payload is not in JSON format"}

    if request.method == 'GET':
        all_news = NewsModel.query.filter(NewsModel.id is not None).all()
        output = list()
        for news in all_news:
            obj = {
                "href": news.href,
                "title": news.title,
                "picture_url": news.picture_url,
                "text": news.text,
                "tags": news.tags
            }

            output.append(obj)

        return output

