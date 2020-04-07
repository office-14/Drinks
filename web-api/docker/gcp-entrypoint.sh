#!/usr/bin/env bash
export ASPNETCORE_URLS="http://0.0.0.0:${PORT}"
# Execute the rest of your ENTRYPOINT and CMD as expected.
exec "$@"