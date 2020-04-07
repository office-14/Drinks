#!/usr/bin/env bash
set -ev

cd web-admin && \
npm install && \
npm run build && \
cd ../web-client && \
npm install && \
npm run build
