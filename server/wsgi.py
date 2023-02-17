from flask_migrate import Migrate
from .config import app, db

from .models.News import NewsModel, NewsTagModel
from .models.Events import EventModel
from .models.Science import ScienceModel
from .models.Hotels import HotelModel, RatesHotelModel, ServicesHotel

migrate = Migrate(app, db)

app.app_context().push()