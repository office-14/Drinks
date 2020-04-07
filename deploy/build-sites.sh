#!/usr/bin/env bash
set -ex

cd web-admin && \
npm install && \
npm run build && \
cd ../web-client && \
npm install && \
npm run build

set +e