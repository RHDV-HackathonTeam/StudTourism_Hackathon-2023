import os, sys
from flask import Flask, request
from flask_sqlalchemy import SQLAlchemy
from dotenv import load_dotenv

DB_SETTINGS = {
    'host': "192.168.32.3",
    'port': '5432',
    'database': 'studhack',
    'user': 'root',
    'password': 'root'
}

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = os.getenv("DATABASE_URL",
                                                  f"postgresql://root:root@{DB_SETTINGS.get('host')}:5432/{DB_SETTINGS.get('database')}")
db = SQLAlchemy(app)