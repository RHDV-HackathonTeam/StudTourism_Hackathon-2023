from flask_migrate import Migrate
from server.config.config import app, db

from server.models.News import NewsModel
from server.models.Events import EventModel
from server.models.Science import ScienceModel
from server.models.Hotels import HotelModel, RatesHotelModel, ServicesHotel

migrate = Migrate(app, db)

app.app_context().push()