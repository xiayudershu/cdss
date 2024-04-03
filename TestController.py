import json
import shutil
import zipfile
from flask import Flask, send_from_directory, send_file, request, jsonify
import os
from flask_cors import CORS
import os
from PIL import Image
app = Flask(__name__)
CORS(app)

def is_empty_dir(directory):
    return not any(os.path.exists(os.path.join(directory, sub_item)) for sub_item in os.listdir(directory))

def remove_empty_dirs(root_folder):
    for root, dirs, _ in os.walk(root_folder, topdown=False):
        for dir_name in dirs:
            dir_path = os.path.join(root, dir_name)
            if is_empty_dir(dir_path):
                print(f"Deleting empty folder: {dir_path}")
                os.rmdir(dir_path)





@app.route('/getx/<path:filename>')
def get_image(filename):
    print(filename)
    return send_from_directory('datasets', filename)


def remove_if_exists(file_path):
     if os.path.exists(file_path):
        os.remove(file_path)

@app.route('/docv', methods=['POST'])
def get_cv_image():

    data = request.get_json()
    left_filename = data.get('left')
    right_filename = data.get('right')
    left_name,left_attri=left_filename.split('.')
    right_name, right_attri = right_filename.split('.')
    left_name=left_name+".txt"
    right_name=right_name+".txt"

    source_path_left = os.path.join('datasets', left_filename)
    source_path_right = os.path.join('datasets', right_filename)

    # 1：mild 2:sever

    remove_if_exists(os.path.join("oaruns", "labels", left_name))
    remove_if_exists(os.path.join("raruns", "labels", left_name))
    remove_if_exists(os.path.join("oaruns", "labels", right_name))
    remove_if_exists(os.path.join("raruns", "labels", right_name))


    output_dir_oa = './oaruns'
    predict_command_left_oa = f'yolo predict model=oabest.pt source={source_path_left} save_txt=True save_conf=True save_dir={output_dir_oa}'
    predict_command_right_oa = f'yolo predict model=oabest.pt source={source_path_right} save_txt=True save_conf=True save_dir={output_dir_oa}'
    output_dir_ra = './raruns'
    predict_command_left_ra = f'yolo predict model=rabest.pt source={source_path_left} save_txt=True save_conf=True save_dir={output_dir_ra}'
    predict_command_right_ra = f'yolo predict model=rabest.pt source={source_path_right} save_txt=True save_conf=True save_dir={output_dir_ra}'

    os.system(predict_command_left_oa)
    os.system(predict_command_right_oa)
    os.system(predict_command_left_ra)
    os.system(predict_command_right_ra)

    return {'message': 'Files received and processed successfully'}

@app.route('/getcv/<path:filename>/<path:atrri>')
def get_image_cv(filename,atrri):
    if atrri=="oa":
        target_folder = os.path.join('oaruns')
    if atrri=="ra":
        target_folder = os.path.join('raruns')


    for file_name in os.listdir(target_folder):
        if file_name==filename:
             return send_from_directory(target_folder,filename)

    return {'message': 'Files received and processed unsuccessfully'}


def get_clip_img(filename, data_path,atrri):
    var_name, var_atrri = filename.split('.')
    filename = var_name + '.png'
    source_path = os.path.join('datasets', filename)
    print(f"图片路径：{source_path}")
    print(f"数据路径：{data_path}")

    # 读取原始图片
    original_image = Image.open(source_path)

    # 创建保存剪裁图像的文件夹
    if atrri=="oa":
        cropped_images_dir = os.path.join('cropped_images_oa', var_name)
        os.makedirs(cropped_images_dir, exist_ok=True)

    if atrri=="ra":
        cropped_images_dir = os.path.join('cropped_images_ra', var_name)
        os.makedirs(cropped_images_dir, exist_ok=True)

    # 读取数据文件中的剪裁参数
    with open(data_path, 'r') as f:
        lines = f.readlines()

    for line in lines:
        data = line.strip().split()
        label = int(data[0])  # 类别
        x_center = float(data[1])  # 相对于原始图像的中心点x坐标
        y_center = float(data[2])  # 相对于原始图像的中心点y坐标
        width = float(data[3])  # 相对于原始图像的宽度
        height = float(data[4])  # 相对于原始图像的高度
        possible=float(data[5])
        # 计算剪裁框的左上角和右下角坐标
        left = int((x_center - width / 2) * original_image.width)
        top = int((y_center - height / 2) * original_image.height)
        right = int((x_center + width / 2) * original_image.width)
        bottom = int((y_center + height / 2) * original_image.height)

        # 根据剪裁框剪裁图像
        cropped_image = original_image.crop((left, top, right, bottom))

        # 保存剪裁后的图像
        cropped_image_filename = f"{var_name}_cropped_{label}_{possible}.png"
        cropped_image_path = os.path.join(cropped_images_dir, cropped_image_filename)
        cropped_image.save(cropped_image_path)
        print(f"保存剪裁后的图像：{cropped_image_path}")

