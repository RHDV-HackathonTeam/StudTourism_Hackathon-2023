from flask import Flask, request
from server.config import db, app
from server.router.EventRouter import event
from server.router.ScienceRouter import science
from server.router.NewsRouter import news
from server.router.HotelRouter import hotel

# import os,sys
# sys.path.insert(1, os.path.join(sys.path[0], '../'))
# from config import app, db

app.register_blueprint(event)
app.register_blueprint(science)
app.register_blueprint(news)
app.register_blueprint(hotel)

if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0")
