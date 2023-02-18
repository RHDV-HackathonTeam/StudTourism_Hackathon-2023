import os, sys
from flask import Flask, request
from flask_sqlalchemy import SQLAlchemy
from dotenv import load_dotenv

DB_SETTINGS = {
    'host': f'{os.getenv("db_host")}',
    'port': f'{os.getenv(("db_port"))}',
    'database': f'{os.getenv("db_database")}',
    'user': f'{os.getenv("db_user")}',
    'password': f'{os.getenv("db_password")}'
}

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = os.getenv("DATABASE_URL",
                                                  f"postgresql://root:root@{DB_SETTINGS.get('host')}:5432/{DB_SETTINGS.get('database')}")
db = SQLAlchemy(app)