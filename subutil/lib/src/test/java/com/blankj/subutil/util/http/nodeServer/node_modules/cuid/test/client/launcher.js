import configureStatic from 'ecstatic';
import http from 'http';
import sauceConnectLauncher from 'sauce-connect-launcher';

const port = process.env.TEST_PORT;
const username = process.env.SAUCE_USERNAME;
const accessKey = process.env.SAUCE_ACCESS_KEY;
const staticServer = configureStatic(__dirname);

const server = http.createServer((req, res) => {
  staticServer(req, res);
});

server.listen(port, () => {
  console.log(`Static server listening on port ${ port }`);

  sauceConnectLauncher({
      username,
      accessKey
    },
    function (err, sauceConnectProcess) {
      if (err) throw err;

      console.log('Sauce Connect ready');

      sauceConnectProcess.close(function () {
        console.log('Closed Sauce Connect process');
      });
    }
  );
});