@app.route('/getdata/<path:filename>')
def get_data_cv(filename):

    target_folder_oa = os.path.join('oaruns', 'labels')

    target_folder_ra = os.path.join('raruns', 'labels')

    for txt_name in os.listdir(target_folder_oa):
        if txt_name==filename:
            data_path_oa = os.path.join(target_folder_oa, filename)
            get_clip_img(filename,data_path_oa,"oa")

    for txt_name in os.listdir(target_folder_ra):
        if txt_name==filename:
            data_path_ra = os.path.join(target_folder_ra, filename)
            get_clip_img(filename,data_path_ra,"ra")

    return {'message': 'Files received and processed successfully'}

@app.route('/getdatafrompy/<path:filename>')
def get_data_py(filename):
    data_array = {
        'oa':[],
        'ra':[]
    }
    oa_l = "oaruns/labels/" + filename + "_L.txt"
    oa_r = "oaruns/labels/" + filename + "_R.txt"
    ra_l = "raruns/labels/" + filename + "_L.txt"
    ra_r = "raruns/labels/" + filename + "_R.txt"


    with open(oa_l, 'r') as file:
        oa_l_lines = file.readlines()

    if oa_l_lines:
        for line in oa_l_lines:
            first_data =line.strip().split(' ')[0]
            last_data = line.strip().split(' ') [-1]
            data_array['oa'].append({'degree': int(first_data),'possible': float(last_data),'hand': "left"})

    with open(oa_r, 'r') as file:
        oa_r_lines = file.readlines()

    if oa_r_lines:
        for line in oa_r_lines:
            first_data =line.strip().split(' ')[0]
            last_data = line.strip().split(' ') [-1]
            data_array['oa'].append({'degree': int(first_data),'possible': float(last_data),'hand': "right"})




    with open(ra_l, 'r') as file:
        ra_l_lines = file.readlines()

    if ra_l_lines:
        for line in oa_r_lines:
            first_data =line.strip().split(' ')[0]
            last_data = line.strip().split(' ') [-1]
            data_array['ra'].append({'degree': int(first_data),'possible': float(last_data),'hand': "left"})

    with open(ra_r, 'r') as file:
        ra_r_lines = file.readlines()

    if ra_r_lines:
        for line in oa_r_lines:
            first_data =line.strip().split(' ')[0]
            last_data = line.strip().split(' ') [-1]
            data_array['ra'].append({'degree': int(first_data),'possible': float(last_data),'hand': "right"})


    #print(data_array)

    return jsonify(data_array)




@app.route('/get_images', methods=['POST'])
def get_clip_image():
    data = request.get_json()
    folder_name = data['folder_name']
    big_folder_name=data['atrri']
    if big_folder_name=="oa":
        folder_path = os.path.join('cropped_images_oa', folder_name)
    if big_folder_name=="ra":
        folder_path = os.path.join('cropped_images_ra', folder_name)

    image_names = os.listdir(folder_path)
    image_paths = [os.path.join(folder_path, img_name) for img_name in image_names]
    print(image_paths)
    return jsonify({'images': image_names}), 200

@app.route('/get_certen_image/<path:filename>/<path:atrri>')
def get_certen_image(filename,atrri):

    img_1,img_2, img_3,img_4,img_5,img_6,= filename.split('_')
    folder_name=img_1+'_'+img_2+'_'+img_3
    if atrri=="oa":
        folder_path = os.path.join('cropped_images_oa', folder_name)
    if atrri=="ra":
        folder_path = os.path.join('cropped_images_ra', folder_name)
    print(folder_path)

    return send_from_directory(folder_path, filename)




if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8088)