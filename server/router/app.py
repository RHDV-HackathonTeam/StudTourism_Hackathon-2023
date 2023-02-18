from flask import Flask, request
from server.config import db, app

# import os,sys
# sys.path.insert(1, os.path.join(sys.path[0], '../'))
# from config import app, db

if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0")
