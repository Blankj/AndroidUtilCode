# Contributing to cuid

## Saucelabs setup

To run unit tests on Saucelabs, first create a Saucelabs account (if you don't
already have one) [here](https://saucelabs.com/opensource).

Copy the `bin/env.sh.example` to `bin/env.sh`

```sh
cp bin/env.sh{.example,}
```

Edit your copy of `bin/env.sh` to enter your Saucelabs username and access key.

```
export SAUCE_USERNAME=username
export SAUCE_ACCESS_KEY=key
```

## Testing

To unit test the server implementation, run

```sh
npm run test:server
```

To unit test the client implementation, run

```sh
npm run test:client
```
