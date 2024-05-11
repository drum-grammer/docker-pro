from flask import Flask

app = Flask(__name__)

@app.route('/')
def hello_world():
    return '1억달성!'

if __name__ == '__main__':
    app.run(host='0.0.0.0')
