from flask import Flask, request, Blueprint, jsonify, send_from_directory
from server.config import db, app

uploader = Blueprint('uploader_router', __name__)


@uploader.route('/uploads/<name>')
def download_file(name):
    try:
        return send_from_directory(f'../files/', name)
    except Exception as e:
        print(e)
        pass

@uploader.route('/uploader', methods = ['POST'])
def upload_file():
    try:
       if request.method == 'POST':
          f = request.files['file']
          f.filename = f.filename.replace(' ', '_')
          print(f.filename)
          f.save(r'../files/'+f.filename)
          return str(f.filename)
    except Exception as e:
        print(e)
        pass