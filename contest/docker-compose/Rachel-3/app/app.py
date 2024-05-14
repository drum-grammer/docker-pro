from flask import Flask, request, jsonify, render_template
import redis

app = Flask(__name__)
db = redis.Redis(host='redis', port=6379, db=0, decode_responses=True)

@app.route('/')
def index():
    todos = db.lrange('todos', 0, -1)
    return render_template('index.html', todos=todos)

@app.route('/todos', methods=['POST'])
def add_todo():
    todo = request.json.get('todo')
    if todo:
        db.lpush('todos', todo)
        return jsonify({'status': 'success', 'message': 'Todo added!'}), 201
    return jsonify({'status': 'error', 'message': 'No todo provided'}), 400

@app.route('/todos', methods=['GET'])
def get_todos():
    todos = db.lrange('todos', 0, -1)
    return jsonify(todos)

@app.route('/delete_todo', methods=['POST'])
def delete_todo():
    todo = request.json.get('todo')
    if todo:
        db.lrem('todos', 0, todo)
        return jsonify({'status': 'success', 'message': 'Todo deleted!'}), 200
    return jsonify({'status': 'error', 'message': 'No todo found'}), 400

if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True)