from flask import Flask, request, render_template_string, session

app = Flask(__name__)
app.secret_key = b'_5#y2L"F4Q8z\n\xec]/'

@app.route('/', methods=['GET', 'POST'])
def home():
    if 'memos' not in session:
        session['memos'] = []

    if request.method == 'POST':
        if 'delete_memo' in request.form:
            memo_to_delete = request.form.get('delete_memo')
            session['memos'].remove(memo_to_delete)
            session.modified = True
        else:
            memo = request.form.get('memo')
            if memo:
                session['memos'].append(memo)
                session.modified = True

    return render_template_string('''
        <html>
        <head><title>Memo App</title></head>
        <body>
        <h1>Simple Memo App</h1>
        <form method="post">
            <textarea name="memo" placeholder="메모를 입력하세요." required></textarea><br>
            <button type="submit">메모 추가</button>
        </form>
        <h2>Memories:</h2>
        <ul>
            {% for memo in session['memos'] %}
            <li>{{ memo }} 
                <form method="post">
                    <input type="hidden" name="delete_memo" value="{{ memo }}">
                    <button type="submit">삭제</button>
                </form>
            </li>
            {% endfor %}
        </ul>
        </body>
        </html>
    ''')

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')