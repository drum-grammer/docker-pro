FROM python:3.9-slim

WORKDIR /app
COPY memo.py .
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

CMD ["python", "memo.py"]