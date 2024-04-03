from flask import Flask, request, jsonify
from flask_cors import CORS
import os

app = Flask(__name__)
CORS(app, resources={r"/cv/getx/*": {"origins": "http://localhost:3000"}})  # 设置允许跨域的资源和来源

def search_images(folder_path, search_string):
    results = []
    #print(folder_path)
    for root, dirs, files in os.walk(folder_path):
        for file in files:
            if file.endswith((".jpg", ".png", ".jpeg")):
                if search_string in file:
                    file_path = os.path.join(root, file)
                    results.append(os.path.abspath(file_path))

    return results

@app.route('/cv/getx/<search_string>', methods=['GET', 'OPTIONS'])  # 添加 OPTIONS 请求的处理
def search_images_route(search_string):
    if request.method == 'OPTIONS':  # 针对预检请求的处理
        response = app.make_default_options_response()
    else:
        folder_path = "datasets"
        results = search_images(folder_path, search_string)
        response = jsonify(results)
    response.headers.add('Access-Control-Allow-Origin', 'http://localhost:3000')
    response.headers.add('Access-Control-Allow-Headers', 'Content-Type')  # 允许 Content-Type 头部
    return response

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8088)