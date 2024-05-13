from flask import Flask

app = Flask(__name__)

@app.route('/')
def hello_world():
    return '수강생들의 빠른 취업, 빠른 연봉 1억 상승 기원합니다!'

if __name__ == '__main__':
    app.run(host='0.0.0.0')