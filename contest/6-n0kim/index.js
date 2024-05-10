const http = require('http');

const server = http.createServer((req, res) => { 
    res.setHeader('Content-Type' , 'text/html');
    res.write('<html>');
    res.write('<head><title>docker image contest</title></head>');
    res.write('<body><h1>hello</h1>');
    res.write('<p><a href="https://github.com/6-n0kim">Go my github</a><p>');
    res.write('</body>');
    res.write('</html>');
    res.end();
});

server.listen(4000, () => {
    console.log('Server running at 4000');
});