import json

from app import app  # app: Ứng dụng flask

from flask import render_template, flash, request, redirect, url_for, jsonify

@app.route('/request_bike_id', methods=['POST'])
def convert_bike_id():
    if request.method == 'POST':
        barcode = request.args['code']
        inverseBarcode = ''
        errorCode = 0
        bikeId = -1

        print(type(barcode))
        print(barcode)

        for c in barcode:
            if c not in ['0', '1']:
                errorCode = 1
                break
            inverseBarcode += '0' if c == '1' else '1'
        if not errorCode:
            bikeId = int(inverseBarcode, 2)
        return jsonify({
            'errorCode': '01' if errorCode else '00',
            'bikeId': bikeId
        })
