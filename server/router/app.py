from flask import Flask, request
from server.config import db, app
from server.router.EventRouter import event
from server.router.ScienceRouter import science
from server.router.NewsRouter import news
from server.router.HotelRouter import hotel
from server.router.UserRouter import user
from server.router.ReviewRouter import review
from server.router.BookingRouter import booking
from server.router.UploaderRouter import uploader
from server.router.MarksRouter import mark
# import os,sys
# sys.path.insert(1, os.path.join(sys.path[0], '../'))
# from config import app, db

app.register_blueprint(event)
app.register_blueprint(science)
app.register_blueprint(news)
app.register_blueprint(hotel)
app.register_blueprint(user)
app.register_blueprint(review)
app.register_blueprint(booking)
app.register_blueprint(uploader)
app.register_blueprint(mark)

if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0")
